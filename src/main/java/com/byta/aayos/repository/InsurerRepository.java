package com.byta.aayos.repository;

import com.byta.aayos.domain.Insurer;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Insurer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsurerRepository extends JpaRepository<Insurer, Long> {

}
