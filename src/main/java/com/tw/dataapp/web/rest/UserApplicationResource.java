package com.tw.dataapp.web.rest;

import com.tw.dataapp.service.UserApplicationService;
import com.tw.dataapp.service.dto.UserApplications;
import com.tw.dataapp.web.rest.errors.BadRequestAlertException;
import com.tw.dataapp.service.dto.UserApplicationDTO;

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
 * REST controller for managing {@link com.tw.dataapp.domain.UserApplication}.
 */
@RestController
@RequestMapping("/api")
public class UserApplicationResource {

    private final Logger log = LoggerFactory.getLogger(UserApplicationResource.class);

    private static final String ENTITY_NAME = "dataappUserApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserApplicationService userApplicationService;

    public UserApplicationResource(UserApplicationService userApplicationService) {
        this.userApplicationService = userApplicationService;
    }

    /**
     * {@code POST  /user-applications} : Create a new userApplication.
     *
     * @param userApplicationDTO the userApplicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userApplicationDTO, or with status {@code 400 (Bad Request)} if the userApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-applications")
    public ResponseEntity<UserApplicationDTO> createUserApplication(@RequestBody UserApplicationDTO userApplicationDTO) throws URISyntaxException {
        log.debug("REST request to save UserApplication : {}", userApplicationDTO);
        if (userApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new userApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserApplicationDTO result = userApplicationService.save(userApplicationDTO);
        return ResponseEntity.created(new URI("/api/user-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-applications} : Updates an existing userApplication.
     *
     * @param userApplicationDTO the userApplicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userApplicationDTO,
     * or with status {@code 400 (Bad Request)} if the userApplicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userApplicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-applications")
    public ResponseEntity<UserApplicationDTO> updateUserApplication(@RequestBody UserApplicationDTO userApplicationDTO) throws URISyntaxException {
        log.debug("REST request to update UserApplication : {}", userApplicationDTO);
        if (userApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserApplicationDTO result = userApplicationService.save(userApplicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userApplicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-applications} : get all the userApplications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userApplications in body.
     */
    @GetMapping("/user-applications")
    public List<UserApplicationDTO> getAllUserApplications() {
        log.debug("REST request to get all UserApplications");
        return userApplicationService.findAll();
    }

    /**
     * {@code GET  /user-applications} : get all the userApplications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userApplications in body.
     */
    @GetMapping("/user-applications/jobpost/{jpId}")
    public UserApplications getAllUserApplicationsForJobPost(@PathVariable Long jpId) {
        log.debug("REST request to get all UserApplications having userId {}", jpId);
        UserApplications userApplications = new UserApplications();
        userApplications.setUserApplications(userApplicationService.findByJobPostId(jpId));

        return userApplications;
    }
    @GetMapping("/user-applications/")
    public UserApplications getAll() {
        UserApplications userApplication = new UserApplications();
        userApplication.setUserApplications(userApplicationService.findAll());
        return userApplication ;
    }

    /**
     * {@code GET  /user-applications} : get all the userApplications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userApplications in body.
     */
    @GetMapping("/user-applications/userId/{userId}")
    public UserApplications getAllUserApplicationsForUser(@PathVariable String userId) {
        log.debug("REST request to get all UserApplications having userId {}", userId);
        UserApplications userApplications = new UserApplications();
        userApplications.setUserApplications(userApplicationService.findByUserId(userId));

        return userApplications;
    }

    /**
     * {@code GET  /user-applications/:id} : get the "id" userApplication.
     *
     * @param id the id of the userApplicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userApplicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-applications/{id}")
    public ResponseEntity<UserApplicationDTO> getUserApplication(@PathVariable Long id) {
        log.debug("REST request to get UserApplication : {}", id);
        Optional<UserApplicationDTO> userApplicationDTO = userApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userApplicationDTO);
    }

    @GetMapping("/user-applications/application/{id}")
    public ResponseEntity<UserApplicationDTO> getUserApplicationByAppId(@PathVariable Long id) {
        log.debug("REST request to get UserApplication : {}", id);
        UserApplicationDTO userApplicationDTO = userApplicationService.findByApplicationId(id);
        return ResponseEntity.ok(userApplicationDTO);
    }

    /**
     * {@code DELETE  /user-applications/:id} : delete the "id" userApplication.
     *
     * @param id the id of the userApplicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-applications/{id}")
    public ResponseEntity<Void> deleteUserApplication(@PathVariable Long id) {
        log.debug("REST request to delete UserApplication : {}", id);
        userApplicationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
