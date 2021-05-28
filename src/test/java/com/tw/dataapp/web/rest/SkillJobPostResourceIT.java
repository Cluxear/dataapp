package com.tw.dataapp.web.rest;

import com.tw.dataapp.DataappApp;
import com.tw.dataapp.config.TestSecurityConfiguration;
import com.tw.dataapp.domain.SkillJobPost;
import com.tw.dataapp.repository.SkillJobPostRepository;
import com.tw.dataapp.service.SkillJobPostService;
import com.tw.dataapp.service.dto.SkillJobPostDTO;
import com.tw.dataapp.service.mapper.SkillJobPostMapper;

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
 * Integration tests for the {@link SkillJobPostResource} REST controller.
 */
@SpringBootTest(classes = { DataappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class SkillJobPostResourceIT {

    private static final Long DEFAULT_SKILL_ID = 1L;
    private static final Long UPDATED_SKILL_ID = 2L;

    private static final Long DEFAULT_JOB_POST_ID = 1L;
    private static final Long UPDATED_JOB_POST_ID = 2L;

    @Autowired
    private SkillJobPostRepository skillJobPostRepository;

    @Autowired
    private SkillJobPostMapper skillJobPostMapper;

    @Autowired
    private SkillJobPostService skillJobPostService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSkillJobPostMockMvc;

    private SkillJobPost skillJobPost;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillJobPost createEntity(EntityManager em) {
        SkillJobPost skillJobPost = new SkillJobPost()
            .skillId(DEFAULT_SKILL_ID)
            .jobPostId(DEFAULT_JOB_POST_ID);
        return skillJobPost;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SkillJobPost createUpdatedEntity(EntityManager em) {
        SkillJobPost skillJobPost = new SkillJobPost()
            .skillId(UPDATED_SKILL_ID)
            .jobPostId(UPDATED_JOB_POST_ID);
        return skillJobPost;
    }

    @BeforeEach
    public void initTest() {
        skillJobPost = createEntity(em);
    }

    @Test
    @Transactional
    public void createSkillJobPost() throws Exception {
        int databaseSizeBeforeCreate = skillJobPostRepository.findAll().size();
        // Create the SkillJobPost
        SkillJobPostDTO skillJobPostDTO = skillJobPostMapper.toDto(skillJobPost);
        restSkillJobPostMockMvc.perform(post("/api/skill-job-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skillJobPostDTO)))
            .andExpect(status().isCreated());

        // Validate the SkillJobPost in the database
        List<SkillJobPost> skillJobPostList = skillJobPostRepository.findAll();
        assertThat(skillJobPostList).hasSize(databaseSizeBeforeCreate + 1);
        SkillJobPost testSkillJobPost = skillJobPostList.get(skillJobPostList.size() - 1);
        assertThat(testSkillJobPost.getSkillId()).isEqualTo(DEFAULT_SKILL_ID);
        assertThat(testSkillJobPost.getJobPostId()).isEqualTo(DEFAULT_JOB_POST_ID);
    }

    @Test
    @Transactional
    public void createSkillJobPostWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = skillJobPostRepository.findAll().size();

        // Create the SkillJobPost with an existing ID
        skillJobPost.setId(1L);
        SkillJobPostDTO skillJobPostDTO = skillJobPostMapper.toDto(skillJobPost);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSkillJobPostMockMvc.perform(post("/api/skill-job-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skillJobPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkillJobPost in the database
        List<SkillJobPost> skillJobPostList = skillJobPostRepository.findAll();
        assertThat(skillJobPostList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSkillJobPosts() throws Exception {
        // Initialize the database
        skillJobPostRepository.saveAndFlush(skillJobPost);

        // Get all the skillJobPostList
        restSkillJobPostMockMvc.perform(get("/api/skill-job-posts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(skillJobPost.getId().intValue())))
            .andExpect(jsonPath("$.[*].skillId").value(hasItem(DEFAULT_SKILL_ID.intValue())))
            .andExpect(jsonPath("$.[*].jobPostId").value(hasItem(DEFAULT_JOB_POST_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getSkillJobPost() throws Exception {
        // Initialize the database
        skillJobPostRepository.saveAndFlush(skillJobPost);

        // Get the skillJobPost
        restSkillJobPostMockMvc.perform(get("/api/skill-job-posts/{id}", skillJobPost.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(skillJobPost.getId().intValue()))
            .andExpect(jsonPath("$.skillId").value(DEFAULT_SKILL_ID.intValue()))
            .andExpect(jsonPath("$.jobPostId").value(DEFAULT_JOB_POST_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingSkillJobPost() throws Exception {
        // Get the skillJobPost
        restSkillJobPostMockMvc.perform(get("/api/skill-job-posts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSkillJobPost() throws Exception {
        // Initialize the database
        skillJobPostRepository.saveAndFlush(skillJobPost);

        int databaseSizeBeforeUpdate = skillJobPostRepository.findAll().size();

        // Update the skillJobPost
        SkillJobPost updatedSkillJobPost = skillJobPostRepository.findById(skillJobPost.getId()).get();
        // Disconnect from session so that the updates on updatedSkillJobPost are not directly saved in db
        em.detach(updatedSkillJobPost);
        updatedSkillJobPost
            .skillId(UPDATED_SKILL_ID)
            .jobPostId(UPDATED_JOB_POST_ID);
        SkillJobPostDTO skillJobPostDTO = skillJobPostMapper.toDto(updatedSkillJobPost);

        restSkillJobPostMockMvc.perform(put("/api/skill-job-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skillJobPostDTO)))
            .andExpect(status().isOk());

        // Validate the SkillJobPost in the database
        List<SkillJobPost> skillJobPostList = skillJobPostRepository.findAll();
        assertThat(skillJobPostList).hasSize(databaseSizeBeforeUpdate);
        SkillJobPost testSkillJobPost = skillJobPostList.get(skillJobPostList.size() - 1);
        assertThat(testSkillJobPost.getSkillId()).isEqualTo(UPDATED_SKILL_ID);
        assertThat(testSkillJobPost.getJobPostId()).isEqualTo(UPDATED_JOB_POST_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingSkillJobPost() throws Exception {
        int databaseSizeBeforeUpdate = skillJobPostRepository.findAll().size();

        // Create the SkillJobPost
        SkillJobPostDTO skillJobPostDTO = skillJobPostMapper.toDto(skillJobPost);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSkillJobPostMockMvc.perform(put("/api/skill-job-posts").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(skillJobPostDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SkillJobPost in the database
        List<SkillJobPost> skillJobPostList = skillJobPostRepository.findAll();
        assertThat(skillJobPostList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSkillJobPost() throws Exception {
        // Initialize the database
        skillJobPostRepository.saveAndFlush(skillJobPost);

        int databaseSizeBeforeDelete = skillJobPostRepository.findAll().size();

        // Delete the skillJobPost
        restSkillJobPostMockMvc.perform(delete("/api/skill-job-posts/{id}", skillJobPost.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SkillJobPost> skillJobPostList = skillJobPostRepository.findAll();
        assertThat(skillJobPostList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
