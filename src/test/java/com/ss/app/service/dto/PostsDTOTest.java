package com.ss.app.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ss.app.web.rest.TestUtil;

public class PostsDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PostsDTO.class);
        PostsDTO postsDTO1 = new PostsDTO();
        postsDTO1.setId("id1");
        PostsDTO postsDTO2 = new PostsDTO();
        assertThat(postsDTO1).isNotEqualTo(postsDTO2);
        postsDTO2.setId(postsDTO1.getId());
        assertThat(postsDTO1).isEqualTo(postsDTO2);
        postsDTO2.setId("id2");
        assertThat(postsDTO1).isNotEqualTo(postsDTO2);
        postsDTO1.setId(null);
        assertThat(postsDTO1).isNotEqualTo(postsDTO2);
    }
}
