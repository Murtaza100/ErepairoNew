package com.stackroute.chatService.exception;

/**
 * The Class IdNotFoundException.
 *
 * @author sushanth
 */
public class IdNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -7064553076035975226L;

	private String message;

	/**
	 * Instantiates a new id not found exception.
	 *
	 * @param message the message
	 */
	public IdNotFoundException(String message) {
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
