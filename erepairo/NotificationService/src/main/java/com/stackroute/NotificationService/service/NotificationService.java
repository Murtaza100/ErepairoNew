package com.stackroute.NotificationService.service;

import com.stackroute.NotificationService.dto.NotificationDto;

/**
 * The Interface NotificationService.
 *
 * @author sushanth
 */
public interface NotificationService {

	/**
	 * Send email.
	 *
	 * @param notificationDto the notification dto
	 * @return the string
	 */
	String sendEmail(NotificationDto notificationDto);

	/**
	 * Send common email.
	 *
	 * @param notificationDto the notification dto
	 * @return the string
	 */
	String sendCommonEmail(NotificationDto notificationDto);
}
