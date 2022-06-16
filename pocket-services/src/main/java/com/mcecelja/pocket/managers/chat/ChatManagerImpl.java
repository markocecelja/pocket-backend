package com.mcecelja.pocket.managers.chat;

import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.chat.Chat;
import com.mcecelja.pocket.domain.post.Post;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.managers.post.PostManager;
import com.mcecelja.pocket.repositories.chat.ChatRepository;
import com.mcecelja.pocket.specification.ChatSearchSpecification;
import com.mcecelja.pocket.specification.criteria.ChatSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatManagerImpl implements ChatManager {

	private final ChatRepository chatRepository;

	private final PostManager postManager;

	@Override
	public Page<Chat> getChatsBySearchCriteria(ChatSearchCriteria chatSearchCriteria, Pageable pageable) {
		return chatRepository.findAll(ChatSearchSpecification.findChats(chatSearchCriteria), pageable);
	}

	@Override
	public Chat getChat(ChatSearchCriteria criteria) throws PocketException {

		Optional<Chat> chatOptional = chatRepository.findOne(ChatSearchSpecification.findChats(criteria));

		if (!chatOptional.isPresent()) {
			log.warn("Chat {} doesn't exist!", criteria.getId());
			throw new PocketException(PocketError.NON_EXISTING_CHAT);
		}

		return chatOptional.get();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Chat createChat(Long postId, User user) throws PocketException {

		Post post = postManager.getPost(postId);

		Chat chat = new Chat();
		chat.setPost(post);
		chat.setUser(user);

		chatRepository.save(chat);

		return chat;
	}
}
