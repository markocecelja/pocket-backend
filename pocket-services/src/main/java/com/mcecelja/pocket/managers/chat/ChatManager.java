package com.mcecelja.pocket.managers.chat;

import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.chat.Chat;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.specification.criteria.ChatSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatManager {

	Page<Chat> getChatsBySearchCriteria(ChatSearchCriteria chatSearchCriteria, Pageable pageable);

	Chat getChat(ChatSearchCriteria criteria) throws PocketException;

	Chat createChat(Long postId, User user) throws PocketException;
}
