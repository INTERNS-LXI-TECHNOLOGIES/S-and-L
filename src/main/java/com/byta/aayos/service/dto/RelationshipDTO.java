package com.byta.aayos.service.dto;


import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Relationship entity.
 */
public class RelationshipDTO implements Serializable {

    private Long id;

    private String relation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RelationshipDTO relationshipDTO = (RelationshipDTO) o;
        if(relationshipDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), relationshipDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RelationshipDTO{" +
            "id=" + getId() +
            ", relation='" + getRelation() + "'" +
            "}";
    }
}
