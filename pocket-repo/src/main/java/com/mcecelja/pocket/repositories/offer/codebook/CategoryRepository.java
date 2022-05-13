package com.mcecelja.pocket.repositories.offer.codebook;

import com.mcecelja.pocket.domain.AbstractEditableCodeBookEntity;
import com.mcecelja.pocket.domain.post.codebook.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CategoryRepository extends JpaRepository<Category, Long>, JpaSpecificationExecutor<AbstractEditableCodeBookEntity> {
}
