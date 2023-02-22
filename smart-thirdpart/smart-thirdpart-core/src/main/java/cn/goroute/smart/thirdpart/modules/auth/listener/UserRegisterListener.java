package cn.goroute.smart.thirdpart.modules.auth.listener;

import cn.goroute.smart.auth.domain.dto.UserRegisterInfoDTO;
import cn.goroute.smart.common.constant.RocketMqBizConstant;
import cn.goroute.smart.rocketmq.domain.RocketMqEntityMessage;
import cn.goroute.smart.rocketmq.listener.BaseMqMessageListener;
import cn.goroute.smart.thirdpart.modules.auth.service.AuthThirdpartService;
import com.alibaba.fastjson2.JSON;
import lombok.RequiredArgsConstructor;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author: Alickx
 * @Date: 2023/02/21 11:57:12
 * @Description: 用户注册监听器
 */
@RequiredArgsConstructor
@RocketMQMessageListener(
        topic = RocketMqBizConstant.AuthMqConstant.AUTH_TOPIC,
        consumerGroup = RocketMqBizConstant.AuthMqConstant.USER_REGISTER_GROUP
)
@Component
public class UserRegisterListener extends BaseMqMessageListener<RocketMqEntityMessage>
        implements RocketMQListener<RocketMqEntityMessage> {


    private final AuthThirdpartService authThirdpartService;

    @Override
    protected String consumerName() {
        return "用户注册监听器";
    }

    @Override
    protected void handleMessage(RocketMqEntityMessage message) {
        UserRegisterInfoDTO userRegisterInfoDTO = JSON.parseObject(message.getMessage(), UserRegisterInfoDTO.class);
        authThirdpartService.sendRegisterActiveEmail(userRegisterInfoDTO);
    }

    @Override
    protected void overMaxRetryTimesMessage(RocketMqEntityMessage message) {

    }

    @Override
    protected boolean isRetry() {
        return false;
    }

    @Override
    protected boolean isThrowException() {
        return false;
    }

    @Override
    public void onMessage(RocketMqEntityMessage rocketMqEntityMessage) {
        dispatchMessage(rocketMqEntityMessage);
    }
}
