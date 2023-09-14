package com.stackroute.orderStatusService.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * The Class GlobalExceptionHandler.
 *
 * @author sushanth
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * Invalid order status request.
	 *
	 * @param invalidOrderStatusRequestException the invalid order status request
	 *                                           exception
	 * @return the response entity
	 */
	@ExceptionHandler(InvalidOrderStatusRequestException.class)
	public ResponseEntity<ErrorInfo> invalidOrderStatusRequest(
			InvalidOrderStatusRequestException invalidOrderStatusRequestException) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(invalidOrderStatusRequestException.getMessage());
		errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
		errorInfo.setDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
	}

}
