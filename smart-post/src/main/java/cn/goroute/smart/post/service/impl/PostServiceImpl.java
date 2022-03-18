package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.*;
import cn.goroute.smart.common.entity.dto.MemberDTO;
import cn.goroute.smart.common.entity.dto.PostDTO;
import cn.goroute.smart.common.entity.dto.PostListDTO;
import cn.goroute.smart.common.entity.pojo.*;
import cn.goroute.smart.common.entity.vo.PostQueryListVO;
import cn.goroute.smart.common.entity.vo.PostVO;
import cn.goroute.smart.common.utils.*;
import cn.goroute.smart.post.feign.MemberFeignService;
import cn.goroute.smart.post.feign.SearchFeignService;
import cn.goroute.smart.post.listener.PostReviewListener;
import cn.goroute.smart.post.service.PostService;
import cn.goroute.smart.post.util.Html2TextUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service("postService")
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostDao, PostEntity> implements PostService {

    @Resource
    MemberFeignService memberFeignService;

    @Resource
    SearchFeignService searchFeignService;

    @Autowired
    TagDao tagDao;

    @Autowired
    SectionDao sectionDao;

    @Autowired
    PostDao postDao;

    @Autowired
    PostTagDao postTagDao;

    @Autowired
    CommentDao commentDao;

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    RedisUtil redisUtil;

    @Autowired
    CollectDao collectDao;

    @Autowired
    PostReviewListener postReviewListener;

    /**
     * 文章分页方法
     *
     * @param queryParam 分页参数
     * @param sectionUid 分类uid
     * @param tagUid     标签uid
     * @return 文章分页对象
     * @throws IOException
     */
    @Override
    public PageUtils queryPage(QueryParam queryParam, Integer sectionUid, Integer tagUid) throws IOException {

        IPage<PostEntity> page;

        if (sectionUid == null) {
            page = this.page(
                    new Query<PostEntity>().getPage(queryParam),
                    new QueryWrapper<PostEntity>()
                            .eq("is_publish", PostConstant.PUBLISH)
                            .eq("status", PostConstant.NORMAL_STATUS)
            );
        } else if (tagUid == null) {

            page = this.page(
                    new Query<PostEntity>().getPage(queryParam),
                    new QueryWrapper<PostEntity>()
                            .eq("section_uid", sectionUid)
                            .eq("status", PostConstant.NORMAL_STATUS)
            );
        } else {

            page = postDao.getPostBySectionTag(new Query<PostEntity>()
                            .getPage(queryParam),
                    queryParam.getSidx(),
                    sectionUid,
                    tagUid,
                    PostConstant.PUBLISH,
                    PostConstant.NORMAL_STATUS);

        }

        List<PostListDTO> postDTOList = getPostDTOList(page);

        PageUtils pageUtils = new PageUtils(page);

        pageUtils.setList(postDTOList);

        return pageUtils;
    }


    /**
     * 根据文章page获取所有文章DTO对象
     *
     * @param page
     * @return
     * @throws IOException
     */
    public List<PostListDTO> getPostDTOList(IPage<PostEntity> page) throws IOException {

        boolean isLogin = StpUtil.isLogin();

        List<PostEntity> records = page.getRecords();

        return getPostListDTOS(isLogin, records);
    }

    /**
     * 获取文章DTO集合的核心方法
     *
     * @param isLogin 用户是否登录
     * @param records 文章集合
     * @return List<PostListDTO> 文章DTO集合
     * @throws IOException
     */
    private List<PostListDTO> getPostListDTOS(boolean isLogin, List<PostEntity> records) throws IOException {

        List<PostListDTO> postDTOList = new ArrayList<>(10);

        //通过stream处理获取到所有文章作者的id
        List<String> memberIds = records.stream().map(PostEntity::getMemberUid).collect(Collectors.toList());

        //调用用户微服务，查询用户信息
        List<MemberDTO> memberInfoWithPost = memberFeignService.getMemberInfoWithPost(memberIds);

        //遍历文章数据并
        for (int i = 0; i < records.size(); i++) {
            PostListDTO postListDTO = new PostListDTO();
            BeanUtils.copyProperties(records.get(i), postListDTO);
            postListDTO.setAuthorInfo(memberInfoWithPost.get(i));
            postListDTO.setCommentCount(getCommentCount(records.get(i)));
            if (isLogin) {
                postListDTO.setIsLike(checkIsThumbOrCollect(records.get(i).getUid(), StpUtil.getLoginIdAsString(), 0));
                postListDTO.setIsCollect(checkIsThumbOrCollect(records.get(i).getUid(), StpUtil.getLoginIdAsString(), 1));
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
     * 返回一个文章DTO集合
     *
     * @param postUids 文章id集合
     * @return 文章DTO List
     */
    public List<PostListDTO> getPostDTOList(List<String> postUids) throws IOException {

        boolean isLogin = StpUtil.isLogin();

        List<PostEntity> postEntities = postDao.selectBatchIds(postUids);

        return getPostListDTOS(isLogin, postEntities);

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

        StpUtil.checkLogin();

        SectionEntity sectionEntity = sectionDao.selectById(postVo.getSectionUid());
        if (sectionEntity == null) {
            return Result.error("分类不存在");
        }

        List<Integer> tageUidList = postVo.getTagUid();
        if (CollectionUtil.isNotEmpty(tageUidList)) {
            for (int tageUid : tageUidList) {
                TagEntity tagEntity = tagDao.selectById(tageUid);
                if (tagEntity == null) {
                    return Result.error("标签不存在!");
                }
            }
        }

        PostEntity postEntity = new PostEntity();

        //使用正则，过滤掉Html标签
        String htmlContent = postVo.getContentHtml();
        String text = Html2TextUtil.Html2Text(htmlContent);


        if (StrUtil.isEmpty(postVo.getSummary())) {
            String summary = StrUtil.sub(text, 0, 150);
            postEntity.setSummary(summary);
        } else {
            postEntity.setSummary(postVo.getSummary());
        }

        postEntity.setIsPublish(postVo.getIsPublish() ? PostConstant.PUBLISH : PostConstant.NOT_PUBLISH);
        postEntity.setTitle(postVo.getTitle());
        postEntity.setContent(postVo.getContent());
        postEntity.setSectionUid(postVo.getSectionUid());
        postEntity.setMemberUid(StpUtil.getLoginIdAsString());
        postEntity.setStatus(PostConstant.CHECK_STATUS);

        int result = postDao.insert(postEntity);
        if (result == 1) {
            //插入后通过消息队列对文章进行异步审核
            postReviewListener.review(postEntity, tageUidList);
            return Result.ok().put("url", postEntity.getUid());
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
    public Result getPostByUid(String uid) {

        boolean isLogin = StpUtil.isLogin();

        String key = RedisKeyConstant.POST_CACHE_KEY + uid;
        PostEntity post;

        if (redisUtil.hasKey(key)) {
            return Result.error("没有该文章");
        }
        post = postDao.selectById(uid);

        if (post == null) {
            redisUtil.set(key, null, 60L * 60 * 3);
            return Result.error("没有该文章");
        }

        if (!isLogin || !Objects.equals(post.getMemberUid(), StpUtil.getLoginIdAsString())) {
            if (Objects.equals(post.getIsPublish(), PostConstant.NOT_PUBLISH)) {
                return Result.error("该文章已设置私有");
            }
            if (!Objects.equals(post.getStatus(), PostConstant.NORMAL_STATUS)) {
                return Result.error("该文章由于其他原因不可展示");
            }
        }

        List<MemberDTO> memberInfoWithPost = memberFeignService
                .getMemberInfoWithPost(CollectionUtil.toList(post.getMemberUid()));


        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        if (CollectionUtil.isNotEmpty(memberInfoWithPost)) {
            postDTO.setAuthorInfo(memberInfoWithPost.get(0));
            if (isLogin) {
                postDTO.setIsCollect(checkIsThumbOrCollect(uid, StpUtil.getLoginIdAsString(), 1));
                postDTO.setIsLike(checkIsThumbOrCollect(uid, StpUtil.getLoginIdAsString(), 0));
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
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result deletePost(String postUid) {
        if (StrUtil.isEmpty(postUid)) {
            return Result.error();
        }

        PostEntity postEntity = postDao.selectById(postUid);

        if (!Objects.equals(postEntity.getMemberUid(), StpUtil.getLoginIdAsString())) {
            return Result.error();
        }

        postEntity.setStatus(PostConstant.DELETE_STATUS);
        postDao.updateById(postEntity);
        //调用es接口逻辑删除文章
        searchFeignService.deleteSearchPost(postUid);
        return Result.ok("文章删除成功");
    }

    /**
     * 根据用户uid查发布文章
     *
     * @param postQueryListVO 查询模型
     * @return 分页结果
     */
    @Override
    public Result listByMemberUid(PostQueryListVO postQueryListVO) {

        QueryParam queryParam = new QueryParam();
        BeanUtils.copyProperties(postQueryListVO, queryParam);

        IPage<PostEntity> page = this.page(new Query<PostEntity>()
                .getPage(queryParam), new QueryWrapper<PostEntity>()
                .eq("member_uid", postQueryListVO.getMemberUid())
                .eq("status", PostConstant.NORMAL_STATUS)
                .orderByAsc("created_time")
                .eq("is_publish", PostConstant.PUBLISH));

        List<PostEntity> postEntityList = page.getRecords();
        if (CollectionUtil.isEmpty(postEntityList)) {
            return Result.ok().put("data", new PageUtils(page));
        }
        List<PostListDTO> postListDTOs = new ArrayList<>(10);
        MemberDTO memberDTO = memberFeignService.getMemberByUid(postQueryListVO.getMemberUid());
        postEntityList.forEach(postEntity -> {
            PostListDTO postListDTO = new PostListDTO();
            BeanUtils.copyProperties(postEntity, postListDTO);
            postListDTO.setAuthorInfo(memberDTO);
            try {
                postListDTO.setThumbCount(getThumbCount(postEntity));
            } catch (IOException e) {
                log.error("获取文章总点赞数失败,e={}", e.getMessage());
            }
            if (StpUtil.isLogin()) {
                postListDTO.setIsLike(checkIsThumbOrCollect(postEntity.getUid(), StpUtil.getLoginIdAsString(), 0));
                postListDTO.setIsCollect(checkIsThumbOrCollect(postEntity.getUid(), StpUtil.getLoginIdAsString(), 1));
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
     * @param loginIdAsString 用户uid
     * @param type            类型
     * @return 是否点赞或是否收藏
     */
    private boolean checkIsThumbOrCollect(String uid, String loginIdAsString, int type) {
        boolean result = false;
        /*
           type = 0 获取是否点赞
           type = 1 获取是否收藏
         */
        switch (type) {
            case 0: {
                //如果缓存存在，则证明点赞
                String thumbRedisKey = RedisKeyConstant.getThumbOrCollectKey(loginIdAsString, uid);
                if (redisUtil.hHasKey(RedisKeyConstant.POST_THUMB_KEY, thumbRedisKey)) {
                    result = true;
                } else {
                    //如果缓存不存在则去数据库中获取
                    Comment thumbResult = commentDao.selectOne(new QueryWrapper<Comment>()
                            .eq("member_uid", loginIdAsString)
                            .eq("to_uid", uid));
                    if (thumbResult != null) {
                        result = true;
                    }
                }
                break;
            }
            case 1: {
                String collectRedisKey = RedisKeyConstant.getThumbOrCollectKey(loginIdAsString, uid);
                if (redisUtil.hHasKey(RedisKeyConstant.POST_COLLECT_KEY, collectRedisKey)) {
                    result = true;
                } else {
                    CollectEntity collectResult = collectDao.selectOne(new QueryWrapper<CollectEntity>()
                            .eq("member_uid", loginIdAsString)
                            .eq("post_uid", uid));
                    if (collectResult != null) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    /**
     * 获取文章的总点赞
     *
     * @param post 文章实体
     * @return 点赞数
     */
    private int getThumbCount(PostEntity post) throws IOException {

        //先从缓存中获取再去数据库中获取
        String key = RedisKeyConstant.POST_COUNT_KEY + post.getUid();
        if (redisUtil.hHasKey(key, RedisKeyConstant.POST_THUMB_COUNT_KEY)) {
            return (int) redisUtil.hget(key, RedisKeyConstant.POST_THUMB_COUNT_KEY);
        }

        //缓存获取不到就数据库获取
        int thumbCount;
        synchronized (this) {
            PostEntity postEntity = postDao.selectOne(new QueryWrapper<PostEntity>().eq("uid", post.getUid()));
            thumbCount = postEntity.getThumbCount();
        }
        //同时存储到redis中
        redisUtil.hset(key, RedisKeyConstant.POST_THUMB_COUNT_KEY, thumbCount);
        return thumbCount;
    }

    /**
     * 获取文章的评论总数
     *
     * @param post 文章实体类
     * @return 文章评论总数
     */
    private int getCommentCount(PostEntity post) {

        String postUid = post.getUid();

        String key = RedisKeyConstant.POST_COUNT_KEY + postUid;
        if (redisUtil.hHasKey(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY)) {
            return (int) redisUtil.hget(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY);
        }
        int commentCount;
        synchronized (this) {
            PostEntity postEntity = postDao.selectOne(new QueryWrapper<PostEntity>().eq("uid", post.getUid()));
            commentCount = postEntity.getCommentCount();
        }

        redisUtil.hset(key, RedisKeyConstant.POST_COMMENT_COUNT_KEY, commentCount);
        return commentCount;
    }


}