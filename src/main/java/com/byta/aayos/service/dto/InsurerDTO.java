package com.byta.aayos.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Insurer entity.
 */
public class InsurerDTO implements Serializable {

    private Long id;

    private String insurarName;

    private Long contactNumber;

    private String address;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsurarName() {
        return insurarName;
    }

    public void setInsurarName(String insurarName) {
        this.insurarName = insurarName;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InsurerDTO insurerDTO = (InsurerDTO) o;
        if(insurerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insurerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsurerDTO{" +
            "id=" + getId() +
            ", insurarName='" + getInsurarName() + "'" +
            ", contactNumber=" + getContactNumber() +
            ", address='" + getAddress() + "'" +
            "}";
    }
}
