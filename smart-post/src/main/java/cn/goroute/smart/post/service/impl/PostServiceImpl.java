package cn.goroute.smart.post.service.impl;


import cn.goroute.smart.common.constant.PostConstant;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.dao.*;
import cn.goroute.smart.common.entity.dto.MemberDto;
import cn.goroute.smart.common.entity.dto.PostDto;
import cn.goroute.smart.common.entity.dto.PostListDto;
import cn.goroute.smart.common.entity.pojo.Category;
import cn.goroute.smart.common.entity.pojo.Post;
import cn.goroute.smart.common.entity.pojo.PostTag;
import cn.goroute.smart.common.entity.pojo.Tag;
import cn.goroute.smart.common.entity.vo.PostQueryVo;
import cn.goroute.smart.common.entity.vo.PostVo;
import cn.goroute.smart.common.feign.MemberFeignService;
import cn.goroute.smart.common.service.AuthService;
import cn.goroute.smart.common.utils.*;
import cn.goroute.smart.post.feign.SearchFeignService;
import cn.goroute.smart.post.manage.IPostManage;
import cn.goroute.smart.post.service.PostService;
import cn.goroute.smart.post.util.Html2TextUtil;
import cn.goroute.smart.post.util.NamingThreadFactory;
import cn.goroute.smart.post.util.RabbitmqUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;


