package com.stackroute.chatService.exception;

/**
 * The Class ChatAlreadyExistException.
 *
 * @author sushanth
 */
public class ChatAlreadyExistException extends RuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 5697684546601587070L;

	/** The message. */
	private String message;

	/**
	 * Instantiates a new chat already exist exception.
	 *
	 * @param message the message
	 */
	public ChatAlreadyExistException(String message) {
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
