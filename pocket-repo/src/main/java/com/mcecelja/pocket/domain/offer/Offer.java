package com.mcecelja.pocket.domain.offer;

import com.mcecelja.pocket.domain.AbstractBaseEntity;
import com.mcecelja.pocket.domain.offer.codebook.Category;
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
public class Offer extends AbstractBaseEntity {

	@Id
	@GeneratedValue(generator = "offer_sequence", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
			name = "offer_sequence",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "offer_id_seq"),
			}
	)
	private Long id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@ManyToOne
	private Category category;

	@ManyToOne
	private Organization organization;
}
