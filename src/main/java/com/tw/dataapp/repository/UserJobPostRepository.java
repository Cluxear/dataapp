package com.tw.dataapp.repository;

import com.tw.dataapp.domain.UserJobPost;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserJobPost entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserJobPostRepository extends JpaRepository<UserJobPost, Long> {
}
