package com.tw.dataapp.service.mapper;


import com.tw.dataapp.domain.*;
import com.tw.dataapp.service.dto.UserSkillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserSkill} and its DTO {@link UserSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserSkillMapper extends EntityMapper<UserSkillDTO, UserSkill> {



    default UserSkill fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserSkill userSkill = new UserSkill();
        userSkill.setId(id);
        return userSkill;
    }
}
