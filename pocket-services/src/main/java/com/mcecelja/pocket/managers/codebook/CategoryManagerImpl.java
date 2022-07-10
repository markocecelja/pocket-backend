package com.mcecelja.pocket.managers.codebook;

import com.mcecelja.pocket.common.dto.codebook.EditableCodeBookEntryDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.AbstractEditableCodeBookEntity;
import com.mcecelja.pocket.domain.post.codebook.Category;
import com.mcecelja.pocket.repositories.offer.codebook.CategoryRepository;
import com.mcecelja.pocket.specification.EditableCodeBookSearchSpecification;
import com.mcecelja.pocket.specification.criteria.EditableCodeBookSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryManagerImpl implements EditableCodeBookManager {

	private final CategoryRepository categoryRepository;

	@Override
	public Page<AbstractEditableCodeBookEntity> getCodeBookEntities(EditableCodeBookSearchCriteria criteria, Pageable pageable) {
		return categoryRepository.findAll(EditableCodeBookSearchSpecification.findEditableCodebookEntities(criteria), pageable).map(category -> category);
	}

	@Override
	public AbstractEditableCodeBookEntity getCodeBookEntity(EditableCodeBookSearchCriteria criteria) throws PocketException {

		Optional<AbstractEditableCodeBookEntity> categoryOptional = categoryRepository.findOne(EditableCodeBookSearchSpecification.findEditableCodebookEntities(criteria));

		if (!categoryOptional.isPresent()) {
			log.warn("Category {} doesn't exist!", criteria.getId());
			throw new PocketException(PocketError.NON_EXISTING_CATEGORY);
		}

		return categoryOptional.get();
	}

	@Override
	public AbstractEditableCodeBookEntity createCodeBookEntity(EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException {

		if (editableCodeBookEntryDTO.getName() == null || editableCodeBookEntryDTO.getName().trim().isEmpty()) {
			log.warn("Failed creating category: name can't be null or empty");
			throw new PocketException(PocketError.BAD_REQUEST);
		}

		Category category = new Category();
		category.setName(editableCodeBookEntryDTO.getName());
		category.setActive(true);

		categoryRepository.save(category);

		return category;
	}

	@Override
	public void updateCodeBookEntity(AbstractEditableCodeBookEntity codeBookEntity, EditableCodeBookEntryDTO editableCodeBookEntryDTO) throws PocketException {

		if (editableCodeBookEntryDTO.getName() == null || editableCodeBookEntryDTO.getName().trim().isEmpty()) {
			log.warn("Failed updating category: name can't be null or empty");
			throw new PocketException(PocketError.BAD_REQUEST);
		}

		Category category = (Category) codeBookEntity;

		category.setName(editableCodeBookEntryDTO.getName());
		category.setActive(editableCodeBookEntryDTO.isActive());

		if(!category.isActive()) {
			category.getPosts().forEach(post -> post.setActive(false));
		}

		categoryRepository.save(category);
	}
}
