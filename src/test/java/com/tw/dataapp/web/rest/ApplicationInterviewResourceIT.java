package com.tw.dataapp.web.rest;

import com.tw.dataapp.DataappApp;
import com.tw.dataapp.config.TestSecurityConfiguration;
import com.tw.dataapp.domain.ApplicationInterview;
import com.tw.dataapp.repository.UserInterviewRepository;
import com.tw.dataapp.service.ApplicationInterviewService;
import com.tw.dataapp.service.dto.ApplicationInterviewDTO;
import com.tw.dataapp.service.mapper.ApplicationInterviewMapper;

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
 * Integration tests for the {@link com.tw.dataapp.service.dto.ApplicationInterviewDTO} REST controller.
 */
@SpringBootTest(classes = { DataappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class ApplicationInterviewResourceIT {

    private static final Long DEFAULT_USER_ID = 1L;
    private static final Long UPDATED_USER_ID = 2L;

    private static final Long DEFAULT_INTERVIEW_ID = 1L;
    private static final Long UPDATED_INTERVIEW_ID = 2L;

    @Autowired
    private UserInterviewRepository userInterviewRepository;

    @Autowired
    private ApplicationInterviewMapper userInterviewMapper;

    @Autowired
    private ApplicationInterviewService applicationInterviewService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserInterviewMockMvc;

    private ApplicationInterview applicationInterview;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationInterview createEntity(EntityManager em) {
        ApplicationInterview applicationInterview = new ApplicationInterview()
            .applicationId(DEFAULT_USER_ID)
            .interviewId(DEFAULT_INTERVIEW_ID);
        return applicationInterview;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ApplicationInterview createUpdatedEntity(EntityManager em) {
        ApplicationInterview applicationInterview = new ApplicationInterview()
            .applicationId(UPDATED_USER_ID)
            .interviewId(UPDATED_INTERVIEW_ID);
        return applicationInterview;
    }

    @BeforeEach
    public void initTest() {
        applicationInterview = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserInterview() throws Exception {
        int databaseSizeBeforeCreate = userInterviewRepository.findAll().size();
        // Create the UserInterview
        ApplicationInterviewDTO userInterviewDTO = userInterviewMapper.toDto(applicationInterview);
        restUserInterviewMockMvc.perform(post("/api/user-interviews").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInterviewDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInterview in the database
        List<ApplicationInterview> applicationInterviewList = userInterviewRepository.findAll();
        assertThat(applicationInterviewList).hasSize(databaseSizeBeforeCreate + 1);
        ApplicationInterview testApplicationInterview = applicationInterviewList.get(applicationInterviewList.size() - 1);
        assertThat(testApplicationInterview.getApplicationId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testApplicationInterview.getInterviewId()).isEqualTo(DEFAULT_INTERVIEW_ID);
    }

    @Test
    @Transactional
    public void createUserInterviewWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userInterviewRepository.findAll().size();

        // Create the UserInterview with an existing ID
        applicationInterview.setId(1L);
        ApplicationInterviewDTO userInterviewDTO = userInterviewMapper.toDto(applicationInterview);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInterviewMockMvc.perform(post("/api/user-interviews").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInterviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInterview in the database
        List<ApplicationInterview> applicationInterviewList = userInterviewRepository.findAll();
        assertThat(applicationInterviewList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserInterviews() throws Exception {
        // Initialize the database
        userInterviewRepository.saveAndFlush(applicationInterview);

        // Get all the userInterviewList
        restUserInterviewMockMvc.perform(get("/api/user-interviews?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(applicationInterview.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].interviewId").value(hasItem(DEFAULT_INTERVIEW_ID.intValue())));
    }

    @Test
    @Transactional
    public void getUserInterview() throws Exception {
        // Initialize the database
        userInterviewRepository.saveAndFlush(applicationInterview);

        // Get the userInterview
        restUserInterviewMockMvc.perform(get("/api/user-interviews/{id}", applicationInterview.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(applicationInterview.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.interviewId").value(DEFAULT_INTERVIEW_ID.intValue()));
    }
    @Test
    @Transactional
    public void getNonExistingUserInterview() throws Exception {
        // Get the userInterview
        restUserInterviewMockMvc.perform(get("/api/user-interviews/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserInterview() throws Exception {
        // Initialize the database
        userInterviewRepository.saveAndFlush(applicationInterview);

        int databaseSizeBeforeUpdate = userInterviewRepository.findAll().size();

        // Update the userInterview
        ApplicationInterview updatedApplicationInterview = userInterviewRepository.findById(applicationInterview.getId()).get();
        // Disconnect from session so that the updates on updatedUserInterview are not directly saved in db
        em.detach(updatedApplicationInterview);
        updatedApplicationInterview
            .applicationId(UPDATED_USER_ID)
            .interviewId(UPDATED_INTERVIEW_ID);
        ApplicationInterviewDTO userInterviewDTO = userInterviewMapper.toDto(updatedApplicationInterview);

        restUserInterviewMockMvc.perform(put("/api/user-interviews").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInterviewDTO)))
            .andExpect(status().isOk());

        // Validate the UserInterview in the database
        List<ApplicationInterview> applicationInterviewList = userInterviewRepository.findAll();
        assertThat(applicationInterviewList).hasSize(databaseSizeBeforeUpdate);
        ApplicationInterview testApplicationInterview = applicationInterviewList.get(applicationInterviewList.size() - 1);
        assertThat(testApplicationInterview.getApplicationId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testApplicationInterview.getInterviewId()).isEqualTo(UPDATED_INTERVIEW_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserInterview() throws Exception {
        int databaseSizeBeforeUpdate = userInterviewRepository.findAll().size();

        // Create the UserInterview
        ApplicationInterviewDTO userInterviewDTO = userInterviewMapper.toDto(applicationInterview);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInterviewMockMvc.perform(put("/api/user-interviews").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userInterviewDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInterview in the database
        List<ApplicationInterview> applicationInterviewList = userInterviewRepository.findAll();
        assertThat(applicationInterviewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserInterview() throws Exception {
        // Initialize the database
        userInterviewRepository.saveAndFlush(applicationInterview);

        int databaseSizeBeforeDelete = userInterviewRepository.findAll().size();

        // Delete the userInterview
        restUserInterviewMockMvc.perform(delete("/api/user-interviews/{id}", applicationInterview.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ApplicationInterview> applicationInterviewList = userInterviewRepository.findAll();
        assertThat(applicationInterviewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
