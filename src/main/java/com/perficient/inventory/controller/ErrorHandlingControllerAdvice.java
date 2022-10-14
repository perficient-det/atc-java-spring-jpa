package com.perficient.inventory.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.perficient.inventory.domain.ErrorResponse;
import com.perficient.inventory.error.ErrorCode;
import com.perficient.inventory.error.NotFoundException;
import com.perficient.inventory.error.ValidationException;


/**
 * Error handling controller
 */
@ControllerAdvice
public class ErrorHandlingControllerAdvice {

	private static final Logger LOGGER = Logger.getLogger(ErrorHandlingControllerAdvice.class.getName());

	public static final String ERROR_TYPE_VALIDATION = "VALIDATION";
	public static final String ERROR_TYPE_SERVICE = "SERVICE";
	public static final String ERROR_TYPE_UNKNOWN = "UNKNOWN";
	public static final String ERROR_TYPE_NOT_FOUND = "NOT_FOUND";

	/**
	 * ValidationException handler method
	 * 
	 * @param exception An Exception with populated error code details.
	 * @return Error Response
	 */
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<ErrorResponse> createErrorResponse(ValidationException exception) {
		LOGGER.log(Level.SEVERE, exception, () -> "ValidationException is being handled by the exception handler. Error Codes: " + exception.getErrorCodes());
		ErrorResponse exceptionResponse = new ErrorResponse(ERROR_TYPE_VALIDATION, "Validation error(s) encountered", exception.getErrorCodes());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
	}

	/**
	 * NotFoundException handler method
	 * 
	 * @param exception An Exception with populated error code details
	 * @return Error response
	 */
	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorResponse> createErrorResponse(NotFoundException exception){
		LOGGER.log(Level.WARNING, "NotFoundException is being handled by the exception handler.", exception);
		ErrorResponse exceptionResponse = new ErrorResponse(ERROR_TYPE_NOT_FOUND, "Object not found", exception.getCode());
		return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
	}
	/**
	 * Exception handler method
	 * 
	 * @param exception Unknown/Unexpected Exceptions
	 * @return Unknown error response
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> createErrorResponse(Exception exception) {
		LOGGER.log(Level.SEVERE, "Unknown/unexpected exception is being handled by the exception handler.", exception);
		ErrorResponse exceptionResponse = new ErrorResponse(ERROR_TYPE_UNKNOWN, "Unknown/unexpected exception", ErrorCode.UNKNOWN_ERROR);
		return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
