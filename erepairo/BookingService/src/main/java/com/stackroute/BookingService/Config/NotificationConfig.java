package com.stackroute.BookingService.Config;



import com.stackroute.BookingService.Util.AppConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {


    @Bean
    public MessageConverter notificationConverter() {
        return  new Jackson2JsonMessageConverter();
    }
    @Bean
    public TopicExchange exchange(){
        return  new TopicExchange(AppConstants.NOTIFICATION_EXCHANGE);
    }

    @Bean
    public Queue registerQueue(){
        return  new Queue(AppConstants.NOTIFICATION_QUEUE);
    }

    @Bean
    Binding bindingServiceProvider(Queue registerQueue,TopicExchange exchange){
        return BindingBuilder.bind(registerQueue).to(exchange).with(AppConstants.NOTIFICATION_ROUTING_KEY);
    }
    
    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory){
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(notificationConverter());
        return template;
    }
}
