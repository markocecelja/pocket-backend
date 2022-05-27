package com.mcecelja.pocket.common.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

	private String id;

	@NotBlank
	private String text;

	private String creator;

	private boolean createdByCurrentUser;

	@NotNull
	private ChatDTO chat;

	private String createdDateTime;
}
