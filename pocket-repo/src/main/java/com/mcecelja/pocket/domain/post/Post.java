package com.mcecelja.pocket.domain.post;

import com.mcecelja.pocket.domain.AbstractBaseEntity;
import com.mcecelja.pocket.domain.post.codebook.Category;
import com.mcecelja.pocket.domain.organization.Organization;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Post extends AbstractBaseEntity {

	@Id
	@GeneratedValue(generator = "post_sequence", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
			name = "post_sequence",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "post_id_seq"),
			}
	)
	private Long id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	private boolean active;

	@ManyToOne
	private Category category;

	@ManyToOne
	private Organization organization;
}
