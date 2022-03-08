package cn.goroute.smart.post.service;

import cn.goroute.smart.common.entity.PostEntity;
import cn.goroute.smart.common.entity.PostVo;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.utils.R;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.IOException;

/**
 * 文章表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
public interface PostService extends IService<PostEntity> {

    PageUtils queryPage(Integer curPage,Integer sectionUid,Integer tagUid) throws IOException;

    R savePost(PostVo postVo);

    R getPostByUid(String uid);
}

