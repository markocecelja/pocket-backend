package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.PocketContextAwareIT;
import com.mcecelja.pocket.common.dto.codebook.EditableCodeBookEntryDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.dto.post.PostDTO;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PostControllerIT extends PocketContextAwareIT {

	@Test
	public void getPosts() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.GET, entity, String.class);

		List<PostDTO> result = getListFromBody(response, PostDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(4, result.size());

		authenticateUser("ffranjic", "ffranjic");

		entity = new HttpEntity<>(null, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.GET, entity, String.class);

		result = getListFromBody(response, PostDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(3, result.size());
	}

	@Test
	public void getPostsFilters() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/posts?categoryId=2"),
				HttpMethod.GET, entity, String.class);

		List<PostDTO> result = getListFromBody(response, PostDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, result.size());

		response = restTemplate.exchange(
				createURLWithPort("/api/posts?organizationId=1"),
				HttpMethod.GET, entity, String.class);

		result = getListFromBody(response, PostDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(3, result.size());
	}

	@Test
	public void getPost() throws PocketException {

		authenticateUser("ffranjic", "ffranjic");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/posts/1"),
				HttpMethod.GET, entity, String.class);

		PostDTO result = getDTOObjectFromBody(response, PostDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("1", result.getId());
		assertEquals("3", result.getCategory().getId());
		assertEquals("1", result.getOrganization().getId());
		assertTrue(result.isActive());
	}

	@Test
	public void getPostNonExistingPost() {

		authenticateUser("ffranjic", "ffranjic");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/posts/0"),
				HttpMethod.GET, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_POST.toString(), response.getBody().getErrorCode());

		response = restTemplate.exchange(
				createURLWithPort("/api/posts/4"),
				HttpMethod.GET, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_POST.toString(), response.getBody().getErrorCode());
	}

	@Test
	@DirtiesContext
	public void createPost() throws PocketException {

		authenticateUser("ffranjic", "ffranjic");

		PostDTO postDTO = PostDTO.builder()
				.title("Novi")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(OrganizationDTO.builder().id("1").build())
				.build();

		HttpEntity<PostDTO> entity = new HttpEntity<>(postDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.POST, entity, String.class);

		PostDTO result = getDTOObjectFromBody(response, PostDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("5", result.getId());
		assertEquals("Novi", result.getTitle());
		assertEquals("Opis", result.getDescription());
		assertEquals("1", result.getCategory().getId());
		assertEquals("1", result.getOrganization().getId());
		assertTrue(result.isActive());
	}

	@Test
	public void createPostUnauthorized() {

		authenticateUser("ffranjic", "ffranjic");

		PostDTO postDTO = PostDTO.builder()
				.title("Novi")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(OrganizationDTO.builder().id("2").build())
				.build();

		HttpEntity<PostDTO> entity = new HttpEntity<>(postDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.UNAUTHORIZED.toString(), response.getBody().getErrorCode());
	}

	@Test
	public void createPostBadRequest() {

		authenticateUser("ffranjic", "ffranjic");

		PostDTO postDTO = PostDTO.builder()
				.title("")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(OrganizationDTO.builder().id("1").build())
				.build();

		HttpEntity<PostDTO> entity = new HttpEntity<>(postDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());

		postDTO = PostDTO.builder()
				.title("Novi")
				.description("")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(OrganizationDTO.builder().id("1").build())
				.build();

		entity = new HttpEntity<>(postDTO, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());

		postDTO = PostDTO.builder()
				.title("Novi")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("").build())
				.organization(OrganizationDTO.builder().id("1").build())
				.build();

		entity = new HttpEntity<>(postDTO, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());

		postDTO = PostDTO.builder()
				.title("Novi")
				.description("Opis")
				.category(null)
				.organization(OrganizationDTO.builder().id("1").build())
				.build();

		entity = new HttpEntity<>(postDTO, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());

		postDTO = PostDTO.builder()
				.title("Novi")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(OrganizationDTO.builder().id("").build())
				.build();

		entity = new HttpEntity<>(postDTO, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());

		postDTO = PostDTO.builder()
				.title("Novi")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(null)
				.build();

		entity = new HttpEntity<>(postDTO, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/posts"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());
	}

	@Test
	@DirtiesContext
	public void updatePost() throws PocketException {

		authenticateUser("ffranjic", "ffranjic");

		PostDTO postDTO = PostDTO.builder()
				.title("ažurirani")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(OrganizationDTO.builder().id("1").build())
				.active(false)
				.build();

		HttpEntity<PostDTO> entity = new HttpEntity<>(postDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/posts/1"),
				HttpMethod.PUT, entity, String.class);

		PostDTO result = getDTOObjectFromBody(response, PostDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("1", result.getId());
		assertEquals("ažurirani", result.getTitle());
		assertEquals("Opis", result.getDescription());
		assertEquals("1", result.getCategory().getId());
		assertEquals("1", result.getOrganization().getId());
		assertFalse(result.isActive());
	}

	@Test
	public void updatePostNonExistingPost() {

		authenticateUser("ffranjic", "ffranjic");

		PostDTO postDTO = PostDTO.builder()
				.title("ažurirani")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(OrganizationDTO.builder().id("1").build())
				.active(false)
				.build();

		HttpEntity<PostDTO> entity = new HttpEntity<>(postDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/posts/0"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_POST.toString(), response.getBody().getErrorCode());

		response = restTemplate.exchange(
				createURLWithPort("/api/posts/4"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_POST.toString(), response.getBody().getErrorCode());
	}

	@Test
	public void updatePostBadRequest() {

		authenticateUser("ffranjic", "ffranjic");

		PostDTO postDTO = PostDTO.builder()
				.title("")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(OrganizationDTO.builder().id("1").build())
				.active(false)
				.build();

		HttpEntity<PostDTO> entity = new HttpEntity<>(postDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/posts/1"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());

		postDTO = PostDTO.builder()
				.title("ažurirani")
				.description("")
				.category(EditableCodeBookEntryDTO.builder().id("1").build())
				.organization(OrganizationDTO.builder().id("1").build())
				.active(false)
				.build();

		entity = new HttpEntity<>(postDTO, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/posts/1"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());

		postDTO = PostDTO.builder()
				.title("ažurirani")
				.description("Opis")
				.category(EditableCodeBookEntryDTO.builder().id("").build())
				.organization(OrganizationDTO.builder().id("1").build())
				.active(false)
				.build();

		entity = new HttpEntity<>(postDTO, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/posts/1"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());

		postDTO = PostDTO.builder()
				.title("ažurirani")
				.description("Opis")
				.category(null)
				.organization(OrganizationDTO.builder().id("1").build())
				.active(false)
				.build();

		entity = new HttpEntity<>(postDTO, headers);

		response = restTemplate.exchange(
				createURLWithPort("/api/posts/1"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.BAD_REQUEST.toString(), response.getBody().getErrorCode());
	}
}
