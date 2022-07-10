package com.mcecelja.pocket.common.dto.authentication;

import com.mcecelja.pocket.common.dto.codebook.CodeBookEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDTO {

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotBlank
	private String username;

	@NotBlank
	private String password;

	@NotBlank
	private String confirmationPassword;

	@NotNull
	private CodeBookEntryDTO role;
}
