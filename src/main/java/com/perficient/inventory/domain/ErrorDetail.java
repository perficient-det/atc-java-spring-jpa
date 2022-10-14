package com.perficient.inventory.domain;

import com.perficient.inventory.error.ErrorCode;

/**
 * Model Object Containing Error Details for Custom Package Service.
 */
public class ErrorDetail {
	
	private int code;
	private String title;
	private String description;

	/**
	 * Constructor used to easily build the ErrorDetail object. 
	 */
	public ErrorDetail(ErrorCode error) {
		super();
		this.code = error.getErrorType();
		this.title = error.name();
		this.description = error.getDescription();
	}
	
	public int getCode() {
		return code;
	}
	
	public void setCode(int code) {
		this.code = code;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Default Constructor
	 */
	public ErrorDetail() {
		super();
	}

	public String getTitle() {
		return title;
	}
	
	public String getDescription() {
		return description;
	}

	@Override
	public String toString() {
		return "ErrorDetail [getCode()=" + getCode() + ", getTitle()=" + getTitle() + ", getDescription()=" + getDescription() + "]";
	}

}
