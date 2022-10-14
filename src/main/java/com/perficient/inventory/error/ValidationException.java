package com.perficient.inventory.error;

import java.util.List;


/**
 * Exception class specific for validation related errors in the Custom Package
 * Service.
 */
public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final List<ErrorCode> errorCodes;

	public List<ErrorCode> getErrorCodes() {
		return errorCodes;
	}

	public ValidationException(String message, List<ErrorCode> errorCodes) {
		super(message);
		this.errorCodes = errorCodes;
	}
	
	@Override
	public String toString() {
		return "ValidationException [getErrorCodes()=" + getErrorCodes() + "]";
	}
	
}
