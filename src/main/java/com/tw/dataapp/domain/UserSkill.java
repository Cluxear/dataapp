package com.tw.dataapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

import com.tw.dataapp.domain.enumeration.SkillLevel;

/**
 * A UserSkill.
 */
@Entity
@Table(name = "user_skill")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class UserSkill implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "skill_id")
    private Long skillId;

    @Column(name = "skill_level")
    private SkillLevel skillLevel;
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

    public UserSkill userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getSkillId() {
        return skillId;
    }

    public UserSkill skillId(Long skillId) {
        this.skillId = skillId;
        return this;
    }

    public void setSkillId(Long skillId) {
        this.skillId = skillId;
    }

    public SkillLevel getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(SkillLevel skillLevel) {
        this.skillLevel = skillLevel;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserSkill)) {
            return false;
        }
        return id != null && id.equals(((UserSkill) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "UserSkill{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", skillId=" + getSkillId() +
            "}";
    }
}
