package com.tw.dataapp.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class UserApplicationMapperTest {

    private UserApplicationMapper userApplicationMapper;

    @BeforeEach
    public void setUp() {
        userApplicationMapper = new UserApplicationMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(userApplicationMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(userApplicationMapper.fromId(null)).isNull();
    }
}
