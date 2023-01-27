package cn.goroute.smart.user.converter;

import cn.goroute.smart.common.model.dto.UserProfileDTO;
import cn.goroute.smart.user.domain.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: 蔡国鹏
 * @Date: 2022/09/27/11:09
 * @Description: 用户信息转换器
 */
@Mapper
public interface UserProfileConverter {

	UserProfileConverter INSTANCE = Mappers.getMapper(UserProfileConverter.class);

	/**
	 * PO 转 DTO
	 * @param userProfile 用户信息
	 * @return UserProfileDTO 用户信息DTO
	 */
	UserProfileDTO poToDto(UserProfile userProfile);


	/**
	 * 批量 PO 转 DTO
	 * @param userProfile 用户信息
	 * @return List<UserProfileDTO> 用户信息DTO
	 */
	List<UserProfileDTO> poToDto(List<UserProfile> userProfile);

}
