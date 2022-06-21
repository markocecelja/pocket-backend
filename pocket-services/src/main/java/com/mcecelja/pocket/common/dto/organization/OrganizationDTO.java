package com.mcecelja.pocket.common.dto.organization;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationDTO {

	private String id;

	@NotBlank
	private String name;

	@NotBlank
	private String description;

	private boolean active;

	private boolean verified;

	private OrganizationCodeDTO organizationCode;

	private OrganizationMemberDTO currentUserMember;
}
