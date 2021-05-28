package com.tw.dataapp.web.rest;

import com.tw.dataapp.service.UserSkillService;
import com.tw.dataapp.web.rest.errors.BadRequestAlertException;
import com.tw.dataapp.service.dto.UserSkillDTO;
import com.tw.dataapp.service.dto.UserSkills;

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
 * REST controller for managing {@link com.tw.dataapp.domain.UserSkill}.
 */
@RestController
@RequestMapping("/api")
public class UserSkillResource {

    private final Logger log = LoggerFactory.getLogger(UserSkillResource.class);

    private static final String ENTITY_NAME = "dataappUserSkill";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserSkillService userSkillService;

    public UserSkillResource(UserSkillService userSkillService) {
        this.userSkillService = userSkillService;
    }

    /**
     * {@code POST  /user-skills} : Create a new userSkill.
     *
     * @param userSkillDTO the userSkillDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userSkillDTO, or with status {@code 400 (Bad Request)} if the userSkill has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-skills")
    public ResponseEntity<UserSkillDTO> createUserSkill(@RequestBody UserSkillDTO userSkillDTO) throws URISyntaxException {
        log.debug("REST request to save UserSkill : {}", userSkillDTO);
        if (userSkillDTO.getId() != null) {
            throw new BadRequestAlertException("A new userSkill cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserSkillDTO result = userSkillService.save(userSkillDTO);
        return ResponseEntity.created(new URI("/api/user-skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-skills} : Updates an existing userSkill.
     *
     * @param userSkillDTO the userSkillDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userSkillDTO,
     * or with status {@code 400 (Bad Request)} if the userSkillDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userSkillDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-skills")
    public ResponseEntity<UserSkillDTO> updateUserSkill(@RequestBody UserSkillDTO userSkillDTO) throws URISyntaxException {
        log.debug("REST request to update UserSkill : {}", userSkillDTO);
        if (userSkillDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserSkillDTO result = userSkillService.save(userSkillDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userSkillDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-skills} : get all the userSkills.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userSkills in body.
     */
    @GetMapping("/user-skills/userId/{userId}")
    public UserSkills getAllUserSkills(@PathVariable String userId) {
        log.debug("REST request to get all UserSkills");
        UserSkills userSkills = new UserSkills();
        userSkills.setUserSkills(userSkillService.findAllByUserId(userId));
        return userSkills;
    }



    /**
     * {@code GET  /user-skills/:id} : get the "id" userSkill.
     *
     * @param id the id of the userSkillDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userSkillDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-skills/{id}")
    public ResponseEntity<UserSkillDTO> getUserSkill(@PathVariable Long id) {
        log.debug("REST request to get UserSkill : {}", id);
        Optional<UserSkillDTO> userSkillDTO = userSkillService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userSkillDTO);
    }

    /**
     * {@code DELETE  /user-skills/:id} : delete the "id" userSkill.
     *
     * @param id the id of the userSkillDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-skills/{id}")
    public ResponseEntity<Void> deleteUserSkill(@PathVariable Long id) {
        log.debug("REST request to delete UserSkill : {}", id);
        userSkillService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
