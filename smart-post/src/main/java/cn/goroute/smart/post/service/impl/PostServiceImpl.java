package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.dao.*;
import cn.goroute.smart.common.entity.*;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.common.utils.RedisUtil;
import cn.goroute.smart.post.feign.MemberFeignService;
import cn.goroute.smart.post.service.PostService;
import cn.goroute.smart.post.util.Html2TextUtil;
import cn.goroute.smart.post.util.PostSaveEsUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static cn.goroute.smart.common.utils.Constant.CloudService.getThumbOrCollectKey;
import static cn.goroute.smart.common.utils.Constant.*;


@Service("postService")
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostDao, PostEntity> implements PostService {


    @Resource
    MemberFeignService memberFeignService;

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
    PostSaveEsUtil postSaveEsUtil;


    @Override
    public PageUtils queryPage(Integer curPage, Integer sectionUid, Integer tagUid) throws IOException {

        IPage<PostEntity> page;

        if (sectionUid == null) {
            page = this.page(
                    new Query<PostEntity>().getPage(curPage),
                    new QueryWrapper<PostEntity>().eq("status", 0)
            );
        } else if (tagUid == null) {

            page = this.page(
                    new Query<PostEntity>().getPage(curPage),
                    new QueryWrapper<PostEntity>()
                            .eq("section_uid", sectionUid)
                            .eq("status", 0)
            );
        } else {

            page = postDao.getPostBySectionTag(new Query<PostEntity>()
                    .getPage(curPage), sectionUid, tagUid);

        }

        List<PostListDTO> postDTOList = getPostDTOList(page);

        PageUtils pageUtils = new PageUtils(page);

        pageUtils.setList(postDTOList);

        return pageUtils;
    }


    private List<PostListDTO> getPostDTOList(IPage<PostEntity> page) throws IOException {
        List<PostListDTO> postDTOList = new ArrayList<>();
        String loginIdAsString = StpUtil.getLoginIdAsString();

        List<PostEntity> records = page.getRecords();

        List<String> memberIds = records.stream().map(PostEntity::getMemberUid).collect(Collectors.toList());

        //遍历集合并获取点赞和收藏信息

        List<MemberDTO> memberInfoWithPost = memberFeignService.getMemberInfoWithPost(memberIds);


        for (int i = 0; i < records.size(); i++) {
            PostListDTO postListDTO = new PostListDTO();
            BeanUtils.copyProperties(records.get(i), postListDTO);
            postListDTO.setAuthorInfo(memberInfoWithPost.get(i));
            postListDTO.setIsLike(checkIsThumbOrCollect(records.get(i).getUid(), loginIdAsString, 0));
            postListDTO.setIsCollect(checkIsThumbOrCollect(records.get(i).getUid(), loginIdAsString, 1));
            postListDTO.setThumbCount(getThumbCount(records.get(i)));
            postDTOList.add(postListDTO);
        }


        return postDTOList;
    }

    /**
     * 发布/编辑文章
     *
     * @param postVo
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public R savePost(PostVo postVo) {

        StpUtil.checkLogin();

        SectionEntity sectionEntity = sectionDao.selectById(postVo.getSectionUid());
        if (sectionEntity == null) {
            return R.error("分类不存在");
        }

        List<Integer> tageUidList = postVo.getTagUid();
        if (CollectionUtil.isNotEmpty(tageUidList)) {
            for (int tageUid : tageUidList) {
                TagEntity tagEntity = tagDao.selectById(tageUid);
                if (tagEntity == null) {
                    return R.error("标签不存在!");
                }
            }
        }

        //TODO 处理图片

        PostEntity postEntity = new PostEntity();

        //使用正则，过滤掉Html标签
        String htmlContent = postVo.getContentHtml();
        String text = Html2TextUtil.Html2Text(htmlContent);


        if (StrUtil.isEmpty(postVo.getSummary())) {
            //使用HanLP(汉语言处理包)处理 自动摘要
            String autoSummary = HanLP.getSummary(text, 150);
            postEntity.setSummary(autoSummary);
        } else {
            postEntity.setSummary(postVo.getSummary());
        }

        postEntity.setIsPublish(postVo.getIsPublish() ? "1" : "0");
        postEntity.setTitle(postVo.getTitle());
        postEntity.setContent(postVo.getContent());
        postEntity.setSectionUid(postVo.getSectionUid());
        postEntity.setMemberUid(StpUtil.getLoginIdAsString());

        int result = postDao.insert(postEntity);
        if (result == 1) {
            //插入Mysql数据库后更换为过滤掉html标签的文本存入ES中
            postEntity.setContent(text);
            postSaveEsUtil.saveEs(postEntity);
            tageUidList.forEach(t -> {
                PostTagEntity postTagEntity = new PostTagEntity();
                postTagEntity.setPostUid(postEntity.getUid());
                postTagEntity.setTagUid(t);
                postTagDao.insert(postTagEntity);
            });
            return R.ok().put("url", postEntity.getUid());
        } else {
            log.error("用户={}发布文章失败,文章对象为={}", StpUtil.getLoginIdAsString(), postVo);
            return R.error("发布文章失败");
        }
    }

    @Override
    public R getPostByUid(String uid) {
        String loginIdAsString = StpUtil.getLoginIdAsString();
        String key = POST_CACHE_KEY + uid;
        PostEntity post = new PostEntity();

        if (redisUtil.hasKey(key)) {
            if (!StrUtil.isEmpty(redisUtil.get(key).toString())) {
                post = JSONObject.parseObject(redisUtil.get(key).toString(), PostEntity.class);
            }
        } else {
            post = postDao.selectById(uid);
            if (post == null) {
                redisUtil.set(key, null, 60L * 60 * 3);
                return R.error("没有该文章");
            }
            redisUtil.set(key, JSONObject.toJSONString(post), 60L * 60 * RandomUtil.randomInt(20, 24) * RandomUtil.randomInt(3, 5));
        }
        List<MemberDTO> memberInfoWithPost = memberFeignService
                .getMemberInfoWithPost(CollectionUtil.toList(post.getMemberUid()));

        boolean isLike;
        boolean isCollect;
        //获取收藏和点赞的信息
        isLike = checkIsThumbOrCollect(uid, loginIdAsString, 0);

        isCollect = checkIsThumbOrCollect(uid, loginIdAsString, 1);

        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        if (CollectionUtil.isNotEmpty(memberInfoWithPost)) {
            postDTO.setAuthorInfo(memberInfoWithPost.get(0));
            postDTO.setIsCollect(isCollect);
            postDTO.setIsLike(isLike);
        }

        return Objects.requireNonNull(R.ok().put("data", postDTO));
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
                Comment thumbResult = commentDao.selectOne(new QueryWrapper<Comment>()
                        .eq("member_uid", loginIdAsString)
                        .eq("to_uid", uid));


                if (thumbResult != null) {
                    result = true;
                } else {
                    //如果数据库获取的数据是null，就去缓存拿
                    String thumbRedisKey = getThumbOrCollectKey(loginIdAsString, uid);
                    if (redisUtil.hHasKey(POST_THUMB_KEY, thumbRedisKey)) {
                        result = true;
                    }
                }
                break;
            }
            case 1: {
                CollectEntity collectResult = collectDao.selectOne(new QueryWrapper<CollectEntity>()
                        .eq("member_uid", loginIdAsString)
                        .eq("post_uid", uid));

                if (collectResult != null) {
                    result = true;
                } else {
                    //如果数据库获取的数据是null，就去缓存拿
                    String collectRedisKey = getThumbOrCollectKey(loginIdAsString, uid);
                    if (redisUtil.hHasKey(POST_COLLECT_KEY, collectRedisKey)) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    private int getThumbCount(PostEntity post) throws IOException {
        int thumbCount;

        List<Comment> thumbList = commentDao.selectList(new QueryWrapper<Comment>()
                .eq("type", 1)
                .eq("to_uid", post.getUid()));

        thumbCount = thumbList.size();

        String thumbScanKey = "*::" + post.getUid();
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash()
                .scan(POST_THUMB_KEY, ScanOptions.scanOptions().match(thumbScanKey).build());

        while (cursor.hasNext()) {
            thumbCount++;
            cursor.next();
        }

        cursor.close();

        return thumbCount;
    }

}