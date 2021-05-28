package com.tw.dataapp.service.mapper;


import com.tw.dataapp.domain.*;
import com.tw.dataapp.service.dto.SkillJobPostDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link SkillJobPost} and its DTO {@link SkillJobPostDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SkillJobPostMapper extends EntityMapper<SkillJobPostDTO, SkillJobPost> {



    default SkillJobPost fromId(Long id) {
        if (id == null) {
            return null;
        }
        SkillJobPost skillJobPost = new SkillJobPost();
        skillJobPost.setId(id);
        return skillJobPost;
    }
}
