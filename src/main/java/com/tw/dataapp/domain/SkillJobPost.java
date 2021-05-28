package com.tw.dataapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SkillJobPost.
 */
@Entity
@Table(name = "skill_job_post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SkillJobPost implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "job_post_id")
    private Long jobPostId;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSkillId() {
        return skillId;
    }

    public SkillJobPost skillId(Long skillId) {
        this.skillId = skillId;
        return this;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public Long getJobPostId() {
        return jobPostId;
    }

    public SkillJobPost jobPostId(Long jobPostId) {
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
        if (!(o instanceof SkillJobPost)) {
            return false;
        }
        return id != null && id.equals(((SkillJobPost) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SkillJobPost{" +
            "id=" + getId() +
            ", skillId=" + getSkillId() +
            ", jobPostId=" + getJobPostId() +
            "}";
    }
}
