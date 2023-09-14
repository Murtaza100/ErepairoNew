package com.stackroute.NotificationService.exception;

/**
 * The Class InvalidNotificationRequestException.
 *
 * @author sushanth
 */
public class InvalidNotificationRequestException extends RuntimeException {

	private static final long serialVersionUID = -7064553076035975226L;

	private String message;

	/**
	 * Instantiates a new invalid notification request exception.
	 *
	 * @param message the message
	 */
	public InvalidNotificationRequestException(String message) {
		super();
		this.message = message;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

}
