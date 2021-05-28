package com.tw.dataapp.repository;

import com.tw.dataapp.domain.ApplicationInterview;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserInterview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInterviewRepository extends JpaRepository<ApplicationInterview, Long> {
}
