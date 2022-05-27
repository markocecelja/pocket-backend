package com.mcecelja.pocket.services.chat;

import com.mcecelja.pocket.common.dto.chat.ChatDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.specification.criteria.ChatSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatService {

	Page<ChatDTO> getChats(ChatSearchCriteria chatSearchCriteria, Pageable pageable);

	ChatDTO getChat(Long id) throws PocketException;
}
