package com.mcecelja.pocket.domain.organization.codebook;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OrganizationRoleEnum {

	ADMIN(1L),
	MEMBER(2L);

	private final Long id;
}
