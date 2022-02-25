package cn.goroute.smart.post.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.post.entity.SectionEntity;

import java.util.Map;

/**
 * 板块表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:44:39
 */
public interface SectionService extends IService<SectionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

