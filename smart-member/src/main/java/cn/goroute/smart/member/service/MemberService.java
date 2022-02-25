package cn.goroute.smart.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import cn.goroute.smart.common.utils.PageUtils;
import cn.goroute.smart.member.entity.MemberEntity;

import java.util.Map;

/**
 * 用户信息表
 *
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

