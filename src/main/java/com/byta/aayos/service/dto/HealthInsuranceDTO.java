package com.byta.aayos.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * A DTO for the HealthInsurance entity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HealthInsuranceDTO implements Serializable {

    private Long id;

    private Long patientId;

    private LocalDate createdDate;

    private Long insuranceNumber;

    private LocalDate expiryDate;

    private String coverager;

    private Long categoryId;

    private Long statusId;

    private Long insurerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Long getInsuranceNumber() {
        return insuranceNumber;
    }

    public void setInsuranceNumber(Long insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCoverager() {
        return coverager;
    }

    public void setCoverager(String coverager) {
        this.coverager = coverager;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long insuranceCategoryId) {
        this.categoryId = insuranceCategoryId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long relationshipId) {
        this.statusId = relationshipId;
    }

    public Long getInsurerId() {
        return insurerId;
    }

    public void setInsurerId(Long insurerId) {
        this.insurerId = insurerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HealthInsuranceDTO healthInsuranceDTO = (HealthInsuranceDTO) o;
        if(healthInsuranceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), healthInsuranceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HealthInsuranceDTO{" +
            "id=" + getId() +
            ", patientId=" + getPatientId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", insuranceNumber=" + getInsuranceNumber() +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", coverager='" + getCoverager() + "'" +
            "}";
    }
}
