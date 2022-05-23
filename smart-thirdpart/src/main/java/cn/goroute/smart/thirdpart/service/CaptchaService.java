package cn.goroute.smart.thirdpart.service;

import cn.goroute.smart.common.utils.Result;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/21:57
 * @Description:
 */
public interface CaptchaService {
    Result generateRegistrationVerificationCode(String emailAddress);
}
