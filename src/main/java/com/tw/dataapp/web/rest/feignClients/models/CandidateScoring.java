package com.tw.dataapp.web.rest.feignClients.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CandidateScoring implements Serializable {

    @JsonProperty("offre")
    private Jobpost jobpost;

    @JsonProperty("candidat")
    private User user;


    public Jobpost getJobpost() {
        return jobpost;
    }

    public void setJobpost(Jobpost jobpost) {
        this.jobpost = jobpost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
