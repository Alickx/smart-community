package cn.goroute.smart.thirdpart.modules.auth.service;

import cn.goroute.smart.auth.domain.dto.UserRegisterInfoDTO;
import cn.goroute.smart.thirdpart.modules.auth.async.AuthAsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author: Alickx
 * @Date: 2023/02/21 12:24:36
 * @Description:
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthThirdpartService {

    private final AuthAsyncService authAsyncService;

    /**
     * 发送注册激活邮件
     * @param userRegisterInfoDTO 注册信息
     */
    public void sendRegisterActiveEmail(UserRegisterInfoDTO userRegisterInfoDTO) {

        String userName = userRegisterInfoDTO.getUserName();

        String userEmail = userRegisterInfoDTO.getUserEmail();

        String token = userRegisterInfoDTO.getToken();

        // 发送邮件
        authAsyncService.sendRegisterActiveEmail(userName, userEmail, token);

    }


}
