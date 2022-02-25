package cn.goroute.smart.comment.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.comment.entity.CommentEntity;

import java.util.Map;

/**
 * 文章回复表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:34:33
 */
public interface CommentService extends IService<CommentEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

