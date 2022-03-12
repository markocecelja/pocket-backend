package com.mcecelja.pocket.common.mappers.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationDTO;
import com.mcecelja.pocket.domain.organization.Organization;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrganizationCodeMapper.class})
public abstract class OrganizationMapper {

	@Mappings({
			@Mapping(target = "organizationCode", source = "organizationCode")
	})
	public abstract OrganizationDTO organizationToOrganizationDTO(Organization entity);
}
