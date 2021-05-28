package com.tw.dataapp.service.impl;

import com.tw.dataapp.service.SkillJobPostService;
import com.tw.dataapp.domain.SkillJobPost;
import com.tw.dataapp.repository.SkillJobPostRepository;
import com.tw.dataapp.service.dto.SkillJobPostDTO;
import com.tw.dataapp.service.mapper.SkillJobPostMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link SkillJobPost}.
 */
@Service
@Transactional
public class SkillJobPostServiceImpl implements SkillJobPostService {

    private final Logger log = LoggerFactory.getLogger(SkillJobPostServiceImpl.class);

    private final SkillJobPostRepository skillJobPostRepository;

    private final SkillJobPostMapper skillJobPostMapper;

    public SkillJobPostServiceImpl(SkillJobPostRepository skillJobPostRepository, SkillJobPostMapper skillJobPostMapper) {
        this.skillJobPostRepository = skillJobPostRepository;
        this.skillJobPostMapper = skillJobPostMapper;
    }

    @Override
    public SkillJobPostDTO save(SkillJobPostDTO skillJobPostDTO) {
        log.debug("Request to save SkillJobPost : {}", skillJobPostDTO);
        SkillJobPost skillJobPost = skillJobPostMapper.toEntity(skillJobPostDTO);
        skillJobPost = skillJobPostRepository.save(skillJobPost);
        return skillJobPostMapper.toDto(skillJobPost);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillJobPostDTO> findAll() {
        log.debug("Request to get all SkillJobPosts");
        return skillJobPostRepository.findAll().stream()
            .map(skillJobPostMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<SkillJobPostDTO> findOne(Long id) {
        log.debug("Request to get SkillJobPost : {}", id);
        return skillJobPostRepository.findById(id)
            .map(skillJobPostMapper::toDto);
    }

    @Override
    public List<SkillJobPostDTO> findSkillJobPostWhereJobPostId(Long id) {

        return skillJobPostRepository.findByJobPostId(id).stream()
            .map(skillJobPostMapper::toDto)
            .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkillJobPost : {}", id);
        skillJobPostRepository.deleteById(id);
    }

    @Override
    public void deleteByJobPostId(Long jobPostId) {
        skillJobPostRepository.deleteSkillJobPostByJobPostId(jobPostId);
    }

    @Override
    public List<SkillJobPostDTO> saveAll(List<SkillJobPostDTO> skillJobPostDTO) {

           return skillJobPostRepository.saveAll(skillJobPostMapper.toEntity(skillJobPostDTO)).stream().map(skillJobPostMapper::toDto).collect(Collectors.toList());

    }
}
