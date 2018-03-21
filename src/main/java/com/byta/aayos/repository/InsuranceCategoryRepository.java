package com.byta.aayos.repository;

import com.byta.aayos.domain.InsuranceCategory;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the InsuranceCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsuranceCategoryRepository extends JpaRepository<InsuranceCategory, Long> {
    @Query("select distinct insurance_category from InsuranceCategory insurance_category left join fetch insurance_category.coveredActivityns")
    List<InsuranceCategory> findAllWithEagerRelationships();

    @Query("select insurance_category from InsuranceCategory insurance_category left join fetch insurance_category.coveredActivityns where insurance_category.id =:id")
    InsuranceCategory findOneWithEagerRelationships(@Param("id") Long id);

}
