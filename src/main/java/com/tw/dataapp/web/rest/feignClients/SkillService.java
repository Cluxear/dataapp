package com.tw.dataapp.web.rest.feignClients;

import com.tw.dataapp.web.rest.feignClients.models.Skill;
import com.tw.dataapp.web.rest.feignClients.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

public interface SkillService {

    @RequestMapping(value = "/skills/{id}", method = RequestMethod.GET)
    ResponseEntity<Skill> getSkillsWhereId(@RequestParam("id") Long id);
}
