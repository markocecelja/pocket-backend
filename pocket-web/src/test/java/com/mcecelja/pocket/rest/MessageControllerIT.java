package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.PocketContextAwareIT;
import com.mcecelja.pocket.common.dto.chat.MessageDTO;
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

public class MessageControllerIT extends PocketContextAwareIT {

	@Test
	public void getMessages() throws PocketException {

		authenticateUser("ffranjic", "ffranjic");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/messages?postId=1&userId=5"),
				HttpMethod.GET, entity, String.class);

		List<MessageDTO> result = getListFromBody(response, MessageDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, result.size());
	}

	@Test
	public void getMessagesBadRequest() {

		authenticateUser("ffranjic", "ffranjic");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/messages"),
				HttpMethod.GET, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());
	}
}
