package com.mcecelja.pocket.domain.user;

import com.mcecelja.pocket.domain.AbstractBaseEntity;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.domain.user.codebook.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@NamedEntityGraph(name = "user_graph",
		attributeNodes = {
				@NamedAttributeNode(value = "organizationMembers")
		}
)
public class User extends AbstractBaseEntity implements Serializable {

	@Id
	@GeneratedValue(generator = "user_sequence", strategy = GenerationType.SEQUENCE)
	@GenericGenerator(
			name = "user_sequence",
			strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
			parameters = {
					@Parameter(name = "sequence_name", value = "users_id_seq"),
			}
	)
	private Long id;

	private String firstName;

	private String lastName;

	@ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinTable(
			name = "user_role",
			joinColumns = {@JoinColumn(name = "user_id")},
			inverseJoinColumns = {@JoinColumn(name = "role_id")},
			indexes = {@Index(name = "idx_user_role_user_id", columnList = "user_id"),
					@Index(name = "idx_user_role_role_id", columnList = "role_id")}
	)
	private Set<Role> roles;

	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private UserLogin userLogin;

	@OneToMany(mappedBy = "organizationMemberPK.user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrganizationMember> organizationMembers;
}
