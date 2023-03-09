package cn.goroute.smart.user.modules.follow.converter;

import cn.goroute.smart.user.domain.entity.UserFollowEntity;
import cn.goroute.smart.user.domain.vo.UserFollowVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: Alickx
 * @Date: 2023/03/05/11:17
 * @Description:
 */
@Mapper
public interface UserFollowConverter {

    UserFollowConverter INSTANCE = Mappers.getMapper(UserFollowConverter.class);

    List<UserFollowVO> poToVo(List<UserFollowEntity> userFollowEntityList);

}
