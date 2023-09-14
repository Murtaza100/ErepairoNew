package com.stackroute.userAuthentication.configuration;

/**
 * This is Class ServiceProviderConfig.
 *
 * @author by Zaid
 */

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stackroute.userAuthentication.util.AppConstants;


@Configuration
public class ServiceProviderConfig {

    @Bean
    public Queue serviceQueue() {
        return  new Queue(AppConstants.Message_queue);
    }

    @Bean
    public TopicExchange serviceExchange() {
        return new TopicExchange(AppConstants.message_EXCHANGE);
    }

    @Bean
    public Binding serviceBinding(Queue serviceQueue, TopicExchange serviceExchange) {
        return BindingBuilder
                .bind(serviceQueue)
                .to(serviceExchange)
                .with(AppConstants.message_ROUTING_KEY);
    }

    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return  template;
    }
}
