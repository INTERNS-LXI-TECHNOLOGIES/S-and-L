package com.byta.aayos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Insurer.
 */
@Entity
@Table(name = "insurer")
public class Insurer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "insurar_name")
    private String insurarName;

    @Column(name = "contact_number")
    private Long contactNumber;

    @Column(name = "address")
    private String address;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsurarName() {
        return insurarName;
    }

    public Insurer insurarName(String insurarName) {
        this.insurarName = insurarName;
        return this;
    }

    public void setInsurarName(String insurarName) {
        this.insurarName = insurarName;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public Insurer contactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public Insurer address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
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
        Insurer insurer = (Insurer) o;
        if (insurer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insurer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Insurer{" +
            "id=" + getId() +
            ", insurarName='" + getInsurarName() + "'" +
            ", contactNumber=" + getContactNumber() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
