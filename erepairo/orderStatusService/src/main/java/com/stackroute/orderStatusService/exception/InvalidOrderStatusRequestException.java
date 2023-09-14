package com.stackroute.orderStatusService.exception;

/**
 * The Class InvalidOrderStatusRequestException.
 *
 * @author sushanth
 */
public class InvalidOrderStatusRequestException extends RuntimeException {

	private static final long serialVersionUID = -7064553076035975226L;

	private String message;

	/**
	 * Instantiates a new invalid order status request exception.
	 *
	 * @param message the message
	 */
	public InvalidOrderStatusRequestException(String message) {
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
