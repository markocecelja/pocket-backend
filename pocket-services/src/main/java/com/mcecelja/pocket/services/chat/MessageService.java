package com.mcecelja.pocket.services.chat;

import com.mcecelja.pocket.common.dto.chat.MessageDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.specification.criteria.MessageSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {

	Page<MessageDTO> getMessages(MessageSearchCriteria messageSearchCriteria, Pageable pageable);

	MessageDTO createMessage(MessageDTO messageDTO) throws PocketException;
}
