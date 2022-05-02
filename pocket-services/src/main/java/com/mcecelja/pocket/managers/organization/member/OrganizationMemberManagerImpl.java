package com.mcecelja.pocket.managers.organization.member;

import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.repositories.organization.OrganizationMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationMemberManagerImpl implements OrganizationMemberManager {

	private final OrganizationMemberRepository organizationMemberRepository;

	@Override
	public Page<OrganizationMember> getOrganizationMembers(Long organizationId, Pageable pageable) {
		return organizationMemberRepository.findAllByOrganizationMemberPK_OrganizationId(organizationId, pageable);
	}
}
