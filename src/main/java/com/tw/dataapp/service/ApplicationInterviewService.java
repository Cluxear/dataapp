package com.tw.dataapp.service;

import com.tw.dataapp.domain.ApplicationInterview;
import com.tw.dataapp.service.dto.ApplicationInterviewDTO;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link ApplicationInterview}.
 */
public interface ApplicationInterviewService {

    /**
     * Save a userInterview.
     *
     * @param userInterviewDTO the entity to save.
     * @return the persisted entity.
     */
    ApplicationInterviewDTO save(ApplicationInterviewDTO userInterviewDTO);

    /**
     * Get all the userInterviews.
     *
     * @return the list of entities.
     */
    List<ApplicationInterviewDTO> findAll();


    /**
     * Get the "id" userInterview.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ApplicationInterviewDTO> findOne(Long id);

    /**
     * Delete the "id" userInterview.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
