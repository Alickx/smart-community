package cn.goroute.smart.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.common.entity.CollectEntity;

import java.util.Map;

/**
 * 收藏表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
public interface CollectService extends IService<CollectEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

