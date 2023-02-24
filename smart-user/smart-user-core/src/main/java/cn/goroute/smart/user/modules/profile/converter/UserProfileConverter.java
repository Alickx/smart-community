package cn.goroute.smart.user.modules.profile.converter;

import cn.goroute.smart.auth.domain.dto.AuthUserDTO;
import cn.goroute.smart.user.domain.entity.UserProfileEntity;
import cn.goroute.smart.user.domain.dto.UserProfileDTO;
import cn.goroute.smart.user.domain.vo.UserProfileVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
	 * @param userProfileEntity 用户信息
	 * @return UserProfileDTO 用户信息DTO
	 */
	UserProfileDTO poToDto(UserProfileEntity userProfileEntity);


	/**
	 * 批量 PO 转 DTO
	 * @param userProfileEntity 用户信息
	 * @return List<UserProfileDTO> 用户信息DTO
	 */
	List<UserProfileDTO> poToDto(List<UserProfileEntity> userProfileEntity);

	UserProfileVO poToVo(UserProfileEntity userProfileEntity);

	List<UserProfileVO> poToVo(List<UserProfileEntity> userProfileEntity);

	UserProfileEntity dtoToPo(UserProfileDTO userProfileDTO);


	@Mapping(target = "userId", source = "id")
	@Mapping(target = "nickName", source = "userName")
	@Mapping(target = "email", source = "userEmail")
	UserProfileEntity authUserDTOToPo(AuthUserDTO authUserDTO);


	UserProfileEntity voToPo(UserProfileVO userProfileVO);

}
