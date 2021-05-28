package com.tw.dataapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import com.tw.dataapp.web.rest.TestUtil;

public class SkillJobPostTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SkillJobPost.class);
        SkillJobPost skillJobPost1 = new SkillJobPost();
        skillJobPost1.setId(1L);
        SkillJobPost skillJobPost2 = new SkillJobPost();
        skillJobPost2.setId(skillJobPost1.getId());
        assertThat(skillJobPost1).isEqualTo(skillJobPost2);
        skillJobPost2.setId(2L);
        assertThat(skillJobPost1).isNotEqualTo(skillJobPost2);
        skillJobPost1.setId(null);
        assertThat(skillJobPost1).isNotEqualTo(skillJobPost2);
    }
}
