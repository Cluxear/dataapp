package com.tw.dataapp.web.rest;

import com.tw.dataapp.DataappApp;
import com.tw.dataapp.config.TestSecurityConfiguration;
import com.tw.dataapp.domain.UserSkill;
import com.tw.dataapp.repository.UserSkillRepository;
import com.tw.dataapp.service.UserSkillService;
import com.tw.dataapp.service.dto.UserSkillDTO;
import com.tw.dataapp.service.mapper.UserSkillMapper;

import org.hibernate.hql.internal.ast.util.ASTUtil;
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

import com.tw.dataapp.domain.enumeration.SkillLevel;
/**
 * Integration tests for the {@link UserSkillResource} REST controller.
 */
@SpringBootTest(classes = { DataappApp.class, TestSecurityConfiguration.class })
@AutoConfigureMockMvc
@WithMockUser
public class UserSkillResourceIT {

    private static final String DEFAULT_USER_ID = "AAAAAAAAAA";
    private static final String UPDATED_USER_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_SKILL_ID = 1L;
    private static final Long UPDATED_SKILL_ID = 2L;

    private static final SkillLevel DEFAULT_SKILL_LEVEL = SkillLevel.NOVICE;
    private static final SkillLevel UPDATED_SKILL_LEVEL = SkillLevel.INTERMEDIATE;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private UserSkillMapper userSkillMapper;

    @Autowired
    private UserSkillService userSkillService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUserSkillMockMvc;

    private UserSkill userSkill;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSkill createEntity(EntityManager em) {
        UserSkill userSkill = new UserSkill()
            .userId(DEFAULT_USER_ID)
            .skillId(DEFAULT_SKILL_ID);
        return userSkill;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserSkill createUpdatedEntity(EntityManager em) {
        UserSkill userSkill = new UserSkill()
            .userId(UPDATED_USER_ID)
            .skillId(UPDATED_SKILL_ID);
        return userSkill;
    }

    @BeforeEach
    public void initTest() {
        userSkill = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserSkill() throws Exception {
      /*  int databaseSizeBeforeCreate = userSkillRepository.findAll().size();
        // Create the UserSkill
        UserSkillDTO userSkillDTO = userSkillMapper.toDto(userSkill);
        restUserSkillMockMvc.perform(post("/api/user-skills").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(ASTUtil.convertObjectToJsonBytes(userSkillDTO)))
            .andExpect(status().isCreated());

        // Validate the UserSkill in the database
        List<UserSkill> userSkillList = userSkillRepository.findAll();
        assertThat(userSkillList).hasSize(databaseSizeBeforeCreate + 1);
        UserSkill testUserSkill = userSkillList.get(userSkillList.size() - 1);
        assertThat(testUserSkill.getUserId()).isEqualTo(DEFAULT_USER_ID);
        assertThat(testUserSkill.getSkillId()).isEqualTo(DEFAULT_SKILL_ID); */
    }

    @Test
    @Transactional
    public void createUserSkillWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userSkillRepository.findAll().size();

        // Create the UserSkill with an existing ID
        userSkill.setId(1L);
        UserSkillDTO userSkillDTO = userSkillMapper.toDto(userSkill);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserSkillMockMvc.perform(post("/api/user-skills").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSkill in the database
        List<UserSkill> userSkillList = userSkillRepository.findAll();
        assertThat(userSkillList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUserSkills() throws Exception {
        // Initialize the database
        userSkillRepository.saveAndFlush(userSkill);

        // Get all the userSkillList
        restUserSkillMockMvc.perform(get("/api/user-skills?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userSkill.getId().intValue())))
            .andExpect(jsonPath("$.[*].userId").value(hasItem(DEFAULT_USER_ID)))
            .andExpect(jsonPath("$.[*].skillId").value(hasItem(DEFAULT_SKILL_ID.intValue())))
            .andExpect(jsonPath("$.[*].skillLevel").value(hasItem(DEFAULT_SKILL_LEVEL.toString())));
    }
    
    @Test
    @Transactional
    public void getUserSkill() throws Exception {
        // Initialize the database
        userSkillRepository.saveAndFlush(userSkill);

        // Get the userSkill
        restUserSkillMockMvc.perform(get("/api/user-skills/{id}", userSkill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(userSkill.getId().intValue()))
            .andExpect(jsonPath("$.userId").value(DEFAULT_USER_ID))
            .andExpect(jsonPath("$.skillId").value(DEFAULT_SKILL_ID.intValue()))
            .andExpect(jsonPath("$.skillLevel").value(DEFAULT_SKILL_LEVEL.toString()));
    }
    @Test
    @Transactional
    public void getNonExistingUserSkill() throws Exception {
        // Get the userSkill
        restUserSkillMockMvc.perform(get("/api/user-skills/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserSkill() throws Exception {
        // Initialize the database
        userSkillRepository.saveAndFlush(userSkill);

        int databaseSizeBeforeUpdate = userSkillRepository.findAll().size();

        // Update the userSkill
        UserSkill updatedUserSkill = userSkillRepository.findById(userSkill.getId()).get();
        // Disconnect from session so that the updates on updatedUserSkill are not directly saved in db
        em.detach(updatedUserSkill);
        updatedUserSkill
            .userId(UPDATED_USER_ID)
            .skillId(UPDATED_SKILL_ID);
        UserSkillDTO userSkillDTO = userSkillMapper.toDto(updatedUserSkill);

        restUserSkillMockMvc.perform(put("/api/user-skills").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSkillDTO)))
            .andExpect(status().isOk());

        // Validate the UserSkill in the database
        List<UserSkill> userSkillList = userSkillRepository.findAll();
        assertThat(userSkillList).hasSize(databaseSizeBeforeUpdate);
        UserSkill testUserSkill = userSkillList.get(userSkillList.size() - 1);
        assertThat(testUserSkill.getUserId()).isEqualTo(UPDATED_USER_ID);
        assertThat(testUserSkill.getSkillId()).isEqualTo(UPDATED_SKILL_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingUserSkill() throws Exception {
        int databaseSizeBeforeUpdate = userSkillRepository.findAll().size();

        // Create the UserSkill
        UserSkillDTO userSkillDTO = userSkillMapper.toDto(userSkill);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserSkillMockMvc.perform(put("/api/user-skills").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(userSkillDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserSkill in the database
        List<UserSkill> userSkillList = userSkillRepository.findAll();
        assertThat(userSkillList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserSkill() throws Exception {
        // Initialize the database
        userSkillRepository.saveAndFlush(userSkill);

        int databaseSizeBeforeDelete = userSkillRepository.findAll().size();

        // Delete the userSkill
        restUserSkillMockMvc.perform(delete("/api/user-skills/{id}", userSkill.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserSkill> userSkillList = userSkillRepository.findAll();
        assertThat(userSkillList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
