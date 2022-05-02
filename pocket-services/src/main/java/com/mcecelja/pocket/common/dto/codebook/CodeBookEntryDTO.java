package com.mcecelja.pocket.common.dto.codebook;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CodeBookEntryDTO {

	private String id;

	@Size(max = 128)
	private String name;
}
