package com.byta.aayos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A InsurerInvoiceStatus.
 */
@Entity
@Table(name = "insurer_invoice_status")
public class InsurerInvoiceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "status")
    private String status;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public InsurerInvoiceStatus status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
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
        InsurerInvoiceStatus insurerInvoiceStatus = (InsurerInvoiceStatus) o;
        if (insurerInvoiceStatus.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insurerInvoiceStatus.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsurerInvoiceStatus{" +
            "id=" + getId() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
