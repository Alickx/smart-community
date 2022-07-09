package cn.goroute.smart.post.manage;

import cn.goroute.smart.common.entity.dto.PostListDto;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2022/06/08/21:24
 * @Description:
 */
public interface IThumbManage {

    /**
     * 获取文章列表DTO
     * @param postIdList 文章ID列表
     * @return 文章列表DTO
     */
    List<PostListDto> getPostDTOListByPostIdList(List<Long> postIdList);
}
