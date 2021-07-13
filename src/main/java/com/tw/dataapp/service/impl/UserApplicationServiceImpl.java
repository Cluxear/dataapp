package com.tw.dataapp.service.impl;

import com.tw.dataapp.service.UserApplicationService;
import com.tw.dataapp.domain.UserApplication;
import com.tw.dataapp.repository.UserApplicationRepository;
import com.tw.dataapp.service.dto.UserApplicationDTO;
import com.tw.dataapp.service.mapper.UserApplicationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link UserApplication}.
 */
@Service
@Transactional
public class UserApplicationServiceImpl implements UserApplicationService {

    private final Logger log = LoggerFactory.getLogger(UserApplicationServiceImpl.class);

    private final UserApplicationRepository userApplicationRepository;

    private final UserApplicationMapper userApplicationMapper;

    public UserApplicationServiceImpl(UserApplicationRepository userApplicationRepository, UserApplicationMapper userApplicationMapper) {
        this.userApplicationRepository = userApplicationRepository;
        this.userApplicationMapper = userApplicationMapper;
    }

    @Override
    public UserApplicationDTO save(UserApplicationDTO userApplicationDTO) {
        log.debug("Request to save UserApplication : {}", userApplicationDTO);
        UserApplication userApplication = userApplicationMapper.toEntity(userApplicationDTO);
        userApplication = userApplicationRepository.save(userApplication);
        return userApplicationMapper.toDto(userApplication);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserApplicationDTO> findAll() {
        log.debug("Request to get all UserApplications");
        return userApplicationRepository.findAll().stream()
            .map(userApplicationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public List<UserApplicationDTO> findByUserId(String userId) {
        return userApplicationRepository.findByUserId(userId).stream()
            .map(userApplicationMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public List<UserApplicationDTO> findByJobPostId(Long jobPostId) {

        return userApplicationRepository.findByJobPostId(jobPostId).stream()
            .map(userApplicationMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public UserApplicationDTO findByApplicationId(long applicationId) {

        return userApplicationMapper.toDto(userApplicationRepository.findByApplicationId(applicationId));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<UserApplicationDTO> findOne(Long id) {
        log.debug("Request to get UserApplication : {}", id);
        return userApplicationRepository.findById(id)
            .map(userApplicationMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserApplication : {}", id);
        userApplicationRepository.deleteById(id);
    }

    @Override
    public void deleteByApplicationId(Long id) {
        userApplicationRepository.deleteUserApplicationByApplicationId(id);
    }
}
