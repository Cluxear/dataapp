package com.tw.dataapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UserApplication.
 */
@Entity
@Table(name = "user_application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserApplication implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "job_post_id")
    private Long jobPostId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public UserApplication userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public UserApplication applicationId(Long applicationId) {
        this.applicationId = applicationId;
        return this;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getJobPostId() {
        return jobPostId;
    }

    public UserApplication jobPostId(Long jobPostId) {
        this.jobPostId = jobPostId;
        return this;
    }

    public void setJobPostId(Long jobPostId) {
        this.jobPostId = jobPostId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserApplication)) {
            return false;
        }
        return id != null && id.equals(((UserApplication) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserApplication{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", applicationId=" + getApplicationId() +
            ", jobPostId=" + getJobPostId() +
            "}";
    }
}
