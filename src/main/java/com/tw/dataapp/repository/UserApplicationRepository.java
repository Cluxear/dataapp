package com.tw.dataapp.repository;

import com.tw.dataapp.domain.User;
import com.tw.dataapp.domain.UserApplication;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the UserApplication entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {

    List<UserApplication> findByUserId(String userId);
    List<UserApplication> findByJobPostId(Long jobPostId);
    UserApplication findByApplicationId(Long applicationId);
    void deleteUserApplicationByApplicationId( Long applicationId);
}
