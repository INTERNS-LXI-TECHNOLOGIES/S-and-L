package com.byta.aayos.service.dto;


import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the InsuranceCategory entity.
 */
public class InsuranceCategoryDTO implements Serializable {

    private Long id;

    private String categoryName;

    private Set<CoveredActivityDTO> coveredActivityns = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<CoveredActivityDTO> getCoveredActivityns() {
        return coveredActivityns;
    }

    public void setCoveredActivityns(Set<CoveredActivityDTO> coveredActivities) {
        this.coveredActivityns = coveredActivities;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InsuranceCategoryDTO insuranceCategoryDTO = (InsuranceCategoryDTO) o;
        if(insuranceCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insuranceCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsuranceCategoryDTO{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            "}";
    }
}
