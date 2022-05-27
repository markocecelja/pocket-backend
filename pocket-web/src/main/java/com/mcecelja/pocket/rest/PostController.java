package com.mcecelja.pocket.rest;

import com.mcecelja.pocket.common.dto.post.PostDTO;
import com.mcecelja.pocket.common.exceptions.PocketException;
import com.mcecelja.pocket.services.post.PostService;
import com.mcecelja.pocket.specification.criteria.PostSearchCriteria;
import com.mcecelja.pocket.utils.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostController {

	private final PostService postService;

	@GetMapping("")
	public ResponseEntity<ResponseMessage<Page<PostDTO>>> getPosts(@RequestParam(required = false) Long organizationId,
	                                                               @RequestParam(required = false) Long categoryId,
	                                                               @PageableDefault(size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {

		PostSearchCriteria postSearchCriteria = PostSearchCriteria.builder()
				.organizationId(organizationId)
				.categoryId(categoryId)
				.build();

		return ResponseEntity.ok(new ResponseMessage<>(postService.getPosts(postSearchCriteria, pageable)));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ResponseMessage<PostDTO>> getPost(@PathVariable Long id) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(postService.getPost(id)));
	}

	@PostMapping("")
	public ResponseEntity<ResponseMessage<PostDTO>> createPost(@Valid @RequestBody PostDTO postDTO) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(postService.createPost(postDTO)));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseMessage<PostDTO>> updatePost(@PathVariable Long id, @Valid @RequestBody PostDTO postDTO) throws PocketException {
		return ResponseEntity.ok(new ResponseMessage<>(postService.updatePost(id, postDTO)));
	}
}
