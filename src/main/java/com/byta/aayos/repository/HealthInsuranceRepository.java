package com.byta.aayos.repository;

import com.byta.aayos.domain.HealthInsurance;
import com.byta.aayos.domain.InsuranceCategory;
import com.google.gag.annotation.enforceable.AnswerToTheUltimateQuestionOfLifeTheUniverseAndEverything;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;


/**
 * Spring Data JPA repository for the HealthInsurance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HealthInsuranceRepository extends JpaRepository<HealthInsurance, Long> {

	HealthInsurance findByPatientId(Long patientId);

	HealthInsurance findByInsuranceNumber(Long insuranceNumber);

	HealthInsurance findByCreatedDate(LocalDate createdDate);

	List<HealthInsurance> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate);

	List<HealthInsurance> findByInsurerId(Long insurerId);
	
	//@Query(value="Select distinct  ins  from Patient p Join p.insurars  ins  Where p.id =:id ")
	@Query(value="Select distinct c from HealthInsurance hi Join hi.category c Where hi.id =:id")
	InsuranceCategory findcategoriesByInsuranceId(@Param("id") Long id);

	//List<HealthInsurance> findAllMarshaled(Pageable pageable);

	@Query(value="Select distinct h from HealthInsurance h join fetch h.category c join fetch c.coveredActivityns ca where h.patientId=:patientId")
	HealthInsurance findCoveredActivitiesByInsuranceNumber(@Param("patientId") Long patientId);
 
}


 