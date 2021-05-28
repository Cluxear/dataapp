package com.tw.dataapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.dataapp.web.rest.TestUtil;

public class ApplicationInterviewDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationInterviewDTO.class);
        ApplicationInterviewDTO userInterviewDTO1 = new ApplicationInterviewDTO();
        userInterviewDTO1.setId(1L);
        ApplicationInterviewDTO userInterviewDTO2 = new ApplicationInterviewDTO();
        assertThat(userInterviewDTO1).isNotEqualTo(userInterviewDTO2);
        userInterviewDTO2.setId(userInterviewDTO1.getId());
        assertThat(userInterviewDTO1).isEqualTo(userInterviewDTO2);
        userInterviewDTO2.setId(2L);
        assertThat(userInterviewDTO1).isNotEqualTo(userInterviewDTO2);
        userInterviewDTO1.setId(null);
        assertThat(userInterviewDTO1).isNotEqualTo(userInterviewDTO2);
    }
}
