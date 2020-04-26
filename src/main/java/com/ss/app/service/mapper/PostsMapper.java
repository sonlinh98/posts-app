package com.ss.app.service.mapper;


import com.ss.app.domain.*;
import com.ss.app.service.dto.PostsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Posts} and its DTO {@link PostsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PostsMapper extends EntityMapper<PostsDTO, Posts> {



    default Posts fromId(String id) {
        if (id == null) {
            return null;
        }
        Posts posts = new Posts();
        posts.setId(id);
        return posts;
    }
}
