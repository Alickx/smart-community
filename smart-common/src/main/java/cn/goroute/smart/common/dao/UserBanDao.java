package cn.goroute.smart.common.dao;

import cn.goroute.smart.common.entity.pojo.UserBan;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Alickx
 * @description 针对表【t_user_ban(封禁表)】的数据库操作Mapper
 * @createDate 2022-05-23 20:44:28
 * @Entity cn.goroute.smart.common.entity.pojo.UserBan
 */
@Mapper
public interface UserBanDao extends BaseMapper<UserBan> {

    /**
     * @param memberId 用户ID
     * @param banType
     * @return UserBan
     * @description 根据用户ID查询用户封禁信息
     */
    UserBan checkUserBan(@Param("banUserId") String memberId,@Param("banType") Integer banType);
}




