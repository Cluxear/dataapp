package com.tw.dataapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.dataapp.web.rest.TestUtil;

public class UserSkillDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserSkillDTO.class);
        UserSkillDTO userSkillDTO1 = new UserSkillDTO();
        userSkillDTO1.setId(1L);
        UserSkillDTO userSkillDTO2 = new UserSkillDTO();
        assertThat(userSkillDTO1).isNotEqualTo(userSkillDTO2);
        userSkillDTO2.setId(userSkillDTO1.getId());
        assertThat(userSkillDTO1).isEqualTo(userSkillDTO2);
        userSkillDTO2.setId(2L);
        assertThat(userSkillDTO1).isNotEqualTo(userSkillDTO2);
        userSkillDTO1.setId(null);
        assertThat(userSkillDTO1).isNotEqualTo(userSkillDTO2);
    }
}
