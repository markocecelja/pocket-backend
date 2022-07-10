package com.mcecelja.pocket.common.exceptions;

public enum PocketError {
	UNRECOGNIZED_EXCEPTION("Unrecognized exception!"),
	SESSION_EXPIRED("Session expired!"),
	JSON_PARSE_ERROR("Error while parsing JSON!"),
	BAD_REQUEST("Bad request!"),
	BAD_CREDENTIALS("Bad credentials!"),
	NON_EXISTING_ROLE("Role with provided id doesn't exist!"),
	UNAUTHORIZED("Unauthorized request!"),
	NON_EXISTING_ORGANIZATION("Organization with provided id doesn't exist!"),
	NON_EXISTING_CATEGORY("Category with provided id doesn't exist!"),
	NON_EXISTING_POST("Post with provided id doesn't exist!"),
	NON_EXISTING_ORGANIZATION_ROLE("Organization role with provided id doesn't exist!"),
	NON_EXISTING_CHAT("Chat with provided id doesn't exist!"),
	PASSWORD_MISMATCH("Password mismatch!"),
	USERNAME_ALREADY_IN_USE("Provided username is already in use!");

	private final String desc;

	PocketError(String desc) {
		this.desc = desc;
	}

	public String desc() {
		return this.desc;
	}
}
