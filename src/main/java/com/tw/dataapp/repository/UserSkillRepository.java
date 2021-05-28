package com.tw.dataapp.repository;

import com.tw.dataapp.domain.UserSkill;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the UserSkill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
	
	List<UserSkill> findByUserId(String userId);
}
