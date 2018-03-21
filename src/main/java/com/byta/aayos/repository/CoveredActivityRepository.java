package com.byta.aayos.repository;

import com.byta.aayos.domain.CoveredActivity;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CoveredActivity entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CoveredActivityRepository extends JpaRepository<CoveredActivity, Long> {

}
