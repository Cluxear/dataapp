package com.tw.dataapp.repository;

import com.tw.dataapp.domain.ApplicationInterview;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the UserInterview entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserInterviewRepository extends JpaRepository<ApplicationInterview, Long> {

    List<ApplicationInterview> findApplicationInterviewByApplicationId(Long applicationId);
    Optional<ApplicationInterview> findApplicationInterviewByInterviewId(Long interviewId);
    void deleteAllByInterviewId(long interviewId);

}
