package com.mcecelja.pocket.managers.codebook;

import com.mcecelja.pocket.common.dto.codebook.EditableCodeBookEntryDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.AbstractEditableCodeBookEntity;
import com.mcecelja.pocket.specification.criteria.EditableCodeBookSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeBookManager {

	Page<AbstractEditableCodeBookEntity> getCodeBookEntities(EditableCodeBookSearchCriteria criteria, Pageable pageable);

	AbstractEditableCodeBookEntity getCodeBookEntity(EditableCodeBookSearchCriteria criteria) throws PocketException;

	AbstractEditableCodeBookEntity createCodeBookEntity(EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException;

	void updateCodeBookEntity(AbstractEditableCodeBookEntity abstractEditableCodeBookEntity, EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException;
}
