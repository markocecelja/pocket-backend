package com.mcecelja.pocket.specification.criteria;

import com.mcecelja.pocket.domain.user.User;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MessageSearchCriteria {

	private Long chatId;

	private User currentUser;
}
