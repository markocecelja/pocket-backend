package com.mcecelja.pocket.services.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationCodeDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.common.dto.organization.OrganizationMemberDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.common.mappers.organization.OrganizationMapper;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.organization.codebook.OrganizationRoleEnum;
import com.mcecelja.pocket.managers.organization.OrganizationManager;
import com.mcecelja.pocket.managers.organization.member.OrganizationMemberManager;
import com.mcecelja.pocket.specification.criteria.OrganizationSearchCriteria;
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

	private final OrganizationManager organizationManager;

	private final OrganizationMapper organizationMapper;

	@Override
	public Page<OrganizationMemberDTO> getOrganizationMembers(Long organizationId, Pageable pageable) {
		return organizationMemberManager.getOrganizationMembers(organizationId, pageable).map(organizationMapper::organizationMemberToOrganizationMemberDTO);
	}

	@Override
	public OrganizationDTO joinOrganizationByCode(OrganizationCodeDTO organizationCodeDTO) throws PocketException {

		OrganizationSearchCriteria organizationSearchCriteria = OrganizationSearchCriteria.builder().code(organizationCodeDTO.getValue()).build();
		Organization organization = organizationManager.getOrganization(organizationSearchCriteria);

		organizationMemberManager.addMemberToOrganization(organization, AuthorizedRequestContext.getCurrentUser(), OrganizationRoleEnum.MEMBER);

		return organizationMapper.organizationToOrganizationDTO(organization);
	}
}
