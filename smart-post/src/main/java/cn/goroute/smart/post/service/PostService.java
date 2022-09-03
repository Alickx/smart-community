package cn.goroute.smart.post.service;

import cn.goroute.smart.common.entity.resp.Response;
import cn.goroute.smart.post.entity.pojo.Post;
import cn.goroute.smart.post.entity.vo.PostQueryVo;
import cn.goroute.smart.post.entity.vo.PostVo;
import cn.goroute.smart.common.utils.QueryParam;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 文章表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
public interface PostService extends IService<Post> {

    Response queryPage(PostQueryVo postQueryVO);

    Response savePost(PostVo postVo);

    Response getPostByUid(Long uid);

    Response deletePost(Long postUid);

    Response listByMemberUid(QueryParam queryParam);
}

