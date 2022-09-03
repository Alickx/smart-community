package cn.goroute.smart.thirdpart.service;

import cn.goroute.smart.common.entity.resp.Response;

/**
 * @Author: Alickx
 * @Date: 2022/03/31/21:57
 * @Description:
 */
public interface CaptchaService {
    Response generateRegistrationVerificationCode(String emailAddress);
}
