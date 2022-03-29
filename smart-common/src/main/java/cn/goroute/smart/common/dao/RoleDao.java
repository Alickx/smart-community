package cn.goroute.smart.common.dao;

import cn.goroute.smart.common.entity.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-28 18:52:38
 */
@Mapper
public interface RoleDao extends BaseMapper<Role> {

    List<String> getRoleNameByMemberUid(String memberUid);

}
