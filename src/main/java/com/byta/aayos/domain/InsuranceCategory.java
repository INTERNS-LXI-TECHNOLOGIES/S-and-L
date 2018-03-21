package com.byta.aayos.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A InsuranceCategory.
 */
@Entity
@Table(name = "insurance_category")
public class InsuranceCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category_name")
    private String categoryName;

    @ManyToMany
    @JoinTable(name = "insurance_category_covered_activityn",
               joinColumns = @JoinColumn(name="insurance_categories_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="covered_activityns_id", referencedColumnName="id"))
    private Set<CoveredActivity> coveredActivityns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public InsuranceCategory categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Set<CoveredActivity> getCoveredActivityns() {
        return coveredActivityns;
    }

    public InsuranceCategory coveredActivityns(Set<CoveredActivity> coveredActivities) {
        this.coveredActivityns = coveredActivities;
        return this;
    }

    public InsuranceCategory addCoveredActivityn(CoveredActivity coveredActivity) {
        this.coveredActivityns.add(coveredActivity);
        return this;
    }

    public InsuranceCategory removeCoveredActivityn(CoveredActivity coveredActivity) {
        this.coveredActivityns.remove(coveredActivity);
        return this;
    }

    public void setCoveredActivityns(Set<CoveredActivity> coveredActivities) {
        this.coveredActivityns = coveredActivities;
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
        InsuranceCategory insuranceCategory = (InsuranceCategory) o;
        if (insuranceCategory.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insuranceCategory.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "InsuranceCategory{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            "}";
    }
}
