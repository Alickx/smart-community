package cn.goroute.smart.gateway.mapper;

import cn.goroute.smart.gateway.entity.pojo.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关联表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@Mapper
public interface UserRoleMapper extends ExtendMapper<UserRole> {
	
}
