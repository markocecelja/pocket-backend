package com.mcecelja.pocket.common.dto.user;

import com.mcecelja.pocket.common.dto.codebook.CodeBookEntryDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

	private String id;

	private String firstName;

	private String lastName;

	private List<CodeBookEntryDTO> roles;
}
