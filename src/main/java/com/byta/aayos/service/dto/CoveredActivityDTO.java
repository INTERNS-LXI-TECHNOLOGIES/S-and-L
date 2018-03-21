package com.byta.aayos.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the CoveredActivity entity.
 */
public class CoveredActivityDTO implements Serializable {

    private Long id;

    private String activityName;

    private Double activityPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public Double getActivityPrice() {
        return activityPrice;
    }

    public void setActivityPrice(Double activityPrice) {
        this.activityPrice = activityPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CoveredActivityDTO coveredActivityDTO = (CoveredActivityDTO) o;
        if(coveredActivityDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), coveredActivityDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CoveredActivityDTO{" +
            "id=" + getId() +
            ", activityName='" + getActivityName() + "'" +
            ", activityPrice=" + getActivityPrice() +
            "}";
    }
}
