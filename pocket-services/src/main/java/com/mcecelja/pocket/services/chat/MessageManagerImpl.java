package com.mcecelja.pocket.services.chat;

import com.mcecelja.pocket.common.dto.chat.MessageDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.chat.Chat;
import com.mcecelja.pocket.domain.chat.Message;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.managers.chat.ChatManager;
import com.mcecelja.pocket.repositories.chat.MessageRepository;
import com.mcecelja.pocket.specification.MessageSearchSpecification;
import com.mcecelja.pocket.specification.criteria.ChatSearchCriteria;
import com.mcecelja.pocket.specification.criteria.MessageSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageManagerImpl implements MessageManager {

	private final MessageRepository messageRepository;

	private final ChatManager chatManager;

	@Override
	public Page<Message> getMessages(MessageSearchCriteria criteria, Pageable pageable) {
		return messageRepository.findAll(MessageSearchSpecification.findMessages(criteria), pageable);
	}

	@Override
	public Message createMessage(MessageDTO messageDTO) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		Long postId = messageDTO.getChat() != null && messageDTO.getChat().getPost() != null ? Long.valueOf(messageDTO.getChat().getPost().getId()) : null;

		ChatSearchCriteria criteria = ChatSearchCriteria.builder()
				.postId(postId)
				.currentUser(currentUser)
				.build();

		Chat chat;

		try {
			chat = chatManager.getChat(criteria);
		} catch (PocketException pocketException) {
			chat = chatManager.createChat(postId, currentUser);
		}

		Message message = new Message();
		message.setText(messageDTO.getText());
		message.setCreatedBy(currentUser);
		message.setChat(chat);

		messageRepository.saveAndFlush(message);

		return message;
	}
}
