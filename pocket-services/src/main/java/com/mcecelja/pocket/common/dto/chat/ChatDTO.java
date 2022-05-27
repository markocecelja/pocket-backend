package com.mcecelja.pocket.common.dto.chat;

import com.mcecelja.pocket.common.dto.post.PostDTO;
import com.mcecelja.pocket.common.dto.user.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatDTO {

	private String id;

	private UserDTO user;

	private PostDTO post;
}
