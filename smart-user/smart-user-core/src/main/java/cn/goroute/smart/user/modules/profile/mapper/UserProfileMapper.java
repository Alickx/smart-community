package cn.goroute.smart.user.modules.profile.mapper;

import cn.goroute.smart.user.domain.entity.UserProfileEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author caiguopeng
 * @description 针对表【user_profile(用户信息表)】的数据库操作Mapper
 * @createDate 2022-09-17 19:30:44
 * @Entity cn.goroute.smart.user.domain.entity.UserProfile
 */
public interface UserProfileMapper extends BaseMapper<UserProfileEntity> {

    void updateIncrArticleNum(@Param("userId") Long userId);

    void updateIncrFansNum(@Param("toUserId") Long toUserId);

    void updateDecrFansNum(@Param("toUserId") Long toUserId);
}




