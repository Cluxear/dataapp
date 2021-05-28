package com.tw.dataapp.web.rest;

import com.tw.dataapp.domain.UserJobPost;
import com.tw.dataapp.repository.UserJobPostRepository;
import com.tw.dataapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.tw.dataapp.domain.UserJobPost}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UserJobPostResource {

    private final Logger log = LoggerFactory.getLogger(UserJobPostResource.class);

    private static final String ENTITY_NAME = "dataappUserJobPost";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UserJobPostRepository userJobPostRepository;

    public UserJobPostResource(UserJobPostRepository userJobPostRepository) {
        this.userJobPostRepository = userJobPostRepository;
    }

    /**
     * {@code POST  /user-job-posts} : Create a new userJobPost.
     *
     * @param userJobPost the userJobPost to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userJobPost, or with status {@code 400 (Bad Request)} if the userJobPost has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/user-job-posts")
    public ResponseEntity<UserJobPost> createUserJobPost(@RequestBody UserJobPost userJobPost) throws URISyntaxException {
        log.debug("REST request to save UserJobPost : {}", userJobPost);
        if (userJobPost.getId() != null) {
            throw new BadRequestAlertException("A new userJobPost cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserJobPost result = userJobPostRepository.save(userJobPost);
        return ResponseEntity.created(new URI("/api/user-job-posts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /user-job-posts} : Updates an existing userJobPost.
     *
     * @param userJobPost the userJobPost to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userJobPost,
     * or with status {@code 400 (Bad Request)} if the userJobPost is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userJobPost couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/user-job-posts")
    public ResponseEntity<UserJobPost> updateUserJobPost(@RequestBody UserJobPost userJobPost) throws URISyntaxException {
        log.debug("REST request to update UserJobPost : {}", userJobPost);
        if (userJobPost.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserJobPost result = userJobPostRepository.save(userJobPost);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, userJobPost.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /user-job-posts} : get all the userJobPosts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of userJobPosts in body.
     */
    @GetMapping("/user-job-posts")
    public List<UserJobPost> getAllUserJobPosts() {
        log.debug("REST request to get all UserJobPosts");
        return userJobPostRepository.findAll();
    }

    /**
     * {@code GET  /user-job-posts/:id} : get the "id" userJobPost.
     *
     * @param id the id of the userJobPost to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userJobPost, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/user-job-posts/{id}")
    public ResponseEntity<UserJobPost> getUserJobPost(@PathVariable Long id) {
        log.debug("REST request to get UserJobPost : {}", id);
        Optional<UserJobPost> userJobPost = userJobPostRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(userJobPost);
    }

    /**
     * {@code DELETE  /user-job-posts/:id} : delete the "id" userJobPost.
     *
     * @param id the id of the userJobPost to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/user-job-posts/{id}")
    public ResponseEntity<Void> deleteUserJobPost(@PathVariable Long id) {
        log.debug("REST request to delete UserJobPost : {}", id);
        userJobPostRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
