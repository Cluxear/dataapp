package com.tw.dataapp.web.rest;

import com.tw.dataapp.service.SkillJobPostService;
import com.tw.dataapp.web.rest.errors.BadRequestAlertException;
import com.tw.dataapp.service.dto.SkillJobPostDTO;

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
 * REST controller for managing {@link com.tw.dataapp.domain.SkillJobPost}.
 */
@RestController
@RequestMapping("/api")
public class SkillJobPostResource {

    private final Logger log = LoggerFactory.getLogger(SkillJobPostResource.class);

    private static final String ENTITY_NAME = "dataappSkillJobPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SkillJobPostService skillJobPostService;

    public SkillJobPostResource(SkillJobPostService skillJobPostService) {
        this.skillJobPostService = skillJobPostService;
    }

    /**
     * {@code POST  /skill-job-posts} : Create a new skillJobPost.
     *
     * @param skillJobPostDTO the skillJobPostDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillJobPostDTO, or with status {@code 400 (Bad Request)} if the skillJobPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/skill-job-posts")
    public ResponseEntity<SkillJobPostDTO> createSkillJobPost(@RequestBody SkillJobPostDTO skillJobPostDTO) throws URISyntaxException {
        log.debug("REST request to save SkillJobPost : {}", skillJobPostDTO);
        if (skillJobPostDTO.getId() != null) {
            throw new BadRequestAlertException("A new skillJobPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SkillJobPostDTO result = skillJobPostService.save(skillJobPostDTO);
        return ResponseEntity.created(new URI("/api/skill-job-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    @PostMapping("/skill-job-posts/")
    public ResponseEntity<List<SkillJobPostDTO>> createAllSkillJobPost(@RequestBody List<SkillJobPostDTO> skillJobPostDTO) throws URISyntaxException {
        log.debug("REST request to save SkillJobPost : {}", skillJobPostDTO);
        for(SkillJobPostDTO skillJobPost: skillJobPostDTO )
        if (skillJobPost.getId() != null) {
            throw new BadRequestAlertException("A new skillJobPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        List<SkillJobPostDTO> result = skillJobPostService.saveAll(skillJobPostDTO);
        return ResponseEntity.created(new URI("/api/skill-job-posts/" + result.size()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.toString()))
            .body(result);
    }

    /**
     * {@code PUT  /skill-job-posts} : Updates an existing skillJobPost.
     *
     * @param skillJobPostDTO the skillJobPostDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillJobPostDTO,
     * or with status {@code 400 (Bad Request)} if the skillJobPostDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the skillJobPostDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/skill-job-posts")
    public ResponseEntity<SkillJobPostDTO> updateSkillJobPost(@RequestBody SkillJobPostDTO skillJobPostDTO) throws URISyntaxException {
        log.debug("REST request to update SkillJobPost : {}", skillJobPostDTO);
        if (skillJobPostDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SkillJobPostDTO result = skillJobPostService.save(skillJobPostDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, skillJobPostDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /skill-job-posts} : get all the skillJobPosts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillJobPosts in body.
     */
    @GetMapping("/skill-job-posts")
    public List<SkillJobPostDTO> getAllSkillJobPosts() {
        log.debug("REST request to get all SkillJobPosts");
        return skillJobPostService.findAll();
    }
    /**
     * {@code GET  /skill-job-posts/jobpost} : get all the skillJobPosts.
     * @param jobpostId the id of the JobPost in the JobPostDTO.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillJobPosts in body.
     */
    @GetMapping("/skill-job-posts/jobpost/{jobpostId}")
    public List<SkillJobPostDTO> getAllSkillForJobPosts(@PathVariable Long jobpostId) {
        log.debug("REST request to get all SkillJobPosts with jobPostId {}", jobpostId);

        return skillJobPostService.findSkillJobPostWhereJobPostId(jobpostId);
    }
    @DeleteMapping("skill-job-posts/jobpost/{jobpostId}")
    public ResponseEntity<Void> deleteAllSkillsForJobPost(@PathVariable Long jobpostId) {

        log.debug("REST request to Delete all SkillJobPosts with jobPostId {}", jobpostId);
        skillJobPostService.deleteByJobPostId(jobpostId);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, jobpostId.toString())).build();
    }




    /**
     * {@code GET  /skill-job-posts/:id} : get the "id" skillJobPost.
     *
     * @param id the id of the skillJobPostDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillJobPostDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/skill-job-posts/{id}")
    public ResponseEntity<SkillJobPostDTO> getSkillJobPost(@PathVariable Long id) {
        log.debug("REST request to get SkillJobPost : {}", id);
        Optional<SkillJobPostDTO> skillJobPostDTO = skillJobPostService.findOne(id);
        return ResponseUtil.wrapOrNotFound(skillJobPostDTO);
    }

    /**
     * {@code DELETE  /skill-job-posts/:id} : delete the "id" skillJobPost.
     *
     * @param id the id of the skillJobPostDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/skill-job-posts/{id}")
        public ResponseEntity<Void> deleteSkillJobPost(@PathVariable Long id) {
        log.debug("REST request to delete SkillJobPost : {}", id);
        skillJobPostService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
