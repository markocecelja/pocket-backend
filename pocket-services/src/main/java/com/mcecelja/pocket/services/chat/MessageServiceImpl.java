package com.mcecelja.pocket.services.chat;

import com.mcecelja.pocket.common.dto.chat.MessageDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.common.mappers.chat.MessageMapper;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.specification.criteria.MessageSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class MessageServiceImpl implements MessageService {

	private final MessageManager messageManager;

	private final MessageMapper messageMapper;

	@Override
	public Page<MessageDTO> getMessages(MessageSearchCriteria messageSearchCriteria, Pageable pageable) {

		User currentUser = AuthorizedRequestContext.getCurrentUser();
		messageSearchCriteria.setCurrentUser(currentUser);

		return messageManager.getMessages(messageSearchCriteria, pageable).map(messageMapper::messageToMessageDTO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MessageDTO createMessage(MessageDTO messageDTO) throws PocketException {
		return messageMapper.messageToMessageDTO(messageManager.createMessage(messageDTO));
	}
}
