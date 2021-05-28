package com.tw.dataapp.web.rest;

import com.tw.dataapp.DataappApp;
import com.tw.dataapp.config.TestSecurityConfiguration;
import com.tw.dataapp.domain.UserApplication;
import com.tw.dataapp.repository.UserApplicationRepository;
import com.tw.dataapp.service.UserApplicationService;
import com.tw.dataapp.service.dto.UserApplicationDTO;
import com.tw.dataapp.service.mapper.UserApplicationMapper;

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
 * Integration tests for the {@link UserApplicationResource} REST controller.
 */
@SpringBootTest(classes = { DataappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserApplicationResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_APPLICATION_ID = 1L;
    private static final Long UPDATED_APPLICATION_ID = 2L;

    private static final Long DEFAULT_JOB_POST_ID = 1L;
    private static final Long UPDATED_JOB_POST_ID = 2L;

    @Autowired
    private UserApplicationRepository userApplicationRepository;

    @Autowired
    private UserApplicationMapper userApplicationMapper;

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserApplicationMockMvc;

    private UserApplication userApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApplication createEntity(EntityManager em) {
        UserApplication userApplication = new UserApplication()
            .userId(DEFAULT_USER_ID)
            .applicationId(DEFAULT_APPLICATION_ID)
            .jobPostId(DEFAULT_JOB_POST_ID);
        return userApplication;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserApplication createUpdatedEntity(EntityManager em) {
        UserApplication userApplication = new UserApplication()
            .userId(UPDATED_USER_ID)
            .applicationId(UPDATED_APPLICATION_ID)
            .jobPostId(UPDATED_JOB_POST_ID);
        return userApplication;
    }

    @BeforeEach
    public void initTest() {
        userApplication = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserApplication() throws Exception {
        int databaseSizeBeforeCreate = userApplicationRepository.findAll().size();
        // Create the UserApplication
        UserApplicationDTO userApplicationDTO = userApplicationMapper.toDto(userApplication);
        restUserApplicationMockMvc.perform(post("/api/user-applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApplicationDTO)))
            .andExpect(status().isCreated());

        // Validate the UserApplication in the database
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        UserApplication testUserApplication = userApplicationList.get(userApplicationList.size() - 1);
        assertThat(testUserApplication.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserApplication.getApplicationId()).isEqualTo(DEFAULT_APPLICATION_ID);
        assertThat(testUserApplication.getJobPostId()).isEqualTo(DEFAULT_JOB_POST_ID);
    }

    @Test
    @Transactional
    public void createUserApplicationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userApplicationRepository.findAll().size();

        // Create the UserApplication with an existing ID
        userApplication.setId(1L);
        UserApplicationDTO userApplicationDTO = userApplicationMapper.toDto(userApplication);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserApplicationMockMvc.perform(post("/api/user-applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApplication in the database
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserApplications() throws Exception {
        // Initialize the database
        userApplicationRepository.saveAndFlush(userApplication);

        // Get all the userApplicationList
        restUserApplicationMockMvc.perform(get("/api/user-applications?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].applicationId").value(hasItem(DEFAULT_APPLICATION_ID.intValue())))
            .andExpect(jsonPath("$.[*].jobPostId").value(hasItem(DEFAULT_JOB_POST_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getUserApplication() throws Exception {
        // Initialize the database
        userApplicationRepository.saveAndFlush(userApplication);

        // Get the userApplication
        restUserApplicationMockMvc.perform(get("/api/user-applications/{id}", userApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userApplication.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.applicationId").value(DEFAULT_APPLICATION_ID.intValue()))
            .andExpect(jsonPath("$.jobPostId").value(DEFAULT_JOB_POST_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserApplication() throws Exception {
        // Get the userApplication
        restUserApplicationMockMvc.perform(get("/api/user-applications/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserApplication() throws Exception {
        // Initialize the database
        userApplicationRepository.saveAndFlush(userApplication);

        int databaseSizeBeforeUpdate = userApplicationRepository.findAll().size();

        // Update the userApplication
        UserApplication updatedUserApplication = userApplicationRepository.findById(userApplication.getId()).get();
        // Disconnect from session so that the updates on updatedUserApplication are not directly saved in db
        em.detach(updatedUserApplication);
        updatedUserApplication
            .userId(UPDATED_USER_ID)
            .applicationId(UPDATED_APPLICATION_ID)
            .jobPostId(UPDATED_JOB_POST_ID);
        UserApplicationDTO userApplicationDTO = userApplicationMapper.toDto(updatedUserApplication);

        restUserApplicationMockMvc.perform(put("/api/user-applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApplicationDTO)))
            .andExpect(status().isOk());

        // Validate the UserApplication in the database
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeUpdate);
        UserApplication testUserApplication = userApplicationList.get(userApplicationList.size() - 1);
        assertThat(testUserApplication.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserApplication.getApplicationId()).isEqualTo(UPDATED_APPLICATION_ID);
        assertThat(testUserApplication.getJobPostId()).isEqualTo(UPDATED_JOB_POST_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserApplication() throws Exception {
        int databaseSizeBeforeUpdate = userApplicationRepository.findAll().size();

        // Create the UserApplication
        UserApplicationDTO userApplicationDTO = userApplicationMapper.toDto(userApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserApplicationMockMvc.perform(put("/api/user-applications").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userApplicationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserApplication in the database
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserApplication() throws Exception {
        // Initialize the database
        userApplicationRepository.saveAndFlush(userApplication);

        int databaseSizeBeforeDelete = userApplicationRepository.findAll().size();

        // Delete the userApplication
        restUserApplicationMockMvc.perform(delete("/api/user-applications/{id}", userApplication.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserApplication> userApplicationList = userApplicationRepository.findAll();
        assertThat(userApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
