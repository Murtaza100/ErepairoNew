package com.stackroute.orderStatusService.config;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stackroute.orderStatusService.util.AppConstants;

/**
 * The Class OrderStatusConfig.
 *
 * @author sushanth
 */
@Configuration
public class OrderStatusConfig {

	/**
	 * Order status queue.
	 *
	 * @return the queue
	 */
	@Bean
	public Queue orderStatusQueue() {
		return new Queue(AppConstants.STATUS_QUEUE);
	}

	/**
	 * Order status exchange.
	 *
	 * @return the topic exchange
	 */
	@Bean
	public TopicExchange orderStatusExchange() {
		return new TopicExchange(AppConstants.STATUS_EXCHANGE);
	}

	/**
	 * Order status binding.
	 *
	 * @param notificationQueue    the notification queue
	 * @param notificationExchange the notification exchange
	 * @return the binding
	 */
	@Bean
	public Binding orderStatusBinding(Queue notificationQueue, TopicExchange notificationExchange) {
		return BindingBuilder.bind(notificationQueue).to(notificationExchange).with(AppConstants.STATUS_ROUTING_KEY);
	}

	/**
	 * Message converter.
	 *
	 * @return the message converter
	 */
	@Bean
	public MessageConverter messageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	/**
	 * Order status template.
	 *
	 * @param connectionFactory the connection factory
	 * @return the amqp template
	 */
	@Bean
	public AmqpTemplate orderStatusTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter());
		return template;
	}
}
