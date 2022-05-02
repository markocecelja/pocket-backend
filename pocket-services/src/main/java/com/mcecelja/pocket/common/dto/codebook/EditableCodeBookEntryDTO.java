package com.mcecelja.pocket.common.dto.codebook;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class EditableCodeBookEntryDTO extends CodeBookEntryDTO {

	private boolean active;
}
