package com.mcecelja.pocket.common.dto.organization;

import com.mcecelja.pocket.common.dto.codebook.CodeBookEntryDTO;
import com.mcecelja.pocket.common.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationMemberDTO {

	@NotNull
	private UserDTO user;

	@NotNull
	private CodeBookEntryDTO role;
}
