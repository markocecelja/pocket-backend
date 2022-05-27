package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.common.dto.chat.MessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin(origins = "*")
public class WebSocketController {

	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public MessageDTO send(MessageDTO messageDTO) {
		return messageDTO;
	}
}
