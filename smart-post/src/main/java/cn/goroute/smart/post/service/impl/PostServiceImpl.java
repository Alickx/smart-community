package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.constant.PostConstant;
import cn.goroute.smart.common.constant.RedisKeyConstant;
import cn.goroute.smart.common.dao.*;
import cn.goroute.smart.common.entity.dto.MemberDTO;
import cn.goroute.smart.common.entity.dto.PostDTO;
import cn.goroute.smart.common.entity.dto.PostListDTO;
import cn.goroute.smart.common.entity.pojo.*;
import cn.goroute.smart.common.entity.vo.PostQueryVO;
import cn.goroute.smart.common.entity.vo.PostVO;
import cn.goroute.smart.common.feign.MemberFeignService;
import cn.goroute.smart.common.utils.*;
import cn.goroute.smart.post.feign.SearchFeignService;
import cn.goroute.smart.post.service.PostService;
import cn.goroute.smart.post.util.Html2TextUtil;
import cn.goroute.smart.post.util.RabbitmqUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    RedisTemplate<String, Object> redisTemplate;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CollectDao collectDao;

    @Resource
    ThumbDao thumbDao;

    /**
     * 文章分页方法
     *
     * @param postQueryVO 分页参数
     * @return 文章分页对象
     */
    @Override
    public Result queryPage(PostQueryVO postQueryVO) {

        IPage<Post> page;

        if (postQueryVO.getCategoryUid() == null) {
            page = postDao.selectPage(
                    new Query<Post>().getPage(postQueryVO),
                    new LambdaQueryWrapper<Post>()
                            .eq(Post::getIsPublish, PostConstant.PUBLISH)
                            .eq(Post::getStatus, PostConstant.NORMAL_STATUS)
            );
        } else if (postQueryVO.getTagUid() == null) {

            page = postDao.selectPage(
                    new Query<Post>().getPage(postQueryVO),
                    new LambdaQueryWrapper<Post>()
                            .eq(Post::getCategoryUid, postQueryVO.getCategoryUid())
                            .eq(Post::getStatus, PostConstant.NORMAL_STATUS)
            );
        } else {

            IPage<PostTag> postTagIPage = postTagDao.selectPage(new Query<PostTag>().getPage(postQueryVO),
                    new LambdaQueryWrapper<PostTag>()
                            .eq(PostTag::getTagUid, postQueryVO.getTagUid()));

            List<PostTag> records = postTagIPage.getRecords();
            if (CollUtil.isEmpty(records)) {
                return Result.ok().put("data", new Page<>());
            }
            List<Long> postIds = records.parallelStream()
                    .map(PostTag::getPostUid).collect(Collectors.toList());

            List<Post> posts = postDao.selectBatchIds(postIds);
            BeanUtils.copyProperties(postTagIPage, page = new Page<>());
            page.setRecords(posts);
        }

        boolean isLogin = StpUtil.isLogin();

        List<Post> records = page.getRecords();

        List<PostListDTO> postListDTOs = getPostListDTOS(isLogin, records);

        PageUtils pageUtils = new PageUtils(page);

        pageUtils.setList(postListDTOs);

        return Result.ok().put("data", pageUtils);
    }


    /**
     * 获取文章DTO集合的核心方法
     *
     * @param isLogin 用户是否登录
     * @param records 文章集合
     * @return List<PostListDTO> 文章DTO集合
     */
    private List<PostListDTO> getPostListDTOS(boolean isLogin, List<Post> records) {

        List<PostListDTO> postDTOList = new ArrayList<>(12);

        //通过stream处理获取到所有文章作者的id
        List<Long> memberIds = records.parallelStream().map(Post::getMemberUid).collect(Collectors.toList());

        //调用用户微服务，查询用户信息
        List<MemberDTO> memberInfoWithPost = memberFeignService.batchQueryUsers(memberIds);

        //遍历文章数据并
        for (int i = 0; i < records.size(); i++) {
            PostListDTO postListDTO = new PostListDTO();
            BeanUtils.copyProperties(records.get(i), postListDTO);
            postListDTO.setAuthorInfo(memberInfoWithPost.get(i));
            postListDTO.setCommentCount(getCommentCount(records.get(i)));
            if (isLogin) {
                postListDTO.setIsLike(checkIsThumbOrCollect(records.get(i).getUid(), StpUtil.getLoginIdAsLong(), 0));
                postListDTO.setIsCollect(checkIsThumbOrCollect(records.get(i).getUid(), StpUtil.getLoginIdAsLong(), 1));
            } else {
                postListDTO.setIsLike(false);
                postListDTO.setIsCollect(false);
            }
            postListDTO.setThumbCount(getThumbCount(records.get(i)));
            postDTOList.add(postListDTO);
        }


        return postDTOList;
    }

    /**
     * 发布/编辑文章
     *
     * @param postVo 文章vo
     * @return 保存结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result savePost(PostVO postVo) {
        Category category = categoryDao.selectById(postVo.getCategoryUid());
        if (category == null) {
            return Result.error("分类不存在");
        }

        List<Long> tagUid = postVo.getTagUid();
        if (CollUtil.isEmpty(tagUid)) {
            return Result.error("请选择标签");
        } else {
            List<Tag> tags = tagDao.selectBatchIds(tagUid);
            tags.parallelStream().forEach(tag -> {
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
        post.setMemberUid(StpUtil.getLoginIdAsLong());
        post.setStatus(PostConstant.CHECK_STATUS);
        


        int result = postDao.insert(post);
        if (result == 1) {
            //插入后通过消息队列对文章进行异步审核
            rabbitmqUtil.reviewPost(post, tagUid);
            log.info("返回文章的ID为：{}", post.getUid());
            return Result.ok().put("url", post.getUid());
        } else {
            log.error("用户={}发布文章失败,文章对象为={}", StpUtil.getLoginIdAsString(), postVo);
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

        boolean isLogin = StpUtil.isLogin();

        String key = RedisKeyConstant.POST_CACHE_KEY + uid;
        Post post;

        if (redisUtil.hasKey(key)) {
            return Result.error("没有该文章");
        }
        post = postDao.selectById(uid);

        if (post == null) {
            redisUtil.set(key, null, 60L * 60 * 3);
            return Result.error("没有该文章");
        }

        if (!isLogin || !Objects.equals(post.getMemberUid(), StpUtil.getLoginIdAsLong())) {
            if (Objects.equals(post.getIsPublish(), PostConstant.NOT_PUBLISH)) {
                return Result.error("该文章已设置私有");
            }
            if (!Objects.equals(post.getStatus(), PostConstant.NORMAL_STATUS)) {
                return Result.error("该文章由于其他原因不可展示");
            }
        }

        List<MemberDTO> memberInfoWithPost = memberFeignService
                .batchQueryUsers(CollUtil.toList(post.getMemberUid()));


        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        if (CollUtil.isNotEmpty(memberInfoWithPost)) {
            postDTO.setAuthorInfo(memberInfoWithPost.get(0));
            if (isLogin) {
                postDTO.setIsCollect(checkIsThumbOrCollect(uid, StpUtil.getLoginIdAsLong(), 1));
                postDTO.setIsLike(checkIsThumbOrCollect(uid, StpUtil.getLoginIdAsLong(), 0));
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

        if (!Objects.equals(post.getMemberUid(), StpUtil.getLoginIdAsLong())) {
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
        if (!StpUtil.isLogin() || !Objects.equals(queryParam.getUid(), StpUtil.getLoginIdAsLong())) {
            page = this.page(new Query<Post>()
                    .getPage(queryParam), new LambdaQueryWrapper<Post>()
                    .eq(Post::getMemberUid, queryParam.getUid())
                    .eq(Post::getStatus, PostConstant.NORMAL_STATUS)
                    .eq(Post::getIsPublish, PostConstant.PUBLISH));
        } else if (Objects.equals(queryParam.getUid(), StpUtil.getLoginIdAsLong())) {
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
        List<PostListDTO> postListDTOs = new ArrayList<>(10);
        MemberDTO memberDTO = memberFeignService.getMemberByUid(queryParam.getUid());
        postList.forEach(postEntity -> {
            PostListDTO postListDTO = new PostListDTO();
            BeanUtils.copyProperties(postEntity, postListDTO);
            postListDTO.setAuthorInfo(memberDTO);
            postListDTO.setThumbCount(getThumbCount(postEntity));
            if (StpUtil.isLogin()) {
                postListDTO.setIsLike(checkIsThumbOrCollect(postEntity.getUid(), StpUtil.getLoginIdAsLong(), 0));
                postListDTO.setIsCollect(checkIsThumbOrCollect(postEntity.getUid(), StpUtil.getLoginIdAsLong(), 1));
            } else {
                postListDTO.setIsLike(false);
                postListDTO.setIsCollect(false);
            }
            postListDTOs.add(postListDTO);
        });
        IPage<PostListDTO> pagePostListDTO = new Page<>();
        BeanUtils.copyProperties(page, pagePostListDTO);

        pagePostListDTO.setRecords(postListDTOs);

        PageUtils pageResult = new PageUtils(pagePostListDTO);
        return Result.ok().put("data", pageResult);
    }


    /**
     * 获取是否点赞或收藏
     *
     * @param uid             目标uid
     * @param loginUid 用户uid
     * @param type            类型
     * @return 是否点赞或是否收藏
     */
    private boolean checkIsThumbOrCollect(Long uid, Long loginUid, int type) {
        boolean result = false;
        /*
          判断是否点赞或是否收藏
         */
        if (type == 0) {
            String thumbRedisKey = RedisKeyConstant.getThumbKey(loginUid, uid);
            if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, thumbRedisKey)) {
                result = true;
            } else {
                //如果缓存不存在则去数据库中获取
                Thumb thumbResult = thumbDao.selectOne(new LambdaQueryWrapper<Thumb>()
                        .eq(Thumb::getMemberUid, loginUid)
                        .eq(Thumb::getType, PostConstant.THUMB_POST_TYPE)
                        .eq(Thumb::getPostUid, uid));
                if (thumbResult != null) {
                    result = true;
                }
            }
        } else if (type == 1) {
            String collectRedisKey = RedisKeyConstant.getThumbKey(loginUid, uid);
            if (redisUtil.hHasKey(RedisKeyConstant.POST_COLLECT_KEY, collectRedisKey)) {
                result = true;
            } else {
                Collect collectResult = collectDao.selectOne(new LambdaQueryWrapper<Collect>()
                        .eq(Collect::getMemberUid, loginUid)
                        .eq(Collect::getPostUid, uid));
                if (collectResult != null) {
                    result = true;
                }
            }
        } else {
            return false;
        }
        return result;
    }

    /**
     * 获取文章的总点赞
     *
     * @param post 文章实体
     * @return 点赞数
     */
    private int getThumbCount(Post post) {

        //先从缓存中获取再去数据库中获取
        String key = RedisKeyConstant.POST_COUNT_KEY + post.getUid();
        if (redisUtil.hHasKey(key, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
            return (int) redisUtil.hget(key, RedisKeyConstant.POST_THUMB_COUNT_KEY);
        }

        //缓存获取不到就数据库获取
        int thumbCount;
        synchronized (this) {
            if (redisUtil.hHasKey(key, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
                return (int) redisUtil.hget(key, RedisKeyConstant.POST_THUMB_COUNT_KEY);
            }
            Post postEntity = postDao.selectOne(new QueryWrapper<Post>().eq("uid", post.getUid()));
            thumbCount = postEntity.getThumbCount();
            redisUtil.hset(key, RedisKeyConstant.POST_THUMB_COUNT_KEY, thumbCount);
        }
        return thumbCount;
    }

    /**
     * 获取文章的评论总数
     *
     * @param post 文章实体类
     * @return 文章评论总数
     */
    private int getCommentCount(Post post) {

        Long postUid = post.getUid();

        String key = RedisKeyConstant.POST_COUNT_KEY + postUid;
        if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
            return (int) redisUtil.hget(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY);
        }
        int commentCount;
        synchronized (this) {
            if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
                return (int) redisUtil.hget(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY);
            }
            Post postEntity = postDao.selectOne(new QueryWrapper<Post>().eq("uid", post.getUid()));
            commentCount = postEntity.getCommentCount();
            redisUtil.hset(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, commentCount);
        }
        return commentCount;
    }


}