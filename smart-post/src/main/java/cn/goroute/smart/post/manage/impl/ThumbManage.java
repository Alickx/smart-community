package cn.goroute.smart.post.manage.impl;

import cn.goroute.smart.post.mapper.PostMapper;
import cn.goroute.smart.common.entity.dto.MemberDto;
import cn.goroute.smart.common.entity.dto.PostListDto;
import cn.goroute.smart.common.entity.pojo.Post;
import cn.goroute.smart.common.feign.MemberFeignService;
import cn.goroute.smart.post.manage.IPostManage;
import cn.goroute.smart.post.manage.IThumbManage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/06/08/21:24
 * @Description:
 */
@Service
public class ThumbManage implements IThumbManage {

    @Autowired
    PostMapper postMapper;

    @Autowired
    MemberFeignService memberFeignService;

    @Autowired
    IPostManage iPostManage;

    /**
     * 获取文章列表DTO
     *
     * @param postIdList 文章ID列表
     * @return 文章列表DTO
     */
    @Override
    public List<PostListDto> getPostDTOListByPostIdList(List<Long> postIdList) {

        if (postIdList == null || postIdList.size() == 0) {
            return new ArrayList<>(0);
        }

        List<Post> posts = postMapper.selectBatchIds(postIdList);

        List<PostListDto> result = new ArrayList<>(posts.size());
        for (Post post : posts) {
            PostListDto postListDTO = new PostListDto();
            Long memberUid = post.getMemberUid();
            MemberDto member = memberFeignService.getMemberByUid(memberUid);
            postListDTO.setAuthorInfo(member);
            BeanUtils.copyProperties(post, postListDTO);
            postListDTO.setIsLike(iPostManage.checkIsThumbOrCollect(post.getUid(), memberUid, 0));
            postListDTO.setIsCollect(iPostManage.checkIsThumbOrCollect(post.getUid(), memberUid, 1));
            result.add(postListDTO);
        }
        return result;
    }

}
