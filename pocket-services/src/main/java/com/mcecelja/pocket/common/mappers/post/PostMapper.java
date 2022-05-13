package com.mcecelja.pocket.common.mappers.post;

import com.mcecelja.pocket.common.dto.post.PostDTO;
import com.mcecelja.pocket.common.mappers.codebook.CodeBookMapper;
import com.mcecelja.pocket.common.mappers.organization.OrganizationMapper;
import com.mcecelja.pocket.domain.post.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrganizationMapper.class, CodeBookMapper.class})
public abstract class PostMapper {

	@Mappings({
			@Mapping(target = "category", source = "entity.category"),
			@Mapping(target = "organization", source = "entity.organization")
	})
	public abstract PostDTO postToPostDTO(Post entity);
}
