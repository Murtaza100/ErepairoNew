package com.stackroute.PaymentService.exception;

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
	 * Invalid payment request.
	 *
	 * @param invalidPaymentRequestException the invalid payment request exception
	 * @return the response entity
	 */
	@ExceptionHandler(InvalidPaymentRequestException.class)
	public ResponseEntity<ErrorInfo> invalidPaymentRequest(
			InvalidPaymentRequestException invalidPaymentRequestException) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(invalidPaymentRequestException.getMessage());
		errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
		errorInfo.setDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
	}

}
