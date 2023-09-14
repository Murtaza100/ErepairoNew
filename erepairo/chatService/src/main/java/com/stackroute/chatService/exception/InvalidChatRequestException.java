package com.stackroute.chatService.exception;

/**
 * The Class InvalidChatRequestException.
 *
 * @author sushanth
 */
public class InvalidChatRequestException extends RuntimeException {

	private static final long serialVersionUID = -7064553076035975226L;

	private String message;

	/**
	 * Instantiates a new invalid chat request exception.
	 *
	 * @param message the message
	 */
	public InvalidChatRequestException(String message) {
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
