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
public class BookingExchangeConfiguration {


    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange bookingexchange() {
        return new TopicExchange(AppConstants.BOOKING_EXCHANGE);
    }

    @Bean
    public Queue bookingregisterQueue() {
        return new Queue(AppConstants.BOOKING_QUEUE);
    }


    @Bean
    Binding bookingbindingServiceProvider(Queue bookingregisterQueue, TopicExchange bookingexchange) {
        return BindingBuilder.bind(bookingregisterQueue).to(bookingexchange).with(AppConstants.BOOKING_ROUTING_KEY);
    }

    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}