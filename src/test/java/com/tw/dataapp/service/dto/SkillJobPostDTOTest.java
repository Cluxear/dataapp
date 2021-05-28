package com.tw.dataapp.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.dataapp.web.rest.TestUtil;

public class SkillJobPostDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillJobPostDTO.class);
        SkillJobPostDTO skillJobPostDTO1 = new SkillJobPostDTO();
        skillJobPostDTO1.setId(1L);
        SkillJobPostDTO skillJobPostDTO2 = new SkillJobPostDTO();
        assertThat(skillJobPostDTO1).isNotEqualTo(skillJobPostDTO2);
        skillJobPostDTO2.setId(skillJobPostDTO1.getId());
        assertThat(skillJobPostDTO1).isEqualTo(skillJobPostDTO2);
        skillJobPostDTO2.setId(2L);
        assertThat(skillJobPostDTO1).isNotEqualTo(skillJobPostDTO2);
        skillJobPostDTO1.setId(null);
        assertThat(skillJobPostDTO1).isNotEqualTo(skillJobPostDTO2);
    }
}
