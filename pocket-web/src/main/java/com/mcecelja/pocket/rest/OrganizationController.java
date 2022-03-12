package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.services.organization.OrganizationService;
import com.mcecelja.pocket.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/organizations")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationController {

	private final OrganizationService organizationService;

	@GetMapping("/{id}")
	public ResponseEntity<ResponseMessage<OrganizationDTO>> getOrganization(@PathVariable Long id) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(organizationService.getOrganization(id)));
	}

	@PostMapping("")
	public ResponseEntity<ResponseMessage<OrganizationDTO>> createOrganization(@Valid @RequestBody OrganizationDTO organizationDTO) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(organizationService.createOrganization(organizationDTO)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseMessage<OrganizationDTO>> updateOrganization(@PathVariable Long id, @Valid @RequestBody OrganizationDTO organizationDTO) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(organizationService.updateOrganization(id, organizationDTO)));
	}
}
