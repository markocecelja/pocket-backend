package com.mcecelja.pocket.common.dto.organization;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationCodeDTO {

	private String id;

	private String value;

	private String expirationDate;
}
