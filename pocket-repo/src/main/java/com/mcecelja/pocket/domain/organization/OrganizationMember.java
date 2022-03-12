package com.mcecelja.pocket.domain.organization;

import com.mcecelja.pocket.domain.organization.codebook.OrganizationRole;
import com.mcecelja.pocket.domain.organization.primaryKey.OrganizationMemberPK;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationMember {

	@EmbeddedId
	private OrganizationMemberPK organizationMemberPK;

	@ManyToOne
	private OrganizationRole organizationRole;
}
