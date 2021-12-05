package com.mcecelja.pocket.common.exceptions;

public enum PocketError {
	UNRECOGNIZED_EXCEPTION("Unrecognized exception!"),
	SESSION_EXPIRED("Session expired!"),
	JSON_PARSE_ERROR("Error while parsing JSON!"),
	BAD_REQUEST("Bad request!"),
	BAD_CREDENTIALS("Bad credentials");

	private final String desc;

	PocketError(String desc) {
		this.desc = desc;
	}

	public String desc() {
		return this.desc;
	}
}
