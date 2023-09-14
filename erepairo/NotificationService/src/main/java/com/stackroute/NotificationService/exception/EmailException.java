package com.stackroute.NotificationService.exception;

/**
 * The Class EmailException.
 *
 * @author sushanth
 */
public class EmailException extends RuntimeException {

	private static final long serialVersionUID = 5697684546601587070L;

	private String message;

	/**
	 * Instantiates a new email exception.
	 *
	 * @param message the message
	 */
	public EmailException(String message) {
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
