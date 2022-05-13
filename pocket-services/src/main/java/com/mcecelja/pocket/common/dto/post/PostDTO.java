package com.mcecelja.pocket.common.dto.post;

import com.mcecelja.pocket.common.dto.codebook.EditableCodeBookEntryDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostDTO {

	private String id;

	@NotBlank
	private String title;

	@NotBlank
	private String description;

	@NotNull
	private EditableCodeBookEntryDTO category;

	@NotNull
	private OrganizationDTO organization;

	private boolean active;
}
