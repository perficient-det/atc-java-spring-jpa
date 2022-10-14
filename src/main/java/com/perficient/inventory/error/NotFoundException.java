package com.perficient.inventory.error;

public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private ErrorCode code;
	
	public NotFoundException(ErrorCode code, String message) {
		super(message);
		setCode(code);
	}

	public NotFoundException(ErrorCode code, String message, Exception e) {
		super(message, e);
		setCode(code);
	}

	public ErrorCode getCode() {
		return code;
	}

	private void setCode(ErrorCode code) {
		this.code = code;
	}

}
