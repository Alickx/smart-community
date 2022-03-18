package cn.goroute.smart.search.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    public static final String ROUTING_KEY_SEARCH = "smart.search.post";

    public static final String ROUTING_KEY_SEARCH_POST_DURABILITY = "smart.search.post.durability";

    public static final String SMART_SEARCH_POST_QUEUE = "smart.search.post";

    public static final String SMART_SEARCH_POST_DURABILITY_QUEUE = "smart.search.post.durability";

    public static final String EXCHANGE_DIRECT = "exchange.direct";


    @Bean(SMART_SEARCH_POST_QUEUE)
    public Queue SMART_SEARCH_POST(){
        return new Queue(SMART_SEARCH_POST_QUEUE);
    }

    @Bean(SMART_SEARCH_POST_DURABILITY_QUEUE)
    public Queue SMART_SEARCH_POST_DURABILITY(){
        return new Queue(SMART_SEARCH_POST_DURABILITY_QUEUE);
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
    public Binding BINDING_QUEUE_INFORM_SEARCH_POST(@Qualifier(SMART_SEARCH_POST_QUEUE) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SEARCH).noargs();
    }

    @Bean
    public Binding BINDING_QUEUE_INFORM_SEARCH_POST_DURABILITY(@Qualifier(SMART_SEARCH_POST_DURABILITY_QUEUE) Queue queue, @Qualifier(EXCHANGE_DIRECT) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY_SEARCH_POST_DURABILITY).noargs();
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
