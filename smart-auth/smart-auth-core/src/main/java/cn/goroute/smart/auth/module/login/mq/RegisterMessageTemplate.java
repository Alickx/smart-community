package cn.goroute.smart.auth.module.login.mq;

import cn.goroute.smart.auth.domain.dto.UserRegisterInfoDTO;
import cn.goroute.smart.auth.module.login.util.JwtUtil;
import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.template.RocketMqTemplate;
import com.alibaba.fastjson.JSON;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: Alickx
 * @Date: 2023/02/21 12:01:41
 * @Description:
 */
@RequiredArgsConstructor
@Component
public class RegisterMessageTemplate extends RocketMqTemplate {


    private final JwtUtil jwtUtil;

    public void sendAsyncRegisterMessage(String userName, String userEmail) {
        UserRegisterInfoDTO registerInfo = new UserRegisterInfoDTO();
        registerInfo.setUserName(userName);
        registerInfo.setUserEmail(userEmail);
        registerInfo.setToken(getToken(userName, userEmail));
        RocketMqEntityMessage rocketMqEntityMessage = new RocketMqEntityMessage();
        rocketMqEntityMessage.setMessage(JSON.toJSONString(registerInfo));
        rocketMqEntityMessage.setKey(userEmail);
        rocketMqEntityMessage.setSource("注册激活邮件");
        sendAsync(RocketMqBizConstant.AuthMqConstant.AUTH_TOPIC, rocketMqEntityMessage);
    }

    private String getToken(String userName,String userEmail) {

        Map<String, Object> map = Map.of("userName", userName, "userEmail", userEmail);

        return jwtUtil.createUserActivateToken(userName, map);
    }

}
