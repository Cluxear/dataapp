package com.tw.dataapp.service;

import com.tw.dataapp.domain.User;
import com.tw.dataapp.service.dto.UserApplicationDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.tw.dataapp.domain.UserApplication}.
 */
public interface UserApplicationService {

    /**
     * Save a userApplication.
     *
     * @param userApplicationDTO the entity to save.
     * @return the persisted entity.
     */
    UserApplicationDTO save(UserApplicationDTO userApplicationDTO);

    /**
     * Get all the userApplications.
     *
     * @return the list of entities.
     */
    List<UserApplicationDTO> findAll();


     List<UserApplicationDTO> findByUserId(String userId);

     List<UserApplicationDTO> findByJobPostId(Long jobPostId);

     UserApplicationDTO findByApplicationId(long applicationId);
    /**
     * Get the "id" userApplication.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UserApplicationDTO> findOne(Long id);

    /**
     * Delete the "id" userApplication.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Delete the  userApplication with application "id".
     *
     * @param id the application id of the entity.
     */
    void deleteByApplicationId(Long id);


}
