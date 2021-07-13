package com.tw.dataapp.service.impl;

import com.tw.dataapp.domain.ApplicationInterview;
import com.tw.dataapp.service.ApplicationInterviewService;
import com.tw.dataapp.repository.UserInterviewRepository;
import com.tw.dataapp.service.dto.ApplicationInterviewDTO;
import com.tw.dataapp.service.mapper.ApplicationInterviewMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ApplicationInterview}.
 */
@Service
@Transactional
public class ApplicationInterviewServiceImpl implements ApplicationInterviewService {

    private final Logger log = LoggerFactory.getLogger(ApplicationInterviewServiceImpl.class);

    private final UserInterviewRepository userInterviewRepository;

    private final ApplicationInterviewMapper userInterviewMapper;

    public ApplicationInterviewServiceImpl(UserInterviewRepository userInterviewRepository, ApplicationInterviewMapper userInterviewMapper) {
        this.userInterviewRepository = userInterviewRepository;
        this.userInterviewMapper = userInterviewMapper;
    }

    @Override
    public ApplicationInterviewDTO save(ApplicationInterviewDTO userInterviewDTO) {
        log.debug("Request to save UserInterview : {}", userInterviewDTO);
        ApplicationInterview applicationInterview = userInterviewMapper.toEntity(userInterviewDTO);
        applicationInterview = userInterviewRepository.save(applicationInterview);
        return userInterviewMapper.toDto(applicationInterview);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ApplicationInterviewDTO> findAll() {
        log.debug("Request to get all UserInterviews");
        return userInterviewRepository.findAll().stream()
            .map(userInterviewMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ApplicationInterviewDTO> findOne(Long id) {
        log.debug("Request to get UserInterview : {}", id);
        return userInterviewRepository.findById(id)
            .map(userInterviewMapper::toDto);
    }

    @Override
    public List<ApplicationInterviewDTO> findByApplicationId(Long appId) {

        return userInterviewRepository.findApplicationInterviewByApplicationId(appId).stream()
            .map(userInterviewMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public Optional<ApplicationInterviewDTO> findByInterviewId(Long interviewId) {
        return userInterviewRepository.findApplicationInterviewByInterviewId(interviewId)
            .map(userInterviewMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInterview : {}", id);
        userInterviewRepository.deleteById(id);
    }

    @Override
    public void deleteByInterviewId(Long id) {
        log.debug("Request to delete UserInterview : {}", id);
        userInterviewRepository.deleteAllByInterviewId(id);

    }
}
