package com.tw.dataapp.web.rest;

import com.tw.dataapp.DataappApp;
import com.tw.dataapp.config.TestSecurityConfiguration;
import com.tw.dataapp.domain.UserJobPost;
import com.tw.dataapp.repository.UserJobPostRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UserJobPostResource} REST controller.
 */
@SpringBootTest(classes = { DataappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserJobPostResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;

    private static final Long DEFAULT_JOB_POST_ID = 1L;
    private static final Long UPDATED_JOB_POST_ID = 2L;

    @Autowired
    private UserJobPostRepository userJobPostRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserJobPostMockMvc;

    private UserJobPost userJobPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserJobPost createEntity(EntityManager em) {
        UserJobPost userJobPost = new UserJobPost()
            .userId(DEFAULT_USER_ID)
            .applicationId(DEFAULT_APPLICATION_ID)
            .jobPostId(DEFAULT_JOB_POST_ID);
        return userJobPost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserJobPost createUpdatedEntity(EntityManager em) {
        UserJobPost userJobPost = new UserJobPost()
            .userId(UPDATED_USER_ID)
            .applicationId(UPDATED_APPLICATION_ID)
            .jobPostId(UPDATED_JOB_POST_ID);
        return userJobPost;
    }

    @BeforeEach
    public void initTest() {
        userJobPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserJobPost() throws Exception {
        int databaseSizeBeforeCreate = userJobPostRepository.findAll().size();
        // Create the UserJobPost
        restUserJobPostMockMvc.perform(post("/api/user-job-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userJobPost)))
            .andExpect(status().isCreated());

        // Validate the UserJobPost in the database
        List<UserJobPost> userJobPostList = userJobPostRepository.findAll();
        assertThat(userJobPostList).hasSize(databaseSizeBeforeCreate + 1);
        UserJobPost testUserJobPost = userJobPostList.get(userJobPostList.size() - 1);
        assertThat(testUserJobPost.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserJobPost.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testUserJobPost.getJobPostId()).isEqualTo(DEFAULT_JOB_POST_ID);
    }

    @Test
    @Transactional
    public void createUserJobPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userJobPostRepository.findAll().size();

        // Create the UserJobPost with an existing ID
        userJobPost.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserJobPostMockMvc.perform(post("/api/user-job-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userJobPost)))
            .andExpect(status().isBadRequest());

        // Validate the UserJobPost in the database
        List<UserJobPost> userJobPostList = userJobPostRepository.findAll();
        assertThat(userJobPostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserJobPosts() throws Exception {
        // Initialize the database
        userJobPostRepository.saveAndFlush(userJobPost);

        // Get all the userJobPostList
        restUserJobPostMockMvc.perform(get("/api/user-job-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userJobPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].jobPostId").value(hasItem(DEFAULT_JOB_POST_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserJobPost() throws Exception {
        // Initialize the database
        userJobPostRepository.saveAndFlush(userJobPost);

        // Get the userJobPost
        restUserJobPostMockMvc.perform(get("/api/user-job-posts/{id}", userJobPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userJobPost.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.jobPostId").value(DEFAULT_JOB_POST_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserJobPost() throws Exception {
        // Get the userJobPost
        restUserJobPostMockMvc.perform(get("/api/user-job-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserJobPost() throws Exception {
        // Initialize the database
        userJobPostRepository.saveAndFlush(userJobPost);

        int databaseSizeBeforeUpdate = userJobPostRepository.findAll().size();

        // Update the userJobPost
        UserJobPost updatedUserJobPost = userJobPostRepository.findById(userJobPost.getId()).get();
        // Disconnect from session so that the updates on updatedUserJobPost are not directly saved in db
        em.detach(updatedUserJobPost);
        updatedUserJobPost
            .userId(UPDATED_USER_ID)
            .applicationId(UPDATED_APPLICATION_ID)
            .jobPostId(UPDATED_JOB_POST_ID);

        restUserJobPostMockMvc.perform(put("/api/user-job-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserJobPost)))
            .andExpect(status().isOk());

        // Validate the UserJobPost in the database
        List<UserJobPost> userJobPostList = userJobPostRepository.findAll();
        assertThat(userJobPostList).hasSize(databaseSizeBeforeUpdate);
        UserJobPost testUserJobPost = userJobPostList.get(userJobPostList.size() - 1);
        assertThat(testUserJobPost.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserJobPost.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testUserJobPost.getJobPostId()).isEqualTo(UPDATED_JOB_POST_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserJobPost() throws Exception {
        int databaseSizeBeforeUpdate = userJobPostRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserJobPostMockMvc.perform(put("/api/user-job-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userJobPost)))
            .andExpect(status().isBadRequest());

        // Validate the UserJobPost in the database
        List<UserJobPost> userJobPostList = userJobPostRepository.findAll();
        assertThat(userJobPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserJobPost() throws Exception {
        // Initialize the database
        userJobPostRepository.saveAndFlush(userJobPost);

        int databaseSizeBeforeDelete = userJobPostRepository.findAll().size();

        // Delete the userJobPost
        restUserJobPostMockMvc.perform(delete("/api/user-job-posts/{id}", userJobPost.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserJobPost> userJobPostList = userJobPostRepository.findAll();
        assertThat(userJobPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
