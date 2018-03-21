package com.byta.aayos.service.impl;

import com.byta.aayos.service.HealthInsuranceService;
import com.byta.aayos.domain.CoveredActivity;
import com.byta.aayos.domain.HealthInsurance;
import com.byta.aayos.domain.InsuranceCategory;
import com.byta.aayos.exception.ExpiryDateException;
import com.byta.aayos.model.HealthInsuranceModel;
import com.byta.aayos.model.InsuranceCategoryModel;
//import com.byta.aayos.model.InsuranceCategoryModel;
import com.byta.aayos.repository.HealthInsuranceRepository;
import com.byta.aayos.service.dto.HealthInsuranceDTO;
import com.byta.aayos.service.mapper.HealthInsuranceMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing HealthInsurance.
 */
@Service
@Transactional
public class HealthInsuranceServiceImpl implements HealthInsuranceService {

	private final Logger log = LoggerFactory.getLogger(HealthInsuranceServiceImpl.class);

	private final HealthInsuranceRepository healthInsuranceRepository;

	private final HealthInsuranceMapper healthInsuranceMapper;
	// @Autowired
	private HealthInsuranceModel healthInsuranceModel;

	public HealthInsuranceServiceImpl(HealthInsuranceRepository healthInsuranceRepository,
			HealthInsuranceMapper healthInsuranceMapper) {
		this.healthInsuranceRepository = healthInsuranceRepository;
		this.healthInsuranceMapper = healthInsuranceMapper;
	}

	/**
	 * Save a healthInsurance.
	 *
	 * @param healthInsuranceDTO
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public HealthInsuranceDTO save(HealthInsuranceDTO healthInsuranceDTO) {
		log.debug("Request to save HealthInsurance : {}", healthInsuranceDTO);
		healthInsuranceDTO.setCreatedDate(LocalDate.now());

		if (healthInsuranceDTO.getExpiryDate().isBefore(healthInsuranceDTO.getCreatedDate())) {

			return null;
		} else {

			HealthInsurance healthInsurance = healthInsuranceMapper.toEntity(healthInsuranceDTO);
			healthInsurance = healthInsuranceRepository.save(healthInsurance);
			return healthInsuranceMapper.toDto(healthInsurance);
		}
	}

	/**
	 * Get all the healthInsurances.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	@Transactional(readOnly = true)
	public Page<HealthInsuranceDTO> findAll(Pageable pageable) {
		log.debug("Request to get all HealthInsurances");
		return healthInsuranceRepository.findAll(pageable).map(healthInsuranceMapper::toDto);
	}

	/**
	 * Get one healthInsurance by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	@Transactional(readOnly = true)
	public HealthInsuranceDTO findOne(Long id) {
		log.debug("Request to get HealthInsurance : {}", id);
		HealthInsurance healthInsurance = healthInsuranceRepository.findOne(id);
		return healthInsuranceMapper.toDto(healthInsurance);
	}

	public InsuranceCategory findcategoriesByInsuranceId(Long id) {

		return healthInsuranceRepository.findcategoriesByInsuranceId(id);

	} 

	/**
	 * Delete the healthInsurance by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(Long id) {
		log.debug("Request to delete HealthInsurance : {}", id);
		healthInsuranceRepository.delete(id);
	}

	/**
	 * Get one healthInsurance by patientId.
	 *
	 * @param patientId
	 *            the patientId of the entity
	 * @return the entity
	 */
	@Override
	public HealthInsuranceDTO findByPatientId(Long patientId) {
		HealthInsurance healthInsurance = healthInsuranceRepository.findByPatientId(patientId);
		return healthInsuranceMapper.toDto(healthInsurance);

	}

	/**
	 * Get one healthInsurance by insuranceNumber.
	 *
	 * @param insuranceNumber
	 *            the insuranceNumber of the entity
	 * @return the entity
	 */
	@Override
	public HealthInsuranceDTO findByInsuranceNumber(Long insuranceNumber) {
		HealthInsurance healthInsurance = healthInsuranceRepository.findByInsuranceNumber(insuranceNumber);
		return healthInsuranceMapper.toDto(healthInsurance);
	}

	@Override
	public List<HealthInsuranceDTO> findByCreatedDateBetween(LocalDate startDate, LocalDate endDate) {
		List<HealthInsurance> healthInsurance = healthInsuranceRepository.findByCreatedDateBetween(startDate, endDate);
		return healthInsuranceMapper.toDto(healthInsurance);
		// HealthInsurance healthInsurance=
		/*
		 * List<HealthInsurance> healthInsurance=
		 * healthInsuranceRepository.findAll(); for (HealthInsurance
		 * healthInsurance2 : healthInsurance) {
		 * if((healthInsurance2.getCreatedDate().isAfter(startDate))&&(
		 * healthInsurance2.getCreatedDate().isAfter(startDate))) {
		 * 
		 * } }
		 */

	}
 
	@Override
	public List<HealthInsuranceDTO> findByInsurer(Long insurerId) {

		List<HealthInsurance> healthInsurance = healthInsuranceRepository.findByInsurerId(insurerId);
		return healthInsuranceMapper.toDto(healthInsurance);
	}

	@Override
	public  ArrayList<CoveredActivity> findCoveredActivitiesByInsuranceNumber(Long patientId) {
		HealthInsurance healthInsurance = healthInsuranceRepository.findCoveredActivitiesByInsuranceNumber(patientId);
	//	LocalDate localDate=LocalDate.of(2017,12,12);
		if(healthInsurance.getExpiryDate().isBefore(LocalDate.now()))
		{ 
		try {
			throw new ExpiryDateException();
		} catch (ExpiryDateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		}
	
	ArrayList<CoveredActivity> coveredActivities=new ArrayList<>(healthInsurance.getCategory().getCoveredActivityns());
	
	/*
		healthInsuranceModel = new HealthInsuranceModel();
		healthInsuranceModel.setId(healthInsurance.getId());
	healthInsuranceModel.setPatientId(healthInsurance.getPatientId());
		healthInsuranceModel.setExpiryDate(healthInsurance.getExpiryDate());
		healthInsuranceModel.setCoverager(healthInsurance.getCoverager());
		healthInsuranceModel.setCategory(healthInsurance.getCategory());
		healthInsuranceModel.setInsurer(healthInsurance.getInsurer());
		
		healthInsuranceModel.getCategory().setCoveredActivityns(healthInsurance.getCategory().getCoveredActivityns());*/
		
		return coveredActivities;
	}

	public Page<HealthInsurance> findAllMarshaled(Pageable pageable) {

		return healthInsuranceRepository.findAll(pageable);

	}
}
