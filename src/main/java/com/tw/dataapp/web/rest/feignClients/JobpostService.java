package com.tw.dataapp.web.rest.feignClients;

import com.tw.dataapp.client.AuthorizedFeignClient;
import com.tw.dataapp.web.rest.feignClients.models.Jobpost;
import com.tw.dataapp.web.rest.feignClients.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@AuthorizedFeignClient(name = "jobpostingapp")
@RequestMapping(value = "/api")
public interface JobpostService {

    @RequestMapping(value = "/jobposts/{id}", method = RequestMethod.GET)
    ResponseEntity<Jobpost> getJobpostWhereId(@RequestParam("id") Long id);
}
