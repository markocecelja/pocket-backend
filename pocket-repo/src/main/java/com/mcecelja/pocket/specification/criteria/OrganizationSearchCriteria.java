package com.mcecelja.pocket.specification.criteria;

import com.mcecelja.pocket.domain.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationSearchCriteria {

	private Long id;

	private String code;

	private Long memberId;

	private User currentUser;

	private String name;
}
