package com.mcecelja.pocket.services.post;

import com.mcecelja.pocket.common.dto.post.PostDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.common.mappers.post.PostMapper;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.post.Post;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.managers.post.PostManager;
import com.mcecelja.pocket.services.common.PermissionCheckerService;
import com.mcecelja.pocket.specification.criteria.PostSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostServiceImpl implements PostService {

	private final PermissionCheckerService permissionCheckerService;

	private final PostManager postManager;

	private final PostMapper postMapper;

	@Override
	public Page<PostDTO> getPosts(PostSearchCriteria criteria, Pageable pageable) {
		return postManager.getPosts(criteria, pageable).map(postMapper::postToPostDTO);
	}

	@Override
	public PostDTO getPost(Long id) throws PocketException {
		return postMapper.postToPostDTO(postManager.getPost(id));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PostDTO createPost(PostDTO postDTO) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		if(!permissionCheckerService.isUserAllowedToCreatePostForOrganization(currentUser, Long.valueOf(postDTO.getOrganization().getId()))) {
			log.warn("User {} is not allowed to create post for organization {}!", currentUser.getId(), postDTO.getOrganization().getId());
			throw new PocketException(PocketError.UNAUTHORIZED);
		}

		Post post = postManager.createPost(postDTO);

		return postMapper.postToPostDTO(post);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public PostDTO updatePost(Long id, PostDTO postDTO) throws PocketException {
		User currentUser = AuthorizedRequestContext.getCurrentUser();

		Post post = postManager.getPost(id);

		if(!permissionCheckerService.isUserAllowedToCreatePostForOrganization(currentUser, post.getOrganization().getId())) {
			log.warn("User {} is not allowed to update post for organization {}!", currentUser.getId(), post.getOrganization().getId());
			throw new PocketException(PocketError.UNAUTHORIZED);
		}

		postManager.updatePost(post, postDTO);

		return postMapper.postToPostDTO(post);
	}
}
