package cn.goroute.smart.member.dao;

import cn.goroute.smart.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
