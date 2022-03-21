package cn.goroute.smart.member.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2022/03/20/13:40
 * @Description:
 */
@Component
@Slf4j
public class TransactionRabbitmqUtil {

    @Autowired
    RabbitTemplate rabbitTemplate;



}
