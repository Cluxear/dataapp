package com.tw.dataapp.service.mapper;


import com.tw.dataapp.domain.*;
import com.tw.dataapp.service.dto.UserApplicationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link UserApplication} and its DTO {@link UserApplicationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserApplicationMapper extends EntityMapper<UserApplicationDTO, UserApplication> {



    default UserApplication fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserApplication userApplication = new UserApplication();
        userApplication.setId(id);
        return userApplication;
    }
}
