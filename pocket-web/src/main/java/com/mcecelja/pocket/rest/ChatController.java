package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.common.dto.chat.ChatDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.services.chat.ChatService;
import com.mcecelja.pocket.specification.criteria.ChatSearchCriteria;
import com.mcecelja.pocket.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chats")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatController {

	private final ChatService chatService;

	@GetMapping("")
	public ResponseEntity<ResponseMessage<Page<ChatDTO>>> getChats(@RequestParam(required = false) Long postId,
	                                                               @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

		ChatSearchCriteria chatSearchCriteria = ChatSearchCriteria.builder()
				.postId(postId)
				.build();

		return ResponseEntity.ok(new ResponseMessage<>(chatService.getChats(chatSearchCriteria, pageable)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseMessage<ChatDTO>> getChat(@PathVariable Long id) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(chatService.getChat(id)));
	}
}
