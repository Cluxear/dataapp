package com.tw.dataapp.service.impl;

import com.tw.dataapp.service.UserSkillService;
import com.tw.dataapp.domain.UserSkill;
import com.tw.dataapp.repository.UserSkillRepository;
import com.tw.dataapp.service.dto.UserSkillDTO;
import com.tw.dataapp.service.mapper.UserSkillMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserSkill}.
 */
@Service
@Transactional
public class UserSkillServiceImpl implements UserSkillService {

    private final Logger log = LoggerFactory.getLogger(UserSkillServiceImpl.class);

    private final UserSkillRepository userSkillRepository;

    private final UserSkillMapper userSkillMapper;

    public UserSkillServiceImpl(UserSkillRepository userSkillRepository, UserSkillMapper userSkillMapper) {
        this.userSkillRepository = userSkillRepository;
        this.userSkillMapper = userSkillMapper;
    }

    @Override
    public UserSkillDTO save(UserSkillDTO userSkillDTO) {
        log.debug("Request to save UserSkill : {}", userSkillDTO);
        UserSkill userSkill = userSkillMapper.toEntity(userSkillDTO);
        userSkill = userSkillRepository.save(userSkill);
        return userSkillMapper.toDto(userSkill);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserSkillDTO> findAll() {
        log.debug("Request to get all UserSkills");
        return userSkillRepository.findAll().stream()
            .map(userSkillMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserSkillDTO> findOne(Long id) {
        log.debug("Request to get UserSkill : {}", id);
        return userSkillRepository.findById(id)
            .map(userSkillMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserSkill : {}", id);
        userSkillRepository.deleteById(id);
    }

	@Override
	public List<UserSkillDTO> findAllByUserId(String id) {
        log.debug("finding all userSkills for user id  : {}", id);

		// TODO Auto-generated method stub
		return userSkillRepository.findByUserId(id).stream()
				.map(userSkillMapper::toDto)
				.collect(Collectors.toList());
	}
}
