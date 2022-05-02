package com.mcecelja.pocket.services.codebook;

import com.mcecelja.pocket.common.dto.codebook.EditableCodeBookEntryDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.common.mappers.codebook.CodeBookMapper;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.offer.codebook.Category;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.domain.user.codebook.RoleEnum;
import com.mcecelja.pocket.managers.codebook.CodeBookManager;
import com.mcecelja.pocket.services.common.PermissionCheckerService;
import com.mcecelja.pocket.specification.criteria.EditableCodeBookSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CodeBookService {

	private final PermissionCheckerService permissionCheckerService;

	@Qualifier("categoryManagerImpl")
	private final CodeBookManager codeBookManager;

	private final CodeBookMapper codeBookMapper;

	@Override
	public Page<EditableCodeBookEntryDTO> getCodeBookEntries(EditableCodeBookSearchCriteria criteria, Pageable pageable) {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		if(!permissionCheckerService.checkUserHasRole(currentUser, RoleEnum.SYSTEM_ADMIN)) {
			criteria.setActive(true);
		}

		return codeBookManager.getCodeBookEntities(criteria, pageable).map(codeBookMapper::editableCodeBookEntryToEditableCodeBookEntryDTO);
	}

	@Override
	public EditableCodeBookEntryDTO getCodeBookEntry(Long id) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		EditableCodeBookSearchCriteria criteria = new EditableCodeBookSearchCriteria();
		criteria.setId(id);

		if(!permissionCheckerService.checkUserHasRole(currentUser, RoleEnum.SYSTEM_ADMIN)) {
			criteria.setActive(true);
		}

		return codeBookMapper.editableCodeBookEntryToEditableCodeBookEntryDTO(codeBookManager.getCodeBookEntity(criteria));
	}

	@Override
	public EditableCodeBookEntryDTO createCodeBookEntry(EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		if(!permissionCheckerService.checkUserHasRole(currentUser, RoleEnum.SYSTEM_ADMIN)) {
			log.warn("User {} can't create category!", currentUser.getId());
			throw new PocketException(PocketError.UNAUTHORIZED);
		}

		Category category = (Category) codeBookManager.createCodeBookEntity(editableCodeBookEntryDTO);

		return codeBookMapper.editableCodeBookEntryToEditableCodeBookEntryDTO(category);
	}

	@Override
	public EditableCodeBookEntryDTO updateCodeBookEntry(Long id, EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		if(!permissionCheckerService.checkUserHasRole(currentUser, RoleEnum.SYSTEM_ADMIN)) {
			log.warn("User {} can't update category!", currentUser.getId());
			throw new PocketException(PocketError.UNAUTHORIZED);
		}

		Category category = (Category) codeBookManager.getCodeBookEntity(EditableCodeBookSearchCriteria.builder().id(id).build());

		codeBookManager.updateCodeBookEntity(category, editableCodeBookEntryDTO);

		return codeBookMapper.editableCodeBookEntryToEditableCodeBookEntryDTO(category);
	}
}
