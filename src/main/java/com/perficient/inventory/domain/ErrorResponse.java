package com.perficient.inventory.domain;

import java.util.ArrayList;
import java.util.List;

import com.perficient.inventory.error.ErrorCode;




/**
 * Model Object Containing Error Response Details for Custom Package Service.
 */
public class ErrorResponse {
	
	private String type;
	private String message;
	private List<ErrorDetail> errors;
	
	/**
	 * Constructor to easily build the ErrorResponse object
	 */
	public ErrorResponse(String type, String message, List<ErrorCode> errors) {
		super();
		this.type = type;
		this.message = message;
		this.errors = convert(errors);
	}
	
	
	public void setType(String type) {
		this.type = type;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setErrors(List<ErrorDetail> errors) {
		this.errors = errors;
	}

	private static List<ErrorDetail> convert(List<ErrorCode> errors) {
		List<ErrorDetail> details = new ArrayList<>();
		for (ErrorCode error : errors) {
			details.add(new ErrorDetail(error));
		}
		return details;
	}

	/**
	 * Default Constructor
	 */
	public ErrorResponse() {
		super();
	}

	/**
	 * Constructor to build an object with a single ErrorCode
	 */
	public ErrorResponse(String type, String message, ErrorCode error) {
		this(type, message, createErrorCodes(error));
	}

	private static List<ErrorCode> createErrorCodes(ErrorCode error) {
		List<ErrorCode> errors = new ArrayList<>();
		errors.add(error);
		return errors;
	}
	
	public String getType() {
		return type;
	}

	public String getMessage() {
		return message;
	}
	
	public List<ErrorDetail> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "ErrorResponse [getType()=" + getType() + ", getMessage()=" + getMessage() + ", getErrors()=" + getErrors() + "]";
	}

}
