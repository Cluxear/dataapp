package com.tw.dataapp.service;

import com.tw.dataapp.service.dto.UserSkillDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.dataapp.domain.UserSkill}.
 */
public interface UserSkillService {

    /**
     * Save a userSkill.
     *
     * @param userSkillDTO the entity to save.
     * @return the persisted entity.
     */
    UserSkillDTO save(UserSkillDTO userSkillDTO);

    /**
     * Get all the userSkills.
     *
     * @return the list of entities.
     */
    List<UserSkillDTO> findAll();


      List<UserSkillDTO> findAllByUserId(String id);
    /**
     * Get the "id" userSkill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserSkillDTO> findOne(Long id);

    /**
     * Delete the "id" userSkill.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
