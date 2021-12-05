package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.common.dto.authentication.LoginRequestDTO;
import com.mcecelja.pocket.common.dto.authentication.LoginResponseDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.services.AuthenticationService;
import com.mcecelja.pocket.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/authentication")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

	private final AuthenticationService authenticationService;

	@PostMapping("/login")
	public ResponseEntity<ResponseMessage<LoginResponseDTO>> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) throws PocketException {

		LoginResponseDTO loginResponseDTO = authenticationService.authenticateUser(loginRequestDTO.getUsername(), loginRequestDTO.getPassword());
		return ResponseEntity.ok(new ResponseMessage<>(loginResponseDTO));
	}
}