@Service("postService")
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostDao, Post> implements PostService {

    @Resource
    MemberFeignService memberFeignService;

    @Resource
    SearchFeignService searchFeignService;

    @Autowired
    RabbitmqUtil rabbitmqUtil;

    @Autowired
    TagDao tagDao;

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    PostDao postDao;

    @Autowired
    PostTagDao postTagDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CollectDao collectDao;

    @Autowired
    IPostManage iPostManage;

    @Autowired
    AuthService authService;

    private static final int CORE_SIZE = Runtime.getRuntime().availableProcessors();

    /**
     * 文章分页方法
     *
     * @param postQueryVO 分页参数
     * @return 文章分页对象
     */
    @Override
    public Result queryPage(PostQueryVo postQueryVO) {

        IPage<Post> page;

        // 查询条件 如果没有选择分类，则查询全部
        if (postQueryVO.getCategoryUid() == null) {
            page = postDao.selectPage(
                    new Query<Post>().getPage(postQueryVO),
                    new LambdaQueryWrapper<Post>()
                            .eq(Post::getIsPublish, PostConstant.PUBLISH)
                            .eq(Post::getStatus, PostConstant.NORMAL_STATUS));
            // 如果选择了分类，没有选择标签，则查询分类下的文章
        } else if (postQueryVO.getTagUid() == null) {

            page = postDao.selectPage(
                    new Query<Post>().getPage(postQueryVO),
                    new LambdaQueryWrapper<Post>()
                            .eq(Post::getCategoryUid, postQueryVO.getCategoryUid())
                            .eq(Post::getStatus, PostConstant.NORMAL_STATUS)
            );
        } else {
            // 如果选择了分类和标签，则查询分类和标签下的文章
            IPage<PostTag> postTagIPage = postTagDao.selectPage(new Query<PostTag>().getPage(postQueryVO),
                    new LambdaQueryWrapper<PostTag>()
                            .eq(PostTag::getTagUid, postQueryVO.getTagUid()));

            List<PostTag> records = postTagIPage.getRecords();
            if (CollUtil.isEmpty(records)) {
                return Result.ok().put("data", new Page<>());
            }
            List<Long> postIds = records.stream()
                    .map(PostTag::getPostUid).collect(Collectors.toList());

            List<Post> posts = postDao.selectBatchIds(postIds);
            BeanUtils.copyProperties(postTagIPage, page = new Page<>());
            page.setRecords(posts);
        }

        boolean isLogin = authService.getIsLogin();

        List<Post> records = page.getRecords();

        List<PostListDto> postList = getPostListDTOS(isLogin, records);

        PageUtils pageUtils = new PageUtils(page);

        pageUtils.setList(postList);

        return Result.ok().put("data", pageUtils);
    }


    /**
     * 获取文章列表信息
     *
     * @param isLogin 用户是否登录
     * @param records 文章集合
     * @return List<PostListDTO> 文章DTO集合
     */
    private List<PostListDto> getPostListDTOS(boolean isLogin, List<Post> records) {
        // 创建线程池
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                CORE_SIZE + 1,
                2 * CORE_SIZE + 1,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new NamingThreadFactory(this.getClass().getName() + "-thread"));
        try {
            List<PostListDto> postDTOList = new ArrayList<>(records.size());
            //遍历文章数据并转换为文章DTO
            CountDownLatch countDownLatch = new CountDownLatch(records.size());
            for (Post record : records) {
                Long loginId = authService.getLoginUid();
                poolExecutor.submit(() -> {
                    try {
                        getPostInfo(isLogin, postDTOList, record, loginId);
                    } catch (Exception e) {
                        log.error("获取文章列表信息异常：{}", e.getMessage());
                    } finally {
                        // 当线程执行完毕后，计数器减1
                        countDownLatch.countDown();
                    }
                });
            }
            countDownLatch.await();
            return postDTOList;
        } catch (RuntimeException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            poolExecutor.shutdown();
        }
    }

    /**
     * 获取文章的核心方法
     *
     * @param isLogin     用户是否登录
     * @param postDTOList 文章DTO集合
     * @param record      文章对象
     */
    private void getPostInfo(boolean isLogin, List<PostListDto> postDTOList, Post record, Long loginId) throws InterruptedException {
        // 创建线程池
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(
                CORE_SIZE + 1,
                2 * CORE_SIZE + 1,
                1L,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100),
                new NamingThreadFactory(this.getClass().getName() + "-thread"));


        PostListDto postListDTO = new PostListDto();
        BeanUtils.copyProperties(record, postListDTO);

        CompletableFuture<PostListDto> postListDTOCompletableFuture = CompletableFuture.supplyAsync(() -> {
            postListDTO.setAuthorInfo(memberFeignService.getMemberByUid(record.getMemberUid()));
            return postListDTO;
        },poolExecutor);

        CompletableFuture<PostListDto> postListDTOCompletableFuture2 = CompletableFuture.supplyAsync(() -> {
            postListDTO.setThumbCount(iPostManage.getThumbCount(record.getUid()));
            postListDTO.setCommentCount(iPostManage.getCommentCount(record.getUid()));
            if (isLogin) {
                postListDTO.setIsLike(iPostManage.checkIsThumbOrCollect(record.getUid(), loginId, 0));
                postListDTO.setIsCollect(iPostManage.checkIsThumbOrCollect(record.getUid(), loginId, 1));
            } else {
                postListDTO.setIsLike(false);
                postListDTO.setIsCollect(false);
            }
            return postListDTO;
        },poolExecutor);

        postListDTOCompletableFuture.thenCombine(postListDTOCompletableFuture2, (postListDTO1, postListDTO2) -> postListDTO1)
                .thenAccept(postDTOList::add).exceptionally(ex->{
                    log.error("获取文章列表信息异常：{}", ex.getMessage());
                    poolExecutor.shutdown();
                    return null;
                }).join();

        poolExecutor.shutdown();
    }

    /**
     * 发布/编辑文章
     *
     * @param postVo 文章vo
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result savePost(PostVo postVo) {
        Category category = categoryDao.selectById(postVo.getCategoryUid());
        if (category == null) {
            return Result.error("分类不存在");
        }

        Set<Long> tagUid = new HashSet<>(postVo.getTagUid());
        if (CollUtil.isEmpty(tagUid)) {
            return Result.error("请选择标签");
        } else {
            List<Tag> tags = tagDao.selectBatchIds(tagUid);
            tags.forEach(tag -> {
                if (tag == null) {
                    throw new RuntimeException("标签不存在");
                }
            });
        }

        Post post = new Post();

        //使用正则，过滤掉Html标签
        String htmlContent = postVo.getContentHtml();
        String text = Html2TextUtil.Html2Text(htmlContent);

        if (CharSequenceUtil.isEmpty(postVo.getSummary())) {
            String summary = CharSequenceUtil.sub(text, 0, 150);
            post.setSummary(summary);
        } else {
            post.setSummary(postVo.getSummary());
        }

        post.setIsPublish(Boolean.TRUE.equals(postVo.getIsPublish()) ? PostConstant.PUBLISH : PostConstant.NOT_PUBLISH);
        post.setTitle(postVo.getTitle());
        post.setContent(postVo.getContent());
        post.setCategoryUid(postVo.getCategoryUid());
        post.setMemberUid(authService.getLoginUid());
        post.setStatus(PostConstant.CHECK_STATUS);

        int result = -1;
        // 如果是编辑，则先删除原有的标签
        if (Objects.equals(postVo.getType(), PostConstant.POST_SAVE_TYPE_EDIT)) {
            postTagDao.delete(new LambdaQueryWrapper<PostTag>().eq(PostTag::getPostUid, postVo.getUid()));
            post.setUid(postVo.getUid());
            result = postDao.updateById(post);
        } else if (Objects.equals(postVo.getType(), PostConstant.POST_SAVE_TYPE_NEW)) {
            // 新增文章
            result = postDao.insert(post);
        }

        if (result == 1) {
            //插入后通过消息队列对文章进行异步审核
            rabbitmqUtil.reviewPost(post, new ArrayList<>(tagUid), Objects.equals(postVo.getType(), PostConstant.POST_SAVE_TYPE_EDIT));
            log.info("返回文章的ID为：{}", post.getUid());
            return Result.ok().put("url", post.getUid());
        } else {
            log.error("用户={}发布文章失败,文章对象为={}", authService.getLoginUid(), postVo);
            return Result.error("发布文章失败");
        }
    }

    /**
     * 获取文章根据文章uid
     *
     * @param uid 文章uid
     * @return 文章信息
     */
    @Override
    public Result getPostByUid(Long uid) {

        boolean isLogin = authService.getIsLogin();

        String key = RedisKeyConstant.POST_CACHE_KEY + uid;
        Post post;

        if (redisUtil.hasKey(key)) {
            return Result.error("没有该文章");
        }
        post = postDao.selectById(uid);
        // 防止缓存穿透
        if (post == null) {
            redisUtil.set(key, null, 60L * 60 * 3);
            return Result.error("没有该文章");
        }

        if (!isLogin || !Objects.equals(post.getMemberUid(), authService.getLoginUid())) {
            if (Objects.equals(post.getIsPublish(), PostConstant.NOT_PUBLISH)) {
                return Result.error("该文章已设置私有");
            }
            if (!Objects.equals(post.getStatus(), PostConstant.NORMAL_STATUS)) {
                return Result.error("该文章由于其他原因不可展示");
            }
        }

        List<MemberDto> memberInfoWithPost = memberFeignService
                .batchQueryUsers(CollUtil.toList(post.getMemberUid()));

        PostDto postDTO = new PostDto();
        BeanUtils.copyProperties(post, postDTO);

        // 获取文章的标签
        List<PostTag> tags = postTagDao.selectList(new LambdaQueryWrapper<PostTag>().eq(PostTag::getPostUid, uid));

        List<Long> tagUid = tags.stream().map(PostTag::getTagUid).collect(Collectors.toList());

        postDTO.setTagUid(tagUid);
        if (CollUtil.isNotEmpty(memberInfoWithPost)) {
            postDTO.setAuthorInfo(memberInfoWithPost.get(0));
            if (isLogin) {
                postDTO.setIsCollect(iPostManage.checkIsThumbOrCollect(uid, authService.getLoginUid(), 1));
                postDTO.setIsLike(iPostManage.checkIsThumbOrCollect(uid, authService.getLoginUid(), 0));
            } else {
                postDTO.setIsCollect(false);
                postDTO.setIsLike(false);
            }
        }

        return Objects.requireNonNull(Result.ok().put("data", postDTO));
    }

    /**
     * 删除文章
     *
     * @param postUid 文章uid
     * @return 删除结果
     */
    @Override
    public Result deletePost(Long postUid) {
        if (postUid == null) {
            return Result.error("文章uid不能为空");
        }

        Post post = postDao.selectById(postUid);

        if (!Objects.equals(post.getMemberUid(), authService.getLoginUid())) {
            return Result.error();
        }

        postDao.deleteById(postUid);
        //调用es接口逻辑删除文章
        searchFeignService.deleteSearchPost(post.getUid());
        return Result.ok();
    }

    /**
     * 根据用户uid查发布文章
     *
     * @param queryParam 查询参数
     * @return 分页结果
     */
    @Override
    public Result listByMemberUid(QueryParam queryParam) {

        IPage<Post> page = null;
        if (!authService.getIsLogin() || !Objects.equals(queryParam.getUid(), authService.getLoginUid())) {
            page = this.page(new Query<Post>()
                    .getPage(queryParam), new LambdaQueryWrapper<Post>()
                    .eq(Post::getMemberUid, queryParam.getUid())
                    .eq(Post::getStatus, PostConstant.NORMAL_STATUS)
                    .eq(Post::getIsPublish, PostConstant.PUBLISH));
        } else if (Objects.equals(queryParam.getUid(), authService.getLoginUid())) {
            page = this.page(new Query<Post>()
                    .getPage(queryParam), new LambdaQueryWrapper<Post>()
                    .eq(Post::getMemberUid, queryParam.getUid())
                    .eq(Post::getStatus, PostConstant.NORMAL_STATUS));
        }

        assert page != null;
        List<Post> postList = page.getRecords();
        if (CollUtil.isEmpty(postList)) {
            return Result.ok().put("data", new PageUtils(page));
        }
        List<PostListDto> postListDtos = new ArrayList<>(10);
        MemberDto memberDTO = memberFeignService.getMemberByUid(queryParam.getUid());

        postList.forEach(postEntity -> {
            PostListDto postListDTO = new PostListDto();
            BeanUtils.copyProperties(postEntity, postListDTO);
            postListDTO.setAuthorInfo(memberDTO);
            postListDTO.setThumbCount(iPostManage.getThumbCount(postEntity.getUid()));
            if (authService.getIsLogin()) {
                postListDTO.setIsLike(iPostManage.checkIsThumbOrCollect(postEntity.getUid(), authService.getLoginUid(), 0));
                postListDTO.setIsCollect(iPostManage.checkIsThumbOrCollect(postEntity.getUid(), authService.getLoginUid(), 1));
            } else {
                postListDTO.setIsLike(false);
                postListDTO.setIsCollect(false);
            }
            postListDtos.add(postListDTO);
        });
        IPage<PostListDto> pagePostListDTO = new Page<>();
        BeanUtils.copyProperties(page, pagePostListDTO);

        pagePostListDTO.setRecords(postListDtos);

        PageUtils pageResult = new PageUtils(pagePostListDTO);
        return Result.ok().put("data", pageResult);
    }
}