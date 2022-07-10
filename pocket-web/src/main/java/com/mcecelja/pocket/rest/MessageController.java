package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.common.dto.chat.MessageDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.services.chat.MessageService;
import com.mcecelja.pocket.specification.criteria.MessageSearchCriteria;
import com.mcecelja.pocket.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageController {

	private final MessageService messageService;

	private final SimpMessagingTemplate template;

	@GetMapping("")
	public ResponseEntity<ResponseMessage<Page<MessageDTO>>> getMessages(@RequestParam Long postId,
	                                                                     @RequestParam Long userId,
	                                                                     @PageableDefault(size = 20, sort = "createdDateTime", direction = Sort.Direction.DESC) Pageable pageable) {

		MessageSearchCriteria messageSearchCriteria = MessageSearchCriteria.builder()
				.postId(postId)
				.userId(userId)
				.build();

		return ResponseEntity.ok(new ResponseMessage<>(messageService.getMessages(messageSearchCriteria, pageable)));
	}

	@PostMapping("")
	public ResponseEntity<ResponseMessage<MessageDTO>> createMessages(@Valid @RequestBody MessageDTO messageDTO) throws PocketException {

		MessageDTO createdMessage = messageService.createMessage(messageDTO);
		template.convertAndSend("/topic/messages", createdMessage);

		return ResponseEntity.ok(new ResponseMessage<>(messageDTO));
	}
}
