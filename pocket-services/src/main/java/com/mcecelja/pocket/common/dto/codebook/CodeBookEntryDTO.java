package com.mcecelja.pocket.common.dto.codebook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeBookEntryDTO {
	private String id;

	@Size(max = 128)
	private String name;

	@Size(max = 512)
	private String description;
}
