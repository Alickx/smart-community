package cn.goroute.smart.post.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String ROUTING_KEY_SEARCH = "smart.post.review";

    public static final String SMART_POST_VIEW = "smart.post.review";

    public static final String EXCHANGE_DIRECT = "exchange.direct";


    @Bean(SMART_POST_VIEW)
    public Queue SMART_SEARCH_POST(){
        return new Queue(SMART_POST_VIEW);
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
    public Binding BINDING_QUEUE_INFORM_SEARCH_POST(@Qualifier(SMART_POST_VIEW) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SEARCH).noargs();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
