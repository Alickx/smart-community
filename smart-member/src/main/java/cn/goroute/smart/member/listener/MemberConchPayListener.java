package cn.goroute.smart.member.listener;

import cn.goroute.smart.common.entity.pojo.TransactionRecordEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author: Alickx
 * @Date: 2022/03/20/13:36
 * @Description: 用户贝壳付款信息处理
 */
@Component
@Slf4j
public class MemberConchPayListener {


    @RabbitListener(queues = "smart.member.conch.pay")
    public void memberConchPayHandler(TransactionRecordEntity transactionRecord){



    }

}
