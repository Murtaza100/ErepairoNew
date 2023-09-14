package com.stackroute.ServiceProviderService.config;
import com.stackroute.ServiceProviderService.util.AppConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfiguration {


    @Bean
    public MessageConverter messageConverter() {
        return  new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange notificationexchange(){
        return  new TopicExchange(AppConstants.NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Queue notificationregisterQueue(){
        return  new Queue(AppConstants.NOTIFICATION_QUEUE);
    }


    @Bean
    Binding notificationbindingServiceProvider(Queue notificationregisterQueue,TopicExchange notificationexchange){
        return BindingBuilder.bind(notificationregisterQueue).to(notificationexchange).with(AppConstants.NOTIFICATION_ROUTING_KEY);
    }

    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
