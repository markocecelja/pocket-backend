package com.mcecelja.pocket.domain.organization;

import com.mcecelja.pocket.domain.AbstractBaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Organization extends AbstractBaseEntity {

	@Id
	@GeneratedValue(generator = "organization_sequence", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
			name = "organization_sequence",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "organization_id_seq"),
			}
	)
	private Long id;

	private String name;

	@Column(columnDefinition = "TEXT")
	private String description;

	private boolean active;

	@OneToOne(mappedBy = "organization", cascade = CascadeType.PERSIST, orphanRemoval = true)
	private OrganizationCode organizationCode;

	@OneToMany(mappedBy = "organizationMemberPK.organization", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrganizationMember> members;
}
