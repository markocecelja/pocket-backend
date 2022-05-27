package com.mcecelja.pocket.services.chat;

import com.mcecelja.pocket.common.dto.chat.ChatDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.common.mappers.chat.ChatMapper;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.managers.chat.ChatManager;
import com.mcecelja.pocket.specification.criteria.ChatSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatServiceImpl implements ChatService {

	private final ChatManager chatManager;

	private final ChatMapper chatMapper;

	@Override
	public Page<ChatDTO> getChats(ChatSearchCriteria chatSearchCriteria, Pageable pageable) {

		User currentUser = AuthorizedRequestContext.getCurrentUser();
		chatSearchCriteria.setCurrentUser(currentUser);

		return chatManager.getChatsBySearchCriteria(chatSearchCriteria, pageable).map(chatMapper::chatToChatDTO);
	}

	@Override
	public ChatDTO getChat(Long id) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		ChatSearchCriteria chatSearchCriteria = ChatSearchCriteria.builder().id(id).currentUser(currentUser).build();

		return chatMapper.chatToChatDTO(chatManager.getChat(chatSearchCriteria));
	}
}
