package com.mcecelja.pocket.services.codebook;

import com.mcecelja.pocket.common.dto.codebook.EditableCodeBookEntryDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.specification.criteria.EditableCodeBookSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CodeBookService {

	Page<EditableCodeBookEntryDTO> getCodeBookEntries(EditableCodeBookSearchCriteria criteria, Pageable pageable);

	EditableCodeBookEntryDTO getCodeBookEntry(Long id) throws PocketException;

	EditableCodeBookEntryDTO createCodeBookEntry(EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException;

	EditableCodeBookEntryDTO updateCodeBookEntry(Long id, EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException;
}
