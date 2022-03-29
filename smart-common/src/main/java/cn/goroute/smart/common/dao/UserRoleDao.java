package cn.goroute.smart.common.dao;

import cn.goroute.smart.common.entity.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@Mapper
public interface UserRoleDao extends BaseMapper<UserRole> {
	
}
