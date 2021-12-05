package com.mcecelja.pocket.context;

import com.mcecelja.pocket.domain.user.User;

import java.time.ZonedDateTime;

public class AuthorizedRequestContext {

	private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

	private static final ThreadLocal<ZonedDateTime> requestDateTime = new ThreadLocal<>();

	public static User getCurrentUser() {
		return currentUser.get();
	}

	public static void setCurrentUser(User user) {
		currentUser.set(user);
		requestDateTime.set(ZonedDateTime.now());
	}

	public static ZonedDateTime getRequestDateTime() {
		return requestDateTime.get();
	}

	public static void clear() {
		currentUser.set(null);
		requestDateTime.set(null);
	}
}