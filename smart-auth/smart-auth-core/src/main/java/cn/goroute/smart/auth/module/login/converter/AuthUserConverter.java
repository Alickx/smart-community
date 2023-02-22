package cn.goroute.smart.auth.module.login.converter;

import cn.goroute.smart.auth.domain.dto.AuthUserDTO;
import cn.goroute.smart.auth.domain.entity.AuthUserEntity;
import cn.goroute.smart.auth.domain.form.UserRegisterForm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author: Alickx
 * @Date: 2023/02/19 15:36:10
 * @Description:
 */
@Mapper
public interface AuthUserConverter {

    AuthUserConverter INSTANCE = Mappers.getMapper(AuthUserConverter.class);


    AuthUserEntity formToEntity(UserRegisterForm userRegisterForm);

    AuthUserDTO entityToDTO(AuthUserEntity authUserEntity);

}
