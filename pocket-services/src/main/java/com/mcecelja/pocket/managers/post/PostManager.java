package com.mcecelja.pocket.managers.post;

import com.mcecelja.pocket.common.dto.post.PostDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.domain.post.Post;
import com.mcecelja.pocket.specification.criteria.PostSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostManager {

	Page<Post> getPosts(PostSearchCriteria criteria, Pageable pageable);

	Post getPost(Long id) throws PocketException;

	Post createPost(PostDTO postDTO) throws PocketException;

	void updatePost(Post post, PostDTO postDTO) throws PocketException;
}
