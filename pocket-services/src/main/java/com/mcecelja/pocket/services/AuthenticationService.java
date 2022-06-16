package com.mcecelja.pocket.services;

import com.mcecelja.pocket.common.dto.authentication.LoginResponseDTO;
import com.mcecelja.pocket.common.dto.authentication.RegistrationRequestDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;

public interface AuthenticationService {

	LoginResponseDTO authenticateUser(String username, String password) throws PocketException;

	void registerUser(RegistrationRequestDTO registrationRequestDTO) throws PocketException;
}
