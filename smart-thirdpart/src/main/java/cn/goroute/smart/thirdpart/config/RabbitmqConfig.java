package cn.goroute.smart.thirdpart.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String SMART_MAIL = "smart.mail";

    public static final String SMART_SEARCH_POST = "smart.search.post";

    public static final String EXCHANGE_DIRECT = "exchange.direct";

    public static final String ROUTING_KEY_MAIL = "smart.mail";

    public static final String ROUTING_KEY_SEARCH_MAIL = "smart.search.post";



    /**
     * 声明交换机
     */
    @Bean(EXCHANGE_DIRECT)
    public Exchange EXCHANGE_DIRECT() {
        // 声明路由交换机，durable:在rabbitmq重启后，交换机还在
        return ExchangeBuilder.directExchange(EXCHANGE_DIRECT).durable(true).build();
    }

    @Bean(SMART_MAIL)
    public Queue queue(){
        return new Queue(SMART_MAIL);
    }

    @Bean(SMART_SEARCH_POST)
    public Queue SMART_SEARCH_POST(){
        return new Queue(SMART_SEARCH_POST);
    }

    /**
     * 队列绑定交换机，指定routingKey
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_QUEUE_INFORM_EMAIL(@Qualifier(SMART_MAIL) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_MAIL).noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_SEARCH_POST(@Qualifier(SMART_SEARCH_POST) Queue queue,@Qualifier(EXCHANGE_DIRECT) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SEARCH_MAIL).noargs();
    }

    /**
     * 使用jackson来做序列化
     * @return
     */
    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
