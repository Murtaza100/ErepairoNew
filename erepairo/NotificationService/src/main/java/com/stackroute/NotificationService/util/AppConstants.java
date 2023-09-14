package com.stackroute.NotificationService.util;

/**
 * The Interface AppConstants.
 *
 * @author sushanth
 */
public interface AppConstants {

	// endpoints
	String COMMOM_URL = "/erepairo/v1";
	String SEND_MAIL_END_POINT = "/email";

	// constant messages
	String SUCCESS_COMMON_MESSAGE = "Notification sent successfully to all emailIds";
	String SUCCESS_MESSAGE = "Notification sent successfully to emailId: %s";
	String INVALID_REQUEST_MESSAGE = "EmailId, EmailBody and EmailSubject are required fields";
	String UNKNOWN_ERROR_MESSAGE = "Unknown exception occured in Notification service";
	String MAIL_ERROR_MESSAGE = "MailException occured in Notification service";
	String MAIL_ALREADY_PRESENT_IN_DB = "emailId already present in the database. Hence we are skipping the insertion";
	String MAIL_INSERTION_SUCCESSFUL = "emailId saved in the database successfully";
	String SEQUENCE_NAME = "notification_sequence";

	// rabbitmq constants
	String NOTIFICATION_QUEUE = "notification_queue";
	String NOTIFICATION_EXCHANGE = "notification_exchange";
	String NOTIFICATION_ROUTING_KEY = "notification_routing_key";

}
