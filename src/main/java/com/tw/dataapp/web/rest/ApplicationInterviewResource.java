package com.tw.dataapp.web.rest;

import com.tw.dataapp.domain.ApplicationInterview;
import com.tw.dataapp.service.ApplicationInterviewService;
import com.tw.dataapp.service.dto.ApplicationInterviewDTO;
import com.tw.dataapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link ApplicationInterview}.
 */
@RestController
@RequestMapping("/api")
public class ApplicationInterviewResource {

    private final Logger log = LoggerFactory.getLogger(ApplicationInterviewResource.class);

    private static final String ENTITY_NAME = "dataappUserInterview";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ApplicationInterviewService applicationInterviewService;

    public ApplicationInterviewResource(ApplicationInterviewService applicationInterviewService) {
        this.applicationInterviewService = applicationInterviewService;
    }
    /**

     * {@code POST  /user-interviews} : Create a new userInterview.
     *
     * @param userInterviewDTO the userInterviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userInterviewDTO, or with status {@code 400 (Bad Request)} if the userInterview has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-interviews")
    public ResponseEntity<ApplicationInterviewDTO> createUserInterview(@RequestBody ApplicationInterviewDTO userInterviewDTO) throws URISyntaxException {
        log.debug("REST request to save UserInterview : {}", userInterviewDTO);
        if (userInterviewDTO.getId() != null) {
            throw new BadRequestAlertException("A new userInterview cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ApplicationInterviewDTO result = applicationInterviewService.save(userInterviewDTO);
        return ResponseEntity.created(new URI("/api/user-interviews/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-interviews} : Updates an existing userInterview.
     *
     * @param userInterviewDTO the userInterviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userInterviewDTO,
     * or with status {@code 400 (Bad Request)} if the userInterviewDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userInterviewDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-interviews")
    public ResponseEntity<ApplicationInterviewDTO> updateUserInterview(@RequestBody ApplicationInterviewDTO userInterviewDTO) throws URISyntaxException {
        log.debug("REST request to update UserInterview : {}", userInterviewDTO);
        if (userInterviewDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ApplicationInterviewDTO result = applicationInterviewService.save(userInterviewDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userInterviewDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-interviews} : get all the userInterviews.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userInterviews in body.
     */
    @GetMapping("/user-interviews")
    public List<ApplicationInterviewDTO> getAllUserInterviews() {
        log.debug("REST request to get all UserInterviews");
        return applicationInterviewService.findAll();
    }

    /**
     * {@code GET  /user-interviews/:id} : get the "id" userInterview.
     *
     * @param id the id of the userInterviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userInterviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-interviews/{id}")
    public ResponseEntity<ApplicationInterviewDTO> getUserInterview(@PathVariable Long id) {
        log.debug("REST request to get UserInterview : {}", id);
        Optional<ApplicationInterviewDTO> userInterviewDTO = applicationInterviewService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userInterviewDTO);
    }
    /**
     * {@code GET  /application-interviews/application/:id} : get the "id" userInterview.
     *
     * @param id the id of the application of the userInterviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userInterviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-interviews/application/{id}")
    public List<ApplicationInterviewDTO> getApplicationInterviews(@PathVariable Long id) {
        log.debug("REST request to get UserInterview : {}", id);

        return applicationInterviewService.findByApplicationId(id);
    }
    /**
     * {@code GET  /application-interviews/interview/:id} : get the "interview_id" userInterview.
     *
     * @param id the id of the application of the userInterviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userInterviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/application-interviews/interview/{id}")
    public ResponseEntity<ApplicationInterviewDTO> getApplicationInterviewByInterview(@PathVariable Long id) {
        log.debug("REST request to get UserInterview : {}", id);

        return ResponseUtil.wrapOrNotFound(applicationInterviewService.findByInterviewId(id));
    }

    /**
     * {@code DELETE  /user-interviews/:id} : delete the "id" userInterview.
     *
     * @param id the id of the userInterviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-interviews/{id}")
    public ResponseEntity<Void> deleteUserInterview(@PathVariable Long id) {
        log.debug("REST request to delete UserInterview : {}", id);
        applicationInterviewService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code DELETE  /user-interviews/interview/:id} : delete the "id" interviewId userInterview.
     *
     * @param id the id of the userInterviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-interviews/interview/{id}")
    public ResponseEntity<Void> deleteUserInterviewWhereInterviewId(@PathVariable Long id) {
        log.debug("REST request to delete UserInterview : {}", id);
        applicationInterviewService.deleteByInterviewId(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
