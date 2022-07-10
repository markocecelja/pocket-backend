package com.mcecelja.pocket.domain.post.codebook;

import com.mcecelja.pocket.domain.AbstractEditableCodeBookEntity;
import com.mcecelja.pocket.domain.post.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Category extends AbstractEditableCodeBookEntity {

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Post> posts;
}
