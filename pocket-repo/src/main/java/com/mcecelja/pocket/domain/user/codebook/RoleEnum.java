package com.mcecelja.pocket.domain.user.codebook;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleEnum {

	SYSTEM_ADMIN(1L),
	ORGANIZATION_MEMBER(2L),
	STUDENT(3L);

	private final Long id;
}
