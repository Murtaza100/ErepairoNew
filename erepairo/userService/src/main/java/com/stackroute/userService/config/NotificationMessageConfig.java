package com.stackroute.userService.config;

import com.stackroute.userService.util.AppConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationMessageConfig {
    @Bean
    public Queue notificationQueue() {
        return  new Queue(AppConstants.NOTIFICATION_QUEUE);
    }

    @Bean
    public TopicExchange notificationExchange() {
        return new TopicExchange(AppConstants.NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Binding notificationBinding(Queue notificationQueue, TopicExchange notificationExchange) {
        return BindingBuilder
                .bind(notificationQueue)
                .to(notificationExchange)
                .with(AppConstants.NOTIFICATION_ROUTING_KEY);
    }

    @Bean
    public MessageConverter notificationConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate notificationTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(notificationConverter());
        return  template;
    }
}
