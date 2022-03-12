package com.mcecelja.pocket.domain.organization.primaryKey;

import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrganizationMemberPK implements Serializable {

	@ManyToOne
	private Organization organization;

	@ManyToOne
	private User user;
}
