package cn.goroute.smart.user.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Alickx
 * @Date: 2022/03/20/13:28
 * @Description: 用户服务消息队列配置类
 */
@Configuration
public class RabbitmqConfig {

    private static final String ROUTING_KEY_NOTIFY_CREATE = "smart.notify.create";

    private static final String SMART_NOTIFY_CREATE_QUEUE = "smart.notify.create";

    public static final String EXCHANGE_DIRECT = "exchange.direct";

    @Bean(SMART_NOTIFY_CREATE_QUEUE)
    public Queue SMART_MESSAGE_CREATE_QUEUE() {
        return new Queue(SMART_NOTIFY_CREATE_QUEUE);
    }

    /**
     * 声明交换机
     */
    @Bean(EXCHANGE_DIRECT)
    public Exchange EXCHANGE_DIRECT() {
        // 声明路由交换机，durable:在rabbitmq重启后，交换机还在
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT).durable(true).build();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_MESSAGE_CREATE(@Qualifier(SMART_NOTIFY_CREATE_QUEUE) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_NOTIFY_CREATE).noargs();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
