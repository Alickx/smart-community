package cn.goroute.smart.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.post.entity.PostTagEntity;

import java.util.Map;

/**
 * 文章标签表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
public interface PostTagService extends IService<PostTagEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

