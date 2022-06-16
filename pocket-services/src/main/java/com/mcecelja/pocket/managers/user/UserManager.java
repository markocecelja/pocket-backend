package com.mcecelja.pocket.managers.user;

import com.mcecelja.pocket.common.dto.authentication.RegistrationRequestDTO;
import com.mcecelja.pocket.domain.user.User;

public interface UserManager {

	User createUser(RegistrationRequestDTO registrationRequestDTO);
}
