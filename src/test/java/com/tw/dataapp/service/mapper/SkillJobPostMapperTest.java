package com.tw.dataapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class SkillJobPostMapperTest {

    private SkillJobPostMapper skillJobPostMapper;

    @BeforeEach
    public void setUp() {
        skillJobPostMapper = new SkillJobPostMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(skillJobPostMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(skillJobPostMapper.fromId(null)).isNull();
    }
}
