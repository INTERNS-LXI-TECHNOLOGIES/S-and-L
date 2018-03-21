package com.byta.aayos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A CoveredActivity.
 */
@Entity
@Table(name = "covered_activity")
public class CoveredActivity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_name")
    private String activityName;

    @Column(name = "activity_price")
    private Double activityPrice;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public CoveredActivity activityName(String activityName) {
        this.activityName = activityName;
        return this;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Double getActivityPrice() {
        return activityPrice;
    }

    public CoveredActivity activityPrice(Double activityPrice) {
        this.activityPrice = activityPrice;
        return this;
    }

    public void setActivityPrice(Double activityPrice) {
        this.activityPrice = activityPrice;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoveredActivity coveredActivity = (CoveredActivity) o;
        if (coveredActivity.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coveredActivity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoveredActivity{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            ", activityPrice=" + getActivityPrice() +
            "}";
    }
}
