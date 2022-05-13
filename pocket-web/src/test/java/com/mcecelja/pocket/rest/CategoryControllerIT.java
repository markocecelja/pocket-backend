package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.PocketContextAwareIT;
import com.mcecelja.pocket.common.dto.codebook.EditableCodeBookEntryDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.utils.ResponseMessage;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryControllerIT extends PocketContextAwareIT {

	@Test
	public void getCategories() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/categories"),
				HttpMethod.GET, entity, String.class);

		List<EditableCodeBookEntryDTO> result = getListFromBody(response, EditableCodeBookEntryDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(5, result.size());

		authenticateUser("ffranjic", "ffranjic");

		entity = new HttpEntity<>(null, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/categories"),
				HttpMethod.GET, entity, String.class);

		result = getListFromBody(response, EditableCodeBookEntryDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(4, result.size());
	}

	@Test
	public void getCategory() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/categories/1"),
				HttpMethod.GET, entity, String.class);

		EditableCodeBookEntryDTO result = getDTOObjectFromBody(response, EditableCodeBookEntryDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("1", result.getId());
		assertEquals("Kultura", result.getName());
		assertTrue(result.isActive());
	}

	@Test
	public void getCategoryNonExistingCategory() {

		authenticateUser("ffranjic", "ffranjic");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/categories/0"),
				HttpMethod.GET, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_CATEGORY.toString(), response.getBody().getErrorCode());

		response = restTemplate.exchange(
				createURLWithPort("/api/categories/5"),
				HttpMethod.GET, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_CATEGORY.toString(), response.getBody().getErrorCode());
	}

	@Test
	@DirtiesContext
	public void createCategory() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		EditableCodeBookEntryDTO editableCodeBookEntryDTO = EditableCodeBookEntryDTO.builder()
				.name("Nova")
				.build();

		HttpEntity<EditableCodeBookEntryDTO> entity = new HttpEntity<>(editableCodeBookEntryDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/categories"),
				HttpMethod.POST, entity, String.class);

		EditableCodeBookEntryDTO result = getDTOObjectFromBody(response, EditableCodeBookEntryDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("6", result.getId());
		assertEquals("Nova", result.getName());
		assertTrue(result.isActive());
	}

	@Test
	public void createCategoryUnauthorized() {

		authenticateUser("ffranjic", "ffranjic");

		EditableCodeBookEntryDTO editableCodeBookEntryDTO = EditableCodeBookEntryDTO.builder()
				.name("Nova")
				.build();

		HttpEntity<EditableCodeBookEntryDTO> entity = new HttpEntity<>(editableCodeBookEntryDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/categories"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.UNAUTHORIZED.toString(), response.getBody().getErrorCode());
	}

	@Test
	public void createCategoryBadRequest() {

		authenticateUser("ikovac", "ikovac");

		EditableCodeBookEntryDTO editableCodeBookEntryDTO = EditableCodeBookEntryDTO.builder()
				.name("  ")
				.build();

		HttpEntity<EditableCodeBookEntryDTO> entity = new HttpEntity<>(editableCodeBookEntryDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/categories"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());
	}

	@Test
	@DirtiesContext
	public void updateCategory() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		EditableCodeBookEntryDTO editableCodeBookEntryDTO = EditableCodeBookEntryDTO.builder()
				.name("Ažurirana")
				.active(false)
				.build();

		HttpEntity<EditableCodeBookEntryDTO> entity = new HttpEntity<>(editableCodeBookEntryDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/categories/1"),
				HttpMethod.PUT, entity, String.class);

		EditableCodeBookEntryDTO result = getDTOObjectFromBody(response, EditableCodeBookEntryDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("1", result.getId());
		assertEquals("Ažurirana", result.getName());
		assertFalse(result.isActive());
	}

	@Test
	public void updateCategoryNonExistingCategory() {

		authenticateUser("ikovac", "ikovac");

		EditableCodeBookEntryDTO editableCodeBookEntryDTO = EditableCodeBookEntryDTO.builder()
				.name("Ažurirana")
				.active(false)
				.build();

		HttpEntity<EditableCodeBookEntryDTO> entity = new HttpEntity<>(editableCodeBookEntryDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/categories/0"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_CATEGORY.toString(), response.getBody().getErrorCode());
	}

	@Test
	public void updateCategoryUnauthorized() {

		authenticateUser("ffranjic", "ffranjic");

		EditableCodeBookEntryDTO editableCodeBookEntryDTO = EditableCodeBookEntryDTO.builder()
				.name("Ažurirana")
				.active(false)
				.build();

		HttpEntity<EditableCodeBookEntryDTO> entity = new HttpEntity<>(editableCodeBookEntryDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/categories/1"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.UNAUTHORIZED.toString(), response.getBody().getErrorCode());
	}

	@Test
	public void updateCategoryBadRequest() {

		authenticateUser("ikovac", "ikovac");

		EditableCodeBookEntryDTO editableCodeBookEntryDTO = EditableCodeBookEntryDTO.builder()
				.name("   ")
				.active(false)
				.build();

		HttpEntity<EditableCodeBookEntryDTO> entity = new HttpEntity<>(editableCodeBookEntryDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/categories/1"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());
	}
}
