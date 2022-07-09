package cn.goroute.smart.post.service;

import cn.goroute.smart.common.entity.pojo.Post;
import cn.goroute.smart.common.entity.vo.PostQueryVo;
import cn.goroute.smart.common.entity.vo.PostVo;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 文章表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
public interface PostService extends IService<Post> {

    Result queryPage(PostQueryVo postQueryVO);

    Result savePost(PostVo postVo);

    Result getPostByUid(Long uid);

    Result deletePost(Long postUid);

    Result listByMemberUid(QueryParam queryParam);
}

