package com.ss.app.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.ss.app.web.rest.TestUtil;

public class PostsTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Posts.class);
        Posts posts1 = new Posts();
        posts1.setId("id1");
        Posts posts2 = new Posts();
        posts2.setId(posts1.getId());
        assertThat(posts1).isEqualTo(posts2);
        posts2.setId("id2");
        assertThat(posts1).isNotEqualTo(posts2);
        posts1.setId(null);
        assertThat(posts1).isNotEqualTo(posts2);
    }
}
