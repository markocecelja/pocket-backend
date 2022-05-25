package com.mcecelja.pocket.common.dto.organization;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationCodeDTO {

	private String id;

	@NotBlank
	private String value;

	private String expirationDate;
}
