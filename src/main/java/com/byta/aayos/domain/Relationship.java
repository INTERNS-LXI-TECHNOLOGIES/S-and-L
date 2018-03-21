package com.byta.aayos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Relationship.
 */
@Entity
@Table(name = "relationship")
public class Relationship implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "relation")
    private String relation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRelation() {
        return relation;
    }

    public Relationship relation(String relation) {
        this.relation = relation;
        return this;
    }

    public void setRelation(String relation) {
        this.relation = relation;
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
        Relationship relationship = (Relationship) o;
        if (relationship.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), relationship.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Relationship{" +
            "id=" + getId() +
            ", relation='" + getRelation() + "'" +
            "}";
    }
}
