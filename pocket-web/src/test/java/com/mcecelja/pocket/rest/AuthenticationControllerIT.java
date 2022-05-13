package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.PocketContextAwareIT;
import com.mcecelja.pocket.common.dto.authentication.LoginRequestDTO;
import com.mcecelja.pocket.common.dto.authentication.LoginResponseDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.utils.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AuthenticationControllerIT extends PocketContextAwareIT {

	@Test
	public void loginUser() throws PocketException {
		LoginRequestDTO body = LoginRequestDTO.builder().username("ikovac").password("ikovac").build();

		HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(body, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/public/authentication/login"),
				HttpMethod.POST, entity, String.class);

		LoginResponseDTO loginResponseDTO = getDTOObjectFromBody(response, LoginResponseDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, StringUtils.countOccurrencesOf(loginResponseDTO.getJwt(), "."));
	}

	@Test
	public void loginUserBadCredentials() {
		LoginRequestDTO body = LoginRequestDTO.builder().username("ikovac").password("wrong").build();

		HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(body, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/public/authentication/login"),
				HttpMethod.POST,
				entity,
				ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_CREDENTIALS.toString(), response.getBody().getErrorCode());
	}
}
