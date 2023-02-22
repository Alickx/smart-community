package cn.goroute.smart.auth.module.login.manager;

import cn.goroute.smart.auth.domain.dto.AuthUserDTO;
import cn.goroute.smart.auth.domain.entity.AuthUserEntity;
import cn.goroute.smart.auth.feign.FeignUserProfileService;
import cn.goroute.smart.auth.module.login.converter.AuthUserConverter;
import cn.goroute.smart.auth.module.login.mapper.AuthUserMapper;
import cn.goroute.smart.common.constant.enums.ErrorCodeEnum;
import cn.goroute.smart.common.exception.BusinessException;
import cn.goroute.smart.common.modules.result.R;
import cn.hutool.crypto.digest.BCrypt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author: Alickx
 * @Date: 2023/02/19 16:33:41
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthUserManagerService {

    private final AuthUserMapper authUserMapper;

    private final FeignUserProfileService feignUserProfileService;



    @Transactional(rollbackFor = Exception.class)
    public void saveAuthUser(AuthUserEntity authUserEntity) {

        // 加密密码
        authUserEntity.setPassword(BCrypt.hashpw(authUserEntity.getPassword(), BCrypt.gensalt()));
        authUserMapper.insert(authUserEntity);

        AuthUserDTO authUserDTO = AuthUserConverter.INSTANCE.entityToDTO(authUserEntity);

        R<Boolean> result = feignUserProfileService.initUserProfile(authUserDTO);
        if (result.getCode() != 200 || !result.getData()) {
            throw new BusinessException(ErrorCodeEnum.SYSTEM_ERROR);
        }
    }

	@Transactional(rollbackFor = Exception.class)
	public void updateAuthUser(AuthUserEntity authUserEntity) {
		authUserMapper.updateById(authUserEntity);
	}

}
