package com.stackroute.chatService.exception;

import java.time.LocalDateTime;

/**
 * The Class ErrorInfo.
 */
public class ErrorInfo {

	private String errorMessage;
	private String httpStatus;
	private LocalDateTime dateTime;

	/**
	 * Instantiates a new error info.
	 */
	public ErrorInfo() {
		super();
	}

	/**
	 * Instantiates a new error info.
	 *
	 * @param errorMessage the error message
	 * @param httpStatus   the http status
	 * @param dateTime     the date time
	 */
	public ErrorInfo(String errorMessage, String httpStatus, LocalDateTime dateTime) {
		super();
		this.errorMessage = errorMessage;
		this.httpStatus = httpStatus;
		this.dateTime = dateTime;
	}

	/**
	 * Gets the error message.
	 *
	 * @return the error message
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Sets the error message.
	 *
	 * @param errorMessage the new error message
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Gets the http status.
	 *
	 * @return the http status
	 */
	public String getHttpStatus() {
		return httpStatus;
	}

	/**
	 * Sets the http status.
	 *
	 * @param httpStatus the new http status
	 */
	public void setHttpStatus(String httpStatus) {
		this.httpStatus = httpStatus;
	}

	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public LocalDateTime getDateTime() {
		return dateTime;
	}

	/**
	 * Sets the date time.
	 *
	 * @param dateTime the new date time
	 */
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}

}
