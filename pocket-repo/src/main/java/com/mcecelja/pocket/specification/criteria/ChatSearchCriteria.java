package com.mcecelja.pocket.specification.criteria;

import com.mcecelja.pocket.domain.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatSearchCriteria {

	private Long id;

	private Long postId;

	private Long userId;

	private User currentUser;
}
