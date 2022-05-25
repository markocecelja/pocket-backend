package com.mcecelja.pocket.managers.organization.member;

import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.domain.organization.codebook.OrganizationRole;
import com.mcecelja.pocket.domain.organization.codebook.OrganizationRoleEnum;
import com.mcecelja.pocket.domain.organization.primaryKey.OrganizationMemberPK;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.managers.codebook.CodeBookManager;
import com.mcecelja.pocket.repositories.organization.OrganizationMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationMemberManagerImpl implements OrganizationMemberManager {

	private final OrganizationMemberRepository organizationMemberRepository;

	@Qualifier("organizationRoleManagerImpl")
	private final CodeBookManager organizationRoleManager;

	@Override
	public Page<OrganizationMember> getOrganizationMembers(Long organizationId, Pageable pageable) {
		return organizationMemberRepository.findAllByOrganizationMemberPK_OrganizationId(organizationId, pageable);
	}

	@Override
	public void addMemberToOrganization(Organization organization, User user, OrganizationRoleEnum organizationRoleEnum) throws PocketException {

		OrganizationRole organizationRole = (OrganizationRole) organizationRoleManager.getCodeBookEntity(organizationRoleEnum.getId());

		OrganizationMember organizationMember = new OrganizationMember(new OrganizationMemberPK(organization, user), organizationRole);
		organizationMemberRepository.save(organizationMember);
	}
}
