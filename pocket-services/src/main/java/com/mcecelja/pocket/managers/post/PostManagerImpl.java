package com.mcecelja.pocket.managers.post;

import com.mcecelja.pocket.common.dto.post.PostDTO;
import com.mcecelja.pocket.common.exceptions.PocketError;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.context.AuthorizedRequestContext;
import com.mcecelja.pocket.domain.organization.Organization;
import com.mcecelja.pocket.domain.post.Post;
import com.mcecelja.pocket.domain.post.codebook.Category;
import com.mcecelja.pocket.domain.user.User;
import com.mcecelja.pocket.managers.codebook.CodeBookManager;
import com.mcecelja.pocket.managers.organization.OrganizationManager;
import com.mcecelja.pocket.repositories.offer.PostRepository;
import com.mcecelja.pocket.specification.PostSearchSpecification;
import com.mcecelja.pocket.specification.criteria.EditableCodeBookSearchCriteria;
import com.mcecelja.pocket.specification.criteria.PostSearchCriteria;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostManagerImpl implements PostManager {

	private final PostRepository postRepository;

	@Qualifier("categoryManagerImpl")
	private final CodeBookManager categoryManager;

	private final OrganizationManager organizationManager;

	@Override
	public Page<Post> getPosts(PostSearchCriteria criteria, Pageable pageable) {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		criteria.setCurrentUser(currentUser);

		return postRepository.findAll(PostSearchSpecification.findPosts(criteria), pageable);
	}

	@Override
	public Post getPost(Long id) throws PocketException {

		User currentUser = AuthorizedRequestContext.getCurrentUser();

		PostSearchCriteria postSearchCriteria = PostSearchCriteria.builder().id(id).currentUser(currentUser).build();

		Optional<Post> postOptional = postRepository.findOne(PostSearchSpecification.findPosts(postSearchCriteria));

		if(!postOptional.isPresent()) {
			log.warn("Post {} doesn't exist or user has no permission to get it!", id);
			throw new PocketException(PocketError.NON_EXISTING_POST);
		}

		return postOptional.get();
	}

	@Override
	public Post createPost(PostDTO postDTO) throws PocketException {

		Post post = new Post();
		post.setTitle(postDTO.getTitle().trim());
		post.setDescription(postDTO.getDescription());
		post.setActive(true);

		EditableCodeBookSearchCriteria categorySearchCriteria = EditableCodeBookSearchCriteria.builder()
				.id(Long.valueOf(postDTO.getCategory().getId()))
				.active(true)
				.build();

		Category category = (Category) categoryManager.getCodeBookEntity(categorySearchCriteria);
		post.setCategory(category);

		Organization organization = organizationManager.getOrganization(Long.valueOf(postDTO.getOrganization().getId()));
		post.setOrganization(organization);

		postRepository.save(post);

		return post;
	}

	@Override
	public void updatePost(Post post, PostDTO postDTO) throws PocketException {

		post.setTitle(postDTO.getTitle().trim());
		post.setDescription(postDTO.getDescription());
		post.setActive(postDTO.isActive());

		EditableCodeBookSearchCriteria categorySearchCriteria = EditableCodeBookSearchCriteria.builder()
				.id(Long.valueOf(postDTO.getCategory().getId()))
				.active(true)
				.build();

		Category category = (Category) categoryManager.getCodeBookEntity(categorySearchCriteria);
		post.setCategory(category);

		postRepository.save(post);
	}
}
