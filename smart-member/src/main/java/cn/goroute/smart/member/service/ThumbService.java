package cn.goroute.smart.member.service;

import cn.goroute.smart.common.utils.PageUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.goroute.smart.common.entity.ThumbEntity;

import java.util.Map;

/**
 * 点赞记录表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-26 16:17:45
 */
public interface ThumbService extends IService<ThumbEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

