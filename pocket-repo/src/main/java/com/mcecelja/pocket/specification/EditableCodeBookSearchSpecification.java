package com.mcecelja.pocket.specification;

import com.mcecelja.pocket.domain.AbstractEditableCodeBookEntity;
import com.mcecelja.pocket.specification.criteria.EditableCodeBookSearchCriteria;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class EditableCodeBookSearchSpecification {

	public static Specification<AbstractEditableCodeBookEntity> findEditableCodebookEntities(final EditableCodeBookSearchCriteria criteria) {

		return (root, criteriaQuery, criteriaBuilder) -> {
			List<Predicate> restrictions = new ArrayList<>();

			Long id = criteria.getId();
			Boolean active = criteria.getActive();

			if (id != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("id"), id)
				);
			}

			if (active != null) {
				restrictions.add(
						criteriaBuilder.equal(root.get("active"), active)
				);
			}

			criteriaQuery.distinct(true);

			return criteriaBuilder.and(restrictions.toArray(new Predicate[0]));
		};
	}
}
