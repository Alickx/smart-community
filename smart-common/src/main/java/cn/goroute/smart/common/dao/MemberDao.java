package cn.goroute.smart.common.dao;

import cn.goroute.smart.common.entity.pojo.Member;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户信息表
 * 
 * @author Alickx
 * @email llwstu@gmail.com
 * @date 2022-02-25 09:45:33
 */
@Mapper
public interface MemberDao extends BaseMapper<Member> {

    void updateMemberStatus(@Param("memberId") String memberId,@Param("status") Integer banType);

    Member selectByUid(@Param("uid") Long uid);
}
