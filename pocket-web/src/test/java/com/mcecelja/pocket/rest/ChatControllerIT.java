package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.PocketContextAwareIT;
import com.mcecelja.pocket.common.dto.chat.ChatDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.utils.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChatControllerIT extends PocketContextAwareIT {

	@Test
	public void getChats() throws PocketException {

		authenticateUser("ffranjic", "ffranjic");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/chats"),
				HttpMethod.GET, entity, String.class);

		List<ChatDTO> result = getListFromBody(response, ChatDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, result.size());

		authenticateUser("iradic", "iradic");

		entity = new HttpEntity<>(null, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/chats"),
				HttpMethod.GET, entity, String.class);

		result = getListFromBody(response, ChatDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, result.size());
	}

	@Test
	public void getChatsFilters() throws PocketException {

		authenticateUser("ffranjic", "ffranjic");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/chats?postId=1"),
				HttpMethod.GET, entity, String.class);

		List<ChatDTO> result = getListFromBody(response, ChatDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, result.size());
	}

	@Test
	public void getChat() throws PocketException {

		authenticateUser("ffranjic", "ffranjic");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/chats/1"),
				HttpMethod.GET, entity, String.class);

		ChatDTO result = getDTOObjectFromBody(response, ChatDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("1", result.getId());
		assertEquals("1", result.getPost().getId());
		assertEquals("5", result.getUser().getId());
	}

	@Test
	public void getChatNonExistingChat() {

		authenticateUser("ffranjic", "ffranjic");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/chats/0"),
				HttpMethod.GET, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_CHAT.toString(), response.getBody().getErrorCode());
	}
}
