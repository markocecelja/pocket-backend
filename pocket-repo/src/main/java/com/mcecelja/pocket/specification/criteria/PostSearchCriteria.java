package com.mcecelja.pocket.specification.criteria;

import com.mcecelja.pocket.domain.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostSearchCriteria {

	private Long id;

	private Long organizationId;

	private Long categoryId;

	private String title;

	private User currentUser;
}
