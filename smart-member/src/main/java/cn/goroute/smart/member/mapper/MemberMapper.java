package cn.goroute.smart.member.mapper;

import cn.goroute.smart.member.entity.pojo.Member;
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
public interface MemberMapper extends BaseMapper<Member> {

    void updateMemberStatus(@Param("memberId") String memberId,@Param("status") Integer banType);

    Member selectByUid(@Param("uid") Long uid);
}
