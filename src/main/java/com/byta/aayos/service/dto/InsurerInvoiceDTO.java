package com.byta.aayos.service.dto;


import java.time.LocalDate;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the InsurerInvoice entity.
 */
public class InsurerInvoiceDTO implements Serializable {

    private Long id;

    private Long invoiceNumber;

    private LocalDate invoiceDate;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long insurerId;

    private Long insurerInvoiceStatusId;

    private Long departmentId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getInsurerId() {
        return insurerId;
    }

    public void setInsurerId(Long insurerId) {
        this.insurerId = insurerId;
    }

    public Long getInsurerInvoiceStatusId() {
        return insurerInvoiceStatusId;
    }

    public void setInsurerInvoiceStatusId(Long insurerInvoiceStatusId) {
        this.insurerInvoiceStatusId = insurerInvoiceStatusId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InsurerInvoiceDTO insurerInvoiceDTO = (InsurerInvoiceDTO) o;
        if(insurerInvoiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insurerInvoiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsurerInvoiceDTO{" +
            "id=" + getId() +
            ", invoiceNumber=" + getInvoiceNumber() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
