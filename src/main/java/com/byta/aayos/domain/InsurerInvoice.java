package com.byta.aayos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A InsurerInvoice.
 */
@Entity
@Table(name = "insurer_invoice")
public class InsurerInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "invoice_number")
    private Long invoiceNumber;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @ManyToOne
    private Insurer insurer;

    @ManyToOne
    private InsurerInvoiceStatus insurerInvoiceStatus;

    @ManyToOne
    private Department department;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInvoiceNumber() {
        return invoiceNumber;
    }

    public InsurerInvoice invoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    public void setInvoiceNumber(Long invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public InsurerInvoice invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public InsurerInvoice startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public InsurerInvoice endDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Insurer getInsurer() {
        return insurer;
    }

    public InsurerInvoice insurer(Insurer insurer) {
        this.insurer = insurer;
        return this;
    }

    public void setInsurer(Insurer insurer) {
        this.insurer = insurer;
    }

    public InsurerInvoiceStatus getInsurerInvoiceStatus() {
        return insurerInvoiceStatus;
    }

    public InsurerInvoice insurerInvoiceStatus(InsurerInvoiceStatus insurerInvoiceStatus) {
        this.insurerInvoiceStatus = insurerInvoiceStatus;
        return this;
    }

    public void setInsurerInvoiceStatus(InsurerInvoiceStatus insurerInvoiceStatus) {
        this.insurerInvoiceStatus = insurerInvoiceStatus;
    }

    public Department getDepartment() {
        return department;
    }

    public InsurerInvoice department(Department department) {
        this.department = department;
        return this;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        InsurerInvoice insurerInvoice = (InsurerInvoice) o;
        if (insurerInvoice.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insurerInvoice.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsurerInvoice{" +
            "id=" + getId() +
            ", invoiceNumber=" + getInvoiceNumber() +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }
}
