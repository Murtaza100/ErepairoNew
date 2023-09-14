package com.stackroute.PaymentService.exception;

/**
 * The Class InvalidPaymentRequestException.
 *
 * @author sushanth
 */
public class InvalidPaymentRequestException extends RuntimeException {

	private static final long serialVersionUID = -7064553076035975226L;

	private String message;

	/**
	 * Instantiates a new invalid payment request exception.
	 *
	 * @param message the message
	 */
	public InvalidPaymentRequestException(String message) {
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
