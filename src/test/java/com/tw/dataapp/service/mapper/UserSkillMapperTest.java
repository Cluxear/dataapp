package com.tw.dataapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserSkillMapperTest {

    private UserSkillMapper userSkillMapper;

    @BeforeEach
    public void setUp() {
        userSkillMapper = new UserSkillMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userSkillMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userSkillMapper.fromId(null)).isNull();
    }
}
