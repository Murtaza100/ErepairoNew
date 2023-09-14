package com.stackroute.NotificationService.config;

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

import com.stackroute.NotificationService.util.AppConstants;

/**
 * The Class NotificationConfig.
 *
 * @author sushanth
 */
@Configuration
public class NotificationConfig {

	/**
	 * Notification queue.
	 *
	 * @return the queue
	 */
	@Bean
	public Queue notificationQueue() {
		return new Queue(AppConstants.NOTIFICATION_QUEUE);
	}

	/**
	 * Notification exchange.
	 *
	 * @return the topic exchange
	 */
	@Bean
	public TopicExchange notificationExchange() {
		return new TopicExchange(AppConstants.NOTIFICATION_EXCHANGE);
	}

	/**
	 * Notification binding.
	 *
	 * @param notificationQueue    the notification queue
	 * @param notificationExchange the notification exchange
	 * @return the binding
	 */
	@Bean
	public Binding notificationBinding(Queue notificationQueue, TopicExchange notificationExchange) {
		return BindingBuilder.bind(notificationQueue).to(notificationExchange)
				.with(AppConstants.NOTIFICATION_ROUTING_KEY);
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
	 * Notification template.
	 *
	 * @param connectionFactory the connection factory
	 * @return the amqp template
	 */
	@Bean
	public AmqpTemplate notificationTemplate(ConnectionFactory connectionFactory) {
		RabbitTemplate template = new RabbitTemplate(connectionFactory);
		template.setMessageConverter(messageConverter());
		return template;
	}
}
