package com.tw.dataapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.dataapp.web.rest.TestUtil;

public class UserSkillTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSkill.class);
        UserSkill userSkill1 = new UserSkill();
        userSkill1.setId(1L);
        UserSkill userSkill2 = new UserSkill();
        userSkill2.setId(userSkill1.getId());
        assertThat(userSkill1).isEqualTo(userSkill2);
        userSkill2.setId(2L);
        assertThat(userSkill1).isNotEqualTo(userSkill2);
        userSkill1.setId(null);
        assertThat(userSkill1).isNotEqualTo(userSkill2);
    }
}
