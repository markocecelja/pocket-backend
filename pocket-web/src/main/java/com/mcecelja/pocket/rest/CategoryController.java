package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.common.dto.codebook.EditableCodeBookEntryDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.services.codebook.CodeBookService;
import com.mcecelja.pocket.specification.criteria.EditableCodeBookSearchCriteria;
import com.mcecelja.pocket.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

	@Qualifier("categoryServiceImpl")
	private final CodeBookService categoryService;

	@GetMapping("")
	public ResponseEntity<ResponseMessage<Page<EditableCodeBookEntryDTO>>> getCategories(@RequestParam(required = false) Boolean active,
	                                                                                     @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

		EditableCodeBookSearchCriteria editableCodeBookSearchCriteria = EditableCodeBookSearchCriteria.builder().active(active).build();

		return ResponseEntity.ok(new ResponseMessage<>(categoryService.getCodeBookEntries(editableCodeBookSearchCriteria, pageable)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseMessage<EditableCodeBookEntryDTO>> getCategory(@PathVariable Long id) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(categoryService.getCodeBookEntry(id)));
	}

	@PostMapping("")
	public ResponseEntity<ResponseMessage<EditableCodeBookEntryDTO>> createCategory(@Valid @RequestBody EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(categoryService.createCodeBookEntry(editableCodeBookEntryDTO)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseMessage<EditableCodeBookEntryDTO>> updateCategory(@PathVariable Long id, @Valid @RequestBody EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(categoryService.updateCodeBookEntry(id, editableCodeBookEntryDTO)));
	}
}
