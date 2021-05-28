package com.tw.dataapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ApplicationInterviewMapperTest {

    private ApplicationInterviewMapper userInterviewMapper;

    @BeforeEach
    public void setUp() {
        userInterviewMapper = new ApplicationInterviewMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userInterviewMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userInterviewMapper.fromId(null)).isNull();
    }
}
