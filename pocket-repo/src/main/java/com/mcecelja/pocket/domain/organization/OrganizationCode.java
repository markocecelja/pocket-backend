package com.mcecelja.pocket.domain.organization;

import com.mcecelja.pocket.domain.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationCode extends AbstractBaseEntity {

	@Id
	@GeneratedValue(generator = "organization_code_sequence", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
			name = "organization_code_sequence",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "organization_code_id_seq"),
			}
	)
	private Long id;

	private String value;

	private LocalDateTime expirationDate;

	@OneToOne
	private Organization organization;
}
