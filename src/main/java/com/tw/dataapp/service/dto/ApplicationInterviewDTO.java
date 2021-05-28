package com.tw.dataapp.service.dto;

import com.tw.dataapp.domain.ApplicationInterview;
import com.tw.dataapp.domain.enumeration.InterviewRole;

import java.io.Serializable;

/**
 * A DTO for the {@link ApplicationInterview} entity.
 */
public class ApplicationInterviewDTO implements Serializable {

    private Long id;

    private Long applicationId;

    private Long interviewId;

    private InterviewRole interviewRole;


    public InterviewRole getInterviewRole() {
        return interviewRole;
    }

    public void setInterviewRole(InterviewRole interviewRole) {
        this.interviewRole = interviewRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setUserId(long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationInterviewDTO)) {
            return false;
        }

        return id != null && id.equals(((ApplicationInterviewDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInterviewDTO{" +
            "id=" + getId() +
            ", userId='" + getApplicationId() + "'" +
            ", interviewId=" + getInterviewId() +
            "}";
    }
}
