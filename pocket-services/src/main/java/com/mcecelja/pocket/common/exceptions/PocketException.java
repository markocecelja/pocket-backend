package com.mcecelja.pocket.common.exceptions;

import javax.servlet.ServletException;

public class PocketException extends ServletException {

	private final Object errorData;

	private final PocketError error;

	public PocketException(PocketError err) {
		this(err, null, null);
	}

	public PocketException(PocketError err, Throwable t) {
		this(err, null, t);
	}

	public PocketException(PocketError err, String errMsg, Throwable t) {
		super(t);
		this.error = err;
		this.errorData = errMsg;
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		sb.append("{").append(this.getClass().getName());
		sb.append(".superMsg[").append(super.getMessage());
		sb.append("].lderror[");
		if (this.error != null) {
			sb.append("<").append(this.error.name()).append(">");
		} else {
			sb.append("null");
		}
		sb.append("].errorData[");
		if (this.errorData != null) {
			sb.append(errorData);
		} else {
			sb.append("null");
		}
		sb.append("]}");
		return sb.toString();
	}

	public PocketError getError() {
		return error;
	}
}
