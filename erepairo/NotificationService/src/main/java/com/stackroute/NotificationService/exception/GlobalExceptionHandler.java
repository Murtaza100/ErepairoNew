package com.stackroute.NotificationService.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The Class GlobalExceptionHandler.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Invalid notification request.
	 *
	 * @param invalidNotificationRequestException the invalid notification request
	 *                                            exception
	 * @return the response entity
	 */
	@ExceptionHandler(InvalidNotificationRequestException.class)
	public ResponseEntity<ErrorInfo> invalidNotificationRequest(
			InvalidNotificationRequestException invalidNotificationRequestException) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(invalidNotificationRequestException.getMessage());
		errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
		errorInfo.setDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
	}

	/**
	 * Email error.
	 *
	 * @param emailException the email exception
	 * @return the response entity
	 */
	@ExceptionHandler(EmailException.class)
	public ResponseEntity<ErrorInfo> EmailError(EmailException emailException) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(emailException.getMessage());
		errorInfo.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		errorInfo.setDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
