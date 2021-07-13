package com.tw.dataapp.domain;

import com.tw.dataapp.domain.enumeration.InterviewRole;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserInterview.
 */
@Entity
@Table(name = "application_interview")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ApplicationInterview implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_id")
    private long applicationId;

    @Column(name = "interview_id")
    private Long interviewId;

    @Column(name = "recruter_id")
    private String recruterId;


    public String getRecruterId() {
        return recruterId;
    }

    public void setRecruterId(String recruterId) {
        this.recruterId = recruterId;
    }

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public ApplicationInterview applicationId(long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getInterviewId() {
        return interviewId;
    }

    public ApplicationInterview interviewId(Long interviewId) {
        this.interviewId = interviewId;
        return this;
    }

    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ApplicationInterview)) {
            return false;
        }
        return id != null && id.equals(((ApplicationInterview) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserInterview{" +
            "id=" + getId() +
            ", userId='" + getApplicationId() + "'" +
            ", interviewId=" + getInterviewId() +
            "}";
    }
}
