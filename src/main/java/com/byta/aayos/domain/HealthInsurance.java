package com.byta.aayos.domain;


import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A HealthInsurance.
 */
@Entity
@Table(name = "health_insurance")

public class HealthInsurance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @Column(name = "insurance_number")
    private Long insuranceNumber;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "coverager")
    private String coverager;

    @ManyToOne
    private InsuranceCategory category;

    @ManyToOne
    private Relationship status;

    @ManyToOne
    private Insurer insurer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public HealthInsurance patientId(Long patientId) {
        this.patientId = patientId;
        return this;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public HealthInsurance createdDate(LocalDate createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Long getInsuranceNumber() {
        return insuranceNumber;
    }

    public HealthInsurance insuranceNumber(Long insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
        return this;
    }

    public void setInsuranceNumber(Long insuranceNumber) {
        this.insuranceNumber = insuranceNumber;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public HealthInsurance expiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
        return this;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCoverager() {
        return coverager;
    }

    public HealthInsurance coverager(String coverager) {
        this.coverager = coverager;
        return this;
    }

    public void setCoverager(String coverager) {
        this.coverager = coverager;
    }

    public InsuranceCategory getCategory() {
        return category;
    }

    public HealthInsurance category(InsuranceCategory insuranceCategory) {
        this.category = insuranceCategory;
        return this;
    }

    public void setCategory(InsuranceCategory insuranceCategory) {
        this.category = insuranceCategory;
    }

    public Relationship getStatus() {
        return status;
    }

    public HealthInsurance status(Relationship relationship) {
        this.status = relationship;
        return this;
    }

    public void setStatus(Relationship relationship) {
        this.status = relationship;
    }

    public Insurer getInsurer() {
        return insurer;
    }

    public HealthInsurance insurer(Insurer insurer) {
        this.insurer = insurer;
        return this;
    }

    public void setInsurer(Insurer insurer) {
        this.insurer = insurer;
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
        HealthInsurance healthInsurance = (HealthInsurance) o;
        if (healthInsurance.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), healthInsurance.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HealthInsurance{" +
            "id=" + getId() +
            ", patientId=" + getPatientId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", insuranceNumber=" + getInsuranceNumber() +
            ", expiryDate='" + getExpiryDate() + "'" +
            ", coverager='" + getCoverager() + "'" +
            "}";
    }
}
