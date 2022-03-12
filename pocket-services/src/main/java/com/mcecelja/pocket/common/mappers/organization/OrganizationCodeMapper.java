package com.mcecelja.pocket.common.mappers.organization;

import com.mcecelja.pocket.common.dto.organization.OrganizationCodeDTO;
import com.mcecelja.pocket.domain.organization.OrganizationCode;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class OrganizationCodeMapper {

	public abstract OrganizationCodeDTO organizationCodeToOrganizationCodeDTO(OrganizationCode entity);
}
