package cn.goroute.smart.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.post.entity.PostEntity;

import java.util.Map;

/**
 * 文章表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
public interface PostService extends IService<PostEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

