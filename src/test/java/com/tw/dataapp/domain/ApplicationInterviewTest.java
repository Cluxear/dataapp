package com.tw.dataapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.dataapp.web.rest.TestUtil;

public class ApplicationInterviewTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ApplicationInterview.class);
        ApplicationInterview applicationInterview1 = new ApplicationInterview();
        applicationInterview1.setId(1L);
        ApplicationInterview applicationInterview2 = new ApplicationInterview();
        applicationInterview2.setId(applicationInterview1.getId());
        assertThat(applicationInterview1).isEqualTo(applicationInterview2);
        applicationInterview2.setId(2L);
        assertThat(applicationInterview1).isNotEqualTo(applicationInterview2);
        applicationInterview1.setId(null);
        assertThat(applicationInterview1).isNotEqualTo(applicationInterview2);
    }
}
