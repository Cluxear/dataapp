package com.tw.dataapp.service;

import com.tw.dataapp.service.dto.SkillJobPostDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.dataapp.domain.SkillJobPost}.
 */
public interface SkillJobPostService {

    /**
     * Save a skillJobPost.
     *
     * @param skillJobPostDTO the entity to save.
     * @return the persisted entity.
     */
    SkillJobPostDTO save(SkillJobPostDTO skillJobPostDTO);

    /**
     * Get all the skillJobPosts.
     *
     * @return the list of entities.
     */
    List<SkillJobPostDTO> findAll();


    /**
     * Get the "id" skillJobPost.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkillJobPostDTO> findOne(Long id);

    /**
     * Get the "id" skillJobPost.
     *
     * @param id the id of the jobPost.
     * @return the list of entity.
     */
    List<SkillJobPostDTO> findSkillJobPostWhereJobPostId(Long id);
    /**
     * Delete the "id" skillJobPost.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Delete the  skillJobPost by "jobPostId".
     *
     * @param jobPostId the id of the entity.
     */
    void deleteByJobPostId(Long jobPostId);

    List<SkillJobPostDTO> saveAll(List<SkillJobPostDTO> skillJobPostDTO);
}
