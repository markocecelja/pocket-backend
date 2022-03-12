package com.mcecelja.pocket.common.dto.organization;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationDTO {

	private String id;

	private String name;

	private String description;

	private boolean active;

	private OrganizationCodeDTO organizationCode;
}
