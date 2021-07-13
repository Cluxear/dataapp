package com.tw.dataapp.web.rest.feignClients;

import com.tw.dataapp.client.AuthorizedFeignClient;
import com.tw.dataapp.web.rest.feignClients.models.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@AuthorizedFeignClient(name = "userapp")
@RequestMapping(value = "/api")
public interface UserappService {

    @RequestMapping (value = "/users/{login}", method = RequestMethod.GET)
    ResponseEntity<User> getUserFromLogin(@RequestParam("login") String login);

    @RequestMapping (value = "/users/id/{id}", method = RequestMethod.GET)
    ResponseEntity<User> getUserFromId(@RequestParam("id") String id);

    @RequestMapping (value = "/candidates/{id}", method = RequestMethod.GET)
    ResponseEntity<User> getCandidateFromId(@RequestParam("id") String id);
}
