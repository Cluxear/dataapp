package com.tw.dataapp.service.mapper;


import com.tw.dataapp.domain.*;
import com.tw.dataapp.service.dto.ApplicationInterviewDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ApplicationInterview} and its DTO {@link ApplicationInterviewDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApplicationInterviewMapper extends EntityMapper<ApplicationInterviewDTO, ApplicationInterview> {



    default ApplicationInterview fromId(Long id) {
        if (id == null) {
            return null;
        }
        ApplicationInterview applicationInterview = new ApplicationInterview();
        applicationInterview.setId(id);
        return applicationInterview;
    }
}
