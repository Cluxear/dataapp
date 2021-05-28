package com.tw.dataapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.dataapp.web.rest.TestUtil;

public class UserApplicationDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserApplicationDTO.class);
        UserApplicationDTO userApplicationDTO1 = new UserApplicationDTO();
        userApplicationDTO1.setId(1L);
        UserApplicationDTO userApplicationDTO2 = new UserApplicationDTO();
        assertThat(userApplicationDTO1).isNotEqualTo(userApplicationDTO2);
        userApplicationDTO2.setId(userApplicationDTO1.getId());
        assertThat(userApplicationDTO1).isEqualTo(userApplicationDTO2);
        userApplicationDTO2.setId(2L);
        assertThat(userApplicationDTO1).isNotEqualTo(userApplicationDTO2);
        userApplicationDTO1.setId(null);
        assertThat(userApplicationDTO1).isNotEqualTo(userApplicationDTO2);
    }
}
