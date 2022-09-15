package cn.goroute.smart.gateway.mapper;

import cn.goroute.smart.gateway.entity.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hccake.extend.mybatis.plus.mapper.ExtendMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 权限表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@Mapper
public interface PermissionMapper extends ExtendMapper<Permission> {

	
}
