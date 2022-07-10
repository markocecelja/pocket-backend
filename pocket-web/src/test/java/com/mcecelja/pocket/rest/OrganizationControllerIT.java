package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.PocketContextAwareIT;
import com.mcecelja.pocket.common.dto.organization.OrganizationCodeDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationMemberDTO;
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

public class OrganizationControllerIT extends PocketContextAwareIT {

	@Test
	public void getOrganizations() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/organizations"),
				HttpMethod.GET, entity, String.class);

		List<OrganizationDTO> result = getListFromBody(response, OrganizationDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, result.size());
	}

	@Test
	public void getOrganizationsFilters() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/organizations?memberId=2"),
				HttpMethod.GET, entity, String.class);

		List<OrganizationDTO> result = getListFromBody(response, OrganizationDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(1, result.size());
	}

	@Test
	public void getOrganization() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/organizations/1"),
				HttpMethod.GET, entity, String.class);

		OrganizationDTO result = getDTOObjectFromBody(response, OrganizationDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("1", result.getId());
		assertEquals("Testna organizacija", result.getName());
		assertEquals("Opis testne organizacije", result.getDescription());
		assertTrue(result.isActive());
		assertNotNull(result.getOrganizationCode());
		assertEquals("1", result.getOrganizationCode().getId());
		assertEquals("A18rT56PO9", result.getOrganizationCode().getValue());
	}

	@Test
	public void getOrganizationNonExistingOrganization() {

		authenticateUser("ikovac", "ikovac");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/organizations/0"),
				HttpMethod.GET, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_ORGANIZATION.toString(), response.getBody().getErrorCode());
	}

	@Test
	@DirtiesContext
	public void createOrganization() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		OrganizationDTO organizationDTO = OrganizationDTO.builder().name("Nova organizacija").description("Opis nove organizacije").build();

		HttpEntity<OrganizationDTO> entity = new HttpEntity<>(organizationDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/organizations"),
				HttpMethod.POST, entity, String.class);

		OrganizationDTO result = getDTOObjectFromBody(response, OrganizationDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("3", result.getId());
		assertEquals("Nova organizacija", result.getName());
		assertEquals("Opis nove organizacije", result.getDescription());
		assertTrue(result.isActive());
		assertNotNull(result.getOrganizationCode());
		assertEquals("3", result.getOrganizationCode().getId());
	}

	@Test
	public void createOrganizationUnauthorized() {

		authenticateUser("iradic", "iradic");

		OrganizationDTO organizationDTO = OrganizationDTO.builder().name("Nova organizacija").description("Opis nove organizacije").build();

		HttpEntity<OrganizationDTO> entity = new HttpEntity<>(organizationDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/organizations"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.UNAUTHORIZED.toString(), response.getBody().getErrorCode());
	}

	@Test
	@DirtiesContext
	public void updateOrganizationSystemAdmin() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		OrganizationDTO organizationDTO = OrganizationDTO.builder().name("Ažurirana organizacija").description("Opis ažurirane organizacije").active(false).build();

		HttpEntity<OrganizationDTO> entity = new HttpEntity<>(organizationDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/organizations/1"),
				HttpMethod.PUT, entity, String.class);

		OrganizationDTO result = getDTOObjectFromBody(response, OrganizationDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("1", result.getId());
		assertEquals("Ažurirana organizacija", result.getName());
		assertEquals("Opis ažurirane organizacije", result.getDescription());
		assertFalse(organizationDTO.isActive());
	}

	@Test
	@DirtiesContext
	public void updateOrganizationOrganizationAdmin() throws PocketException {

		authenticateUser("ffranjic", "ffranjic");

		OrganizationDTO organizationDTO = OrganizationDTO.builder().name("Ažurirana organizacija").description("Opis ažurirane organizacije").active(false).build();

		HttpEntity<OrganizationDTO> entity = new HttpEntity<>(organizationDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/organizations/1"),
				HttpMethod.PUT, entity, String.class);

		OrganizationDTO result = getDTOObjectFromBody(response, OrganizationDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("1", result.getId());
		assertEquals("Ažurirana organizacija", result.getName());
		assertEquals("Opis ažurirane organizacije", result.getDescription());
		assertTrue(result.isActive());
	}

	@Test
	public void updateOrganizationNonExistingOrganization() {

		authenticateUser("ikovac", "ikovac");

		OrganizationDTO organizationDTO = OrganizationDTO.builder().name("Ažurirana organizacija").description("Opis ažurirane organizacije").active(false).build();

		HttpEntity<OrganizationDTO> entity = new HttpEntity<>(organizationDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/organizations/0"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_ORGANIZATION.toString(), response.getBody().getErrorCode());
	}

	@Test
	public void updateOrganizationUnauthorized() {

		authenticateUser("adukic", "adukic");

		OrganizationDTO organizationDTO = OrganizationDTO.builder().name("Ažurirana organizacija").description("Opis ažurirane organizacije").active(false).build();

		HttpEntity<OrganizationDTO> entity = new HttpEntity<>(organizationDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/organizations/1"),
				HttpMethod.PUT, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.UNAUTHORIZED.toString(), response.getBody().getErrorCode());
	}

	@Test
	public void getOrganizationMembers() throws PocketException {

		authenticateUser("ikovac", "ikovac");

		HttpEntity<String> entity = new HttpEntity<>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/organizations/1/members"),
				HttpMethod.GET, entity, String.class);

		List<OrganizationMemberDTO> result = getListFromBody(response, OrganizationMemberDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(2, result.size());
	}

	@Test
	@DirtiesContext
	public void joinOrganization() throws PocketException {

		authenticateUser("pperic", "pperic");

		OrganizationCodeDTO organizationCodeDTO = OrganizationCodeDTO.builder().value("A18rT56PO9").build();

		HttpEntity<OrganizationCodeDTO> entity = new HttpEntity<>(organizationCodeDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/organizations/join"),
				HttpMethod.POST, entity, String.class);

		OrganizationDTO result = getDTOObjectFromBody(response, OrganizationDTO.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("1", result.getId());
	}

	@Test
	public void joinOrganizationNonExistingOrganization() {

		authenticateUser("ffranjic", "ffranjic");

		OrganizationCodeDTO organizationCodeDTO = OrganizationCodeDTO.builder().value("xxxxxxxxx").build();

		HttpEntity<OrganizationCodeDTO> entity = new HttpEntity<>(organizationCodeDTO, headers);

		ResponseEntity<ResponseMessage> response = restTemplate.exchange(
				createURLWithPort("/api/organizations/join"),
				HttpMethod.POST, entity, ResponseMessage.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(PocketError.NON_EXISTING_ORGANIZATION.toString(), response.getBody().getErrorCode());
	}
}
