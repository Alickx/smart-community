package cn.goroute.smart.post.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.goroute.smart.common.entity.*;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.Query;
import cn.goroute.smart.common.utils.R;
import cn.goroute.smart.post.dao.PostDao;
import cn.goroute.smart.post.dao.SectionDao;
import cn.goroute.smart.post.dao.TagDao;
import cn.goroute.smart.post.feign.MemberFeignService;
import cn.goroute.smart.post.service.PostService;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hankcs.hanlp.HanLP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("postService")
@Slf4j
public class PostServiceImpl extends ServiceImpl<PostDao, PostEntity> implements PostService {

    private static final Integer POST_THUMB_TYPE = 0;

    @Resource
    MemberFeignService memberFeignService;

    @Autowired
    TagDao tagDao;

    @Autowired
    SectionDao sectionDao;

    @Autowired
    PostDao postDao;


    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PostEntity> page = this.page(
                new Query<PostEntity>().getPage(params),
                new QueryWrapper<PostEntity>()
        );

        List<PostDTO> postDTOList = getPostDTOList(page);

        PageUtils pageUtils = new PageUtils(page);

        pageUtils.setList(postDTOList);

        return pageUtils;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, String sectionUid) {

        IPage<PostEntity> page = this.page(
                new Query<PostEntity>().getPage(params),
                new QueryWrapper<PostEntity>().eq("section_uid", sectionUid)
        );

        List<PostDTO> postDTOList = getPostDTOList(page);
        PageUtils pageDTO = new PageUtils(page);

        pageDTO.setList(postDTOList);

        return pageDTO;
    }

    private List<PostDTO> getPostDTOList(IPage<PostEntity> page) {
        List<PostDTO> postDTOList = new ArrayList<>();

        String loginIdAsString = StpUtil.getLoginIdAsString();
        //遍历集合并获取点赞和收藏信息
        for (PostEntity post : page.getRecords()) {
            //调用接口查询用户点赞和收藏的信息
            //调用用户微服务
            R memberInfoWithPost = memberFeignService.getMemberInfoWithPost(loginIdAsString, POST_THUMB_TYPE, post.getUid());
            PostDTO postDTO = new PostDTO();
            //解析返回结果
            MemberDTO memberDTO = JSONObject.parseObject(JSONObject.toJSONString(memberInfoWithPost.get("authorInfo")), MemberDTO.class);
            Boolean isLike = (Boolean) memberInfoWithPost.get("isLike");
            Boolean isCollect = (Boolean) memberInfoWithPost.get("isCollect");
            BeanUtils.copyProperties(post, postDTO);
            //设置作者信息，点赞和收藏信息
            postDTO.setAuthorInfo(memberDTO);
            postDTO.setIsLike(isLike);
            postDTO.setIsCollect(isCollect);
            postDTOList.add(postDTO);
        }
        return postDTOList;
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, String sectionUid, String tagUid) {
        IPage<PostEntity> page = this.page(
                new Query<PostEntity>().getPage(params),
                new QueryWrapper<PostEntity>()
                        .eq("section_uid", sectionUid)
                        .eq("tag_uid", tagUid)
        );

        List<PostDTO> postDTOList = getPostDTOList(page);

        PageUtils pageUtils = new PageUtils(page);

        pageUtils.setList(postDTOList);

        return pageUtils;
    }

    /**
     * 发布/编辑文章
     *
     * @param postVo
     * @return
     */
    @Override
    public R savePost(PostVo postVo) {

        StpUtil.checkLogin();

        SectionEntity sectionEntity = sectionDao.selectById(postVo.getSectionUid());
        if (sectionEntity == null) {
            return R.error("分类不存在");
        }

        List<String> tageUidList = postVo.getTagUid();
        if (CollectionUtil.isNotEmpty(tageUidList)) {
            for (String tageUid : tageUidList) {
                TagEntity tagEntity = tagDao.selectById(tageUid);
                if (tagEntity == null) {
                    return R.error("标签不存在!");
                }
            }
        }

        //TODO 处理图片

        PostEntity postEntity = new PostEntity();

        if (StrUtil.isEmpty(postVo.getSummary())) {
            //使用HanLP(汉语言处理包)处理 自动摘要
            String autoSummary = HanLP.getSummary(postVo.getContent(), 50);
            postEntity.setSummary(autoSummary);
        }

        postEntity.setIsPublish(postVo.getIsPublish() ? "1" : "0");

        postEntity.setTitle(postVo.getTitle());
        postEntity.setContent(postVo.getContent());
        postEntity.setSectionUid(postVo.getSectionUid());
        postEntity.setMemberUid(StpUtil.getLoginIdAsString());


        int result = postDao.insert(postEntity);
        if (result == 1) {
            return R.ok().put("url", postEntity.getUid());
        } else {
            log.error("用户={}发布文章失败,文章对象为={}",StpUtil.getLoginIdAsString(), postVo);
            return R.error("发布文章失败");
        }



    }

}