package com.tw.dataapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.dataapp.web.rest.TestUtil;

public class UserJobPostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserJobPost.class);
        UserJobPost userJobPost1 = new UserJobPost();
        userJobPost1.setId(1L);
        UserJobPost userJobPost2 = new UserJobPost();
        userJobPost2.setId(userJobPost1.getId());
        assertThat(userJobPost1).isEqualTo(userJobPost2);
        userJobPost2.setId(2L);
        assertThat(userJobPost1).isNotEqualTo(userJobPost2);
        userJobPost1.setId(null);
        assertThat(userJobPost1).isNotEqualTo(userJobPost2);
    }
}
