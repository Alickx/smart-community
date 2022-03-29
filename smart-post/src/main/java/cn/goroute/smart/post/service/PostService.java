package cn.goroute.smart.post.service;

import cn.goroute.smart.common.entity.pojo.Post;
import cn.goroute.smart.common.entity.vo.PostQueryListVO;
import cn.goroute.smart.common.entity.vo.PostVO;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.QueryParam;
import cn.goroute.smart.common.utils.Result;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * 文章表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
public interface PostService extends IService<Post> {

    PageUtils queryPage(QueryParam queryParam, Integer sectionUid, Integer tagUid) throws IOException;

    Result savePost(PostVO postVo);

    Result getPostByUid(String uid);

    Result deletePost(String postUid);

    Result listByMemberUid(PostQueryListVO postQueryListVo);
}

