package com.stackroute.NotificationService.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stackroute.NotificationService.dto.NotificationDto;
import com.stackroute.NotificationService.service.NotificationService;
import com.stackroute.NotificationService.util.AppConstants;

/**
 * The listener interface for receiving notification events. The class that is
 * interested in processing a notification event implements this interface, and
 * the object created with that class is registered with a component using the
 * component's <code>addNotificationListener<code> method. When the notification
 * event occurs, that object's appropriate method is invoked.
 *
 * @author sushanth
 */
@Component
public class NotificationListener {

	/** The log. */
	Logger log = LoggerFactory.getLogger(NotificationListener.class);

	/** The notification service. */
	@Autowired
	NotificationService notificationService;

	/**
	 * Listen notification.
	 *
	 * @param notificationDto the notification dto
	 */
	@RabbitListener(queues = AppConstants.NOTIFICATION_QUEUE)
	public void listenNotification(NotificationDto notificationDto) {
		if (null != notificationDto.getEmail()) {
			notificationService.sendEmail(notificationDto);
			log.info("message consumed and email sent successfully");
		} else {
			notificationService.sendCommonEmail(notificationDto);
			log.info("message consumed and bulk email sent successfully");
		}
	}
}
