package com.mcecelja.pocket.services.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationMemberDTO;
import com.mcecelja.pocket.common.mappers.organization.OrganizationMapper;
import com.mcecelja.pocket.domain.organization.OrganizationMember;
import com.mcecelja.pocket.managers.organization.member.OrganizationMemberManager;
import com.mcecelja.pocket.repositories.organization.OrganizationMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrganizationMemberServiceImpl implements OrganizationMemberService {

	private final OrganizationMemberManager organizationMemberManager;

	private final OrganizationMapper organizationMapper;

	@Override
	public Page<OrganizationMemberDTO> getOrganizationMembers(Long organizationId, Pageable pageable) {
		return organizationMemberManager.getOrganizationMembers(organizationId, pageable).map(organizationMapper::organizationMemberToOrganizationMemberDTO);
	}
}
