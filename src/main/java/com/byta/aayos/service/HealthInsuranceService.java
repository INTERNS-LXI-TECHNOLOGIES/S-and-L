package com.byta.aayos.service;

import com.byta.aayos.domain.CoveredActivity;
import com.byta.aayos.domain.HealthInsurance;
import com.byta.aayos.domain.InsuranceCategory;
import com.byta.aayos.model.HealthInsuranceModel;
import com.byta.aayos.service.dto.HealthInsuranceDTO;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing HealthInsurance.
 */
public interface HealthInsuranceService {

    /**
     * Save a healthInsurance.
     *
     * @param healthInsuranceDTO the entity to save
     * @return the persisted entity
     */
    HealthInsuranceDTO save(HealthInsuranceDTO healthInsuranceDTO);

    /**
     * Get all the healthInsurances.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<HealthInsuranceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" healthInsurance.
     *
     * @param id the id of the entity
     * @return the entity
     */
    HealthInsuranceDTO findOne(Long id);

    /**
     * Delete the "id" healthInsurance.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Get the "patientId" healthInsurance.
     *
     * @param patientId the patientId of the entity
     * @return the entity
     */
	HealthInsuranceDTO findByPatientId(Long patientId);

	/**
     * Get the "insuranceNumber" healthInsurance.
     *
     * @param insuranceNumber the insuranceNumber of the entity
     * @return the entity
     */
	HealthInsuranceDTO findByInsuranceNumber(Long insuranceNumber);
	
	
	/**
     * Get the "createdDate" healthInsurance.
     *
     * @param startDate,endDate 
     * @return the List of entity
     */
	List<HealthInsuranceDTO> findByCreatedDateBetween(LocalDate startDate,LocalDate endDate);
	
	
	/**
     * Get the "createdDate" healthInsurance.
     *
     * @param insurerId 
     * @return the List of entity
     */
	List<HealthInsuranceDTO> findByInsurer(Long insurerId);

	InsuranceCategory findcategoriesByInsuranceId(Long id);

	Page<HealthInsurance> findAllMarshaled(Pageable pageable);

	ArrayList<CoveredActivity> findCoveredActivitiesByInsuranceNumber(Long patientId);

	}
 