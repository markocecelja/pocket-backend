package com.mcecelja.pocket.services.post;

import com.mcecelja.pocket.common.dto.post.PostDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.specification.criteria.PostSearchCriteria;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

	Page<PostDTO> getPosts(PostSearchCriteria criteria, Pageable pageable);

	PostDTO getPost(Long id) throws PocketException;

	PostDTO createPost(PostDTO postDTO) throws PocketException;

	PostDTO updatePost(Long id, PostDTO postDTO) throws PocketException;
}
