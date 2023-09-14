package com.stackroute.chatService.exception;

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
	 * Invalid chat request.
	 *
	 * @param invalidChatRequestException the invalid chat request exception
	 * @return the response entity
	 */
	@ExceptionHandler(InvalidChatRequestException.class)
	public ResponseEntity<ErrorInfo> invalidChatRequest(InvalidChatRequestException invalidChatRequestException) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(invalidChatRequestException.getMessage());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
		errorInfo.setDateTime(LocalDateTime.now());
		return new ResponseEntity<>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Chat already exist error.
	 *
	 * @param chatAlreadyExistException the chat already exist exception
	 * @return the response entity
	 */
	@ExceptionHandler(ChatAlreadyExistException.class)
	public ResponseEntity<ErrorInfo> chatAlreadyExistError(ChatAlreadyExistException chatAlreadyExistException) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(chatAlreadyExistException.getMessage());
		errorInfo.setHttpStatus(HttpStatus.BAD_REQUEST.toString());
		errorInfo.setDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Id not found error.
	 *
	 * @param idNotFoundException the id not found exception
	 * @return the response entity
	 */
	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ErrorInfo> idNotFoundError(IdNotFoundException idNotFoundException) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorMessage(idNotFoundException.getMessage());
		errorInfo.setHttpStatus(HttpStatus.NOT_FOUND.toString());
		errorInfo.setDateTime(LocalDateTime.now());
		return new ResponseEntity<ErrorInfo>(errorInfo, HttpStatus.NOT_FOUND);
	}

}
