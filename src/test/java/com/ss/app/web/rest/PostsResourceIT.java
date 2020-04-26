package com.ss.app.web.rest;

import com.ss.app.PostsappApp;
import com.ss.app.domain.Posts;
import com.ss.app.repository.PostsRepository;
import com.ss.app.service.PostsService;
import com.ss.app.service.dto.PostsDTO;
import com.ss.app.service.mapper.PostsMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.ss.app.domain.enumeration.CategoryPosts;
/**
 * Integration tests for the {@link PostsResource} REST controller.
 */
@SpringBootTest(classes = PostsappApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PostsResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final CategoryPosts DEFAULT_CATEGORY = CategoryPosts.FRENCH;
    private static final CategoryPosts UPDATED_CATEGORY = CategoryPosts.ENGLISH;

    @Autowired
    private PostsRepository postsRepository;

    @Autowired
    private PostsMapper postsMapper;

    @Autowired
    private PostsService postsService;

    @Autowired
    private MockMvc restPostsMockMvc;

    private Posts posts;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Posts createEntity() {
        Posts posts = new Posts()
            .title(DEFAULT_TITLE)
            .image(DEFAULT_IMAGE)
            .content(DEFAULT_CONTENT)
            .category(DEFAULT_CATEGORY);
        return posts;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Posts createUpdatedEntity() {
        Posts posts = new Posts()
            .title(UPDATED_TITLE)
            .image(UPDATED_IMAGE)
            .content(UPDATED_CONTENT)
            .category(UPDATED_CATEGORY);
        return posts;
    }

    @BeforeEach
    public void initTest() {
        postsRepository.deleteAll();
        posts = createEntity();
    }

    @Test
    public void createPosts() throws Exception {
        int databaseSizeBeforeCreate = postsRepository.findAll().size();

        // Create the Posts
        PostsDTO postsDTO = postsMapper.toDto(posts);
        restPostsMockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isCreated());

        // Validate the Posts in the database
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeCreate + 1);
        Posts testPosts = postsList.get(postsList.size() - 1);
        assertThat(testPosts.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPosts.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testPosts.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPosts.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    public void createPostsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = postsRepository.findAll().size();

        // Create the Posts with an existing ID
        posts.setId("existing_id");
        PostsDTO postsDTO = postsMapper.toDto(posts);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPostsMockMvc.perform(post("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Posts in the database
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    public void getAllPosts() throws Exception {
        // Initialize the database
        postsRepository.save(posts);

        // Get all the postsList
        restPostsMockMvc.perform(get("/api/posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(posts.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }
    
    @Test
    public void getPosts() throws Exception {
        // Initialize the database
        postsRepository.save(posts);

        // Get the posts
        restPostsMockMvc.perform(get("/api/posts/{id}", posts.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(posts.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.image").value(DEFAULT_IMAGE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    public void getNonExistingPosts() throws Exception {
        // Get the posts
        restPostsMockMvc.perform(get("/api/posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updatePosts() throws Exception {
        // Initialize the database
        postsRepository.save(posts);

        int databaseSizeBeforeUpdate = postsRepository.findAll().size();

        // Update the posts
        Posts updatedPosts = postsRepository.findById(posts.getId()).get();
        updatedPosts
            .title(UPDATED_TITLE)
            .image(UPDATED_IMAGE)
            .content(UPDATED_CONTENT)
            .category(UPDATED_CATEGORY);
        PostsDTO postsDTO = postsMapper.toDto(updatedPosts);

        restPostsMockMvc.perform(put("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isOk());

        // Validate the Posts in the database
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeUpdate);
        Posts testPosts = postsList.get(postsList.size() - 1);
        assertThat(testPosts.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPosts.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testPosts.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPosts.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    public void updateNonExistingPosts() throws Exception {
        int databaseSizeBeforeUpdate = postsRepository.findAll().size();

        // Create the Posts
        PostsDTO postsDTO = postsMapper.toDto(posts);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPostsMockMvc.perform(put("/api/posts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(postsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Posts in the database
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    public void deletePosts() throws Exception {
        // Initialize the database
        postsRepository.save(posts);

        int databaseSizeBeforeDelete = postsRepository.findAll().size();

        // Delete the posts
        restPostsMockMvc.perform(delete("/api/posts/{id}", posts.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Posts> postsList = postsRepository.findAll();
        assertThat(postsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
