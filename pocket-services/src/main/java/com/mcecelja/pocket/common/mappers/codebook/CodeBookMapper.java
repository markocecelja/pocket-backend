package com.mcecelja.pocket.common.mappers.codebook;

import com.mcecelja.pocket.common.dto.codebook.CodeBookEntryDTO;
import com.mcecelja.pocket.common.dto.codebook.EditableCodeBookEntryDTO;
import com.mcecelja.pocket.domain.AbstractCodeBookEntity;
import com.mcecelja.pocket.domain.AbstractEditableCodeBookEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class CodeBookMapper {

	public abstract CodeBookEntryDTO codeBookEntityToCodeBookEntryDTO(AbstractCodeBookEntity entity);

	public abstract EditableCodeBookEntryDTO editableCodeBookEntryToEditableCodeBookEntryDTO(AbstractEditableCodeBookEntity entity);
}
