package com.tw.dataapp.repository;

import com.tw.dataapp.domain.SkillJobPost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the SkillJobPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SkillJobPostRepository extends JpaRepository<SkillJobPost, Long> {

    List<SkillJobPost> findByJobPostId(long jobPostId);
    void deleteSkillJobPostByJobPostId(long jobPostId);
}
