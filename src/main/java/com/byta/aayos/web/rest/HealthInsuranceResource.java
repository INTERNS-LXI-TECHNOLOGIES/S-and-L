package com.byta.aayos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.aayos.domain.CoveredActivity;
import com.byta.aayos.domain.HealthInsurance;
import com.byta.aayos.domain.InsuranceCategory;
import com.byta.aayos.marshal.HealthInsuranceMarshal;
import com.byta.aayos.model.HealthInsuranceModel;
import com.byta.aayos.service.HealthInsuranceService;
import com.byta.aayos.service.InsuranceCategoryService;
import com.byta.aayos.web.rest.errors.BadRequestAlertException;
import com.byta.aayos.web.rest.util.HeaderUtil;
import com.byta.aayos.web.rest.util.PaginationUtil;
import com.byta.aayos.service.dto.HealthInsuranceDTO;
import com.byta.aayos.service.dto.InsuranceCategoryDTO;

import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing HealthInsurance.
 */
@RestController
@RequestMapping("/api")
public class HealthInsuranceResource {

	private final Logger log = LoggerFactory.getLogger(HealthInsuranceResource.class);

	private static final String ENTITY_NAME = "healthInsurance";

	private final HealthInsuranceService healthInsuranceService;
	@Autowired
	private InsuranceCategoryService insuranceCategoryService;

	public HealthInsuranceResource(HealthInsuranceService healthInsuranceService) {
		this.healthInsuranceService = healthInsuranceService;
	}

	/**
	 * POST /health-insurances : Create a new healthInsurance.
	 *
	 * @param healthInsuranceDTO
	 *            the healthInsuranceDTO to create
	 * @return the ResponseEntity with status 201 (Created) and with body the
	 *         new healthInsuranceDTO, or with status 400 (Bad Request) if the
	 *         healthInsurance has already an ID
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PostMapping("/health-insurances")
	@Timed
	public ResponseEntity<HealthInsuranceDTO> createHealthInsurance(@RequestBody HealthInsuranceDTO healthInsuranceDTO)
			throws URISyntaxException {
		log.debug("REST request to save HealthInsurance : {}", healthInsuranceDTO);
		if (healthInsuranceDTO.getId() != null) {
			throw new BadRequestAlertException("A new healthInsurance cannot already have an ID", ENTITY_NAME,
					"idexists");
		}
		HealthInsuranceDTO result = healthInsuranceService.save(healthInsuranceDTO);
		return ResponseEntity.created(new URI("/api/health-insurances/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString())).body(result);
	}

	/**
	 * PUT /health-insurances : Updates an existing healthInsurance.
	 *
	 * @param healthInsuranceDTO
	 *            the healthInsuranceDTO to update
	 * @return the ResponseEntity with status 200 (OK) and with body the updated
	 *         healthInsuranceDTO, or with status 400 (Bad Request) if the
	 *         healthInsuranceDTO is not valid, or with status 500 (Internal
	 *         Server Error) if the healthInsuranceDTO couldn't be updated
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/health-insurances")
	@Timed
	public ResponseEntity<HealthInsuranceDTO> updateHealthInsurance(@RequestBody HealthInsuranceDTO healthInsuranceDTO)
			throws URISyntaxException {
		log.debug("REST request to update HealthInsurance : {}", healthInsuranceDTO);
		if (healthInsuranceDTO.getId() == null) {
			return createHealthInsurance(healthInsuranceDTO);
		}
		HealthInsuranceDTO result = healthInsuranceService.save(healthInsuranceDTO);
		return ResponseEntity.ok()
				.headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, healthInsuranceDTO.getId().toString()))
				.body(result);
	}

	/**
	 * GET /health-insurances : get all the healthInsurances.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         healthInsurances in body
	 */
	@GetMapping("/health-insurances")
	@Timed
	public ResponseEntity<List<HealthInsuranceDTO>> getAllHealthInsurances(Pageable pageable) {
		log.debug("REST request to get a page of HealthInsurances");
		Page<HealthInsuranceDTO> page = healthInsuranceService.findAll(pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/health-insurances");
		return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
	}

	/*
	 * public ResponseEntity<List<HealthInsuranceMarshal>>
	 * getAllMarshaledHealthInsurances(Pageable pageable) {
	 * log.debug("REST request to get a page of HealthInsurances");
	 * Page<HealthInsuranceDTO> healthInsuranceDTOs =
	 * healthInsuranceService.findAll(pageable);
	 * 
	 * List<HealthInsuranceMarshal> healthInsuranceMarshals = new ArrayList<>();
	 * for (HealthInsuranceDTO healthInsuranceDTO : healthInsuranceDTOs) {
	 * HealthInsuranceMarshal healthInsuranceMarshal = new
	 * HealthInsuranceMarshal();
	 * healthInsuranceMarshal.setId(healthInsuranceDTO.getId());
	 * healthInsuranceMarshal.setCreatedDate(healthInsuranceDTO.getCreatedDate()
	 * );
	 * healthInsuranceMarshal.setExpiryDate(healthInsuranceDTO.getExpiryDate());
	 * healthInsuranceMarshal.setInsuranceNumber(healthInsuranceDTO.
	 * getInsuranceNumber());
	 * healthInsuranceMarshal.setPatientId(healthInsuranceDTO.getPatientId());
	 * healthInsuranceMarshal.setCoverager(healthInsuranceDTO.getCoverager());
	 * log.debug("REST request to get a insuranceCategory by using category id"
	 * ); InsuranceCategoryDTO insuranceCategoryDTO =
	 * insuranceCategoryService.findOne(healthInsuranceDTO.getCategoryId());
	 * InsuranceCategory insuranceCategory=mapper(insuranceCategoryDTO); //
	 * insuranceCategoryDTO.getCoveredActivityns().
	 * healthInsuranceMarshal.setCategory(insuranceCategory);
	 * healthInsuranceMarshals.add(healthInsuranceMarshal); } return
	 * ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthInsuranceMarshals))
	 * ;
	 * 
	 * }
	 * 
	 * InsuranceCategory mapper(InsuranceCategoryDTO insuranceCategoryDTO) {
	 * InsuranceCategory insuranceCategory = new InsuranceCategory();
	 * insuranceCategory.setId(insuranceCategoryDTO.getId());
	 * insuranceCategory.setCategoryName(insuranceCategoryDTO.getCategoryName())
	 * ;
	 * 
	 * return insuranceCategory; }
	 * 
	 * @GetMapping("/health-insurances-marshaled")
	 * 
	 * @Timed public ResponseEntity<List<HealthInsuranceMarshal>>
	 * getAllMarshaledHealthInsurances(Pageable pageable) {
	 * log.debug("REST request to get a page of HealthInsurances");
	 * Page<HealthInsuranceDTO> healthInsuranceDTOs =
	 * healthInsuranceService.findAll(pageable);
	 * 
	 * List<HealthInsuranceMarshal> healthInsuranceMarshals = new ArrayList<>();
	 * for (HealthInsuranceDTO healthInsuranceDTO : healthInsuranceDTOs) {
	 * log.debug("check for loop HealthInsurances"); HealthInsuranceMarshal
	 * healthInsuranceMarshal = new HealthInsuranceMarshal();
	 * healthInsuranceMarshal.setId(healthInsuranceDTO.getId());
	 * healthInsuranceMarshal.setCreatedDate(healthInsuranceDTO.getCreatedDate()
	 * );
	 * healthInsuranceMarshal.setExpiryDate(healthInsuranceDTO.getExpiryDate());
	 * healthInsuranceMarshal.setInsuranceNumber(healthInsuranceDTO.
	 * getInsuranceNumber());
	 * healthInsuranceMarshal.setPatientId(healthInsuranceDTO.getPatientId());
	 * healthInsuranceMarshal.setCoverager(healthInsuranceDTO.getCoverager());
	 * InsuranceCategory
	 * insuranceCategories= .findcategoriesByInsuranceId(
	 * healthInsuranceDTO.getId());
	 * 
	 * healthInsuranceMarshal.setCategory(insuranceCategories);
	 * healthInsuranceMarshals.add(healthInsuranceMarshal);
	 * 
	 * } return
	 * ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthInsuranceMarshals))
	 * ; }
	 * 
	 */
	/**
	 * GET /health-insurances : get all the marshaled healthInsurances.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the list of
	 *         healthInsurances in body
	 */
	@GetMapping("/health-insurances-marshaled")
	@Timed
	public ResponseEntity<List<HealthInsurance>> getAllMarshaledHealthInsurances(Pageable pageable) {
		log.debug("REST request to get a page of HealthInsurances");
		Page<HealthInsurance>healthInsurances=healthInsuranceService.findAllMarshaled(pageable);

		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(healthInsurances, "/api/health-insurances");
		return new ResponseEntity<>(healthInsurances.getContent(), headers, HttpStatus.OK);
	}
	@GetMapping("/health-insurance-marshaled")
	@Timed
	public ResponseEntity<ArrayList<CoveredActivity>> findCoveredActivitiesByInsuranceNumber(Long patientId) {
		log.debug("REST request to get a page of HealthInsurances");
		ArrayList<CoveredActivity> coveredActivities = healthInsuranceService.findCoveredActivitiesByInsuranceNumber(patientId);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coveredActivities));

	}

	/**
	 * GET /health-insurances/:id : get the "id" healthInsurance.
	 *
	 * @param id
	 *            the id of the healthInsuranceDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         healthInsuranceDTO, or with status 404 (Not Found)
	 */
	@GetMapping("/health-insurances/{id}")
	@Timed
	public ResponseEntity<HealthInsuranceDTO> getHealthInsurance(@PathVariable Long id) {
		log.debug("REST request to get HealthInsurance : {}", id);
		HealthInsuranceDTO healthInsuranceDTO = healthInsuranceService.findOne(id);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthInsuranceDTO));
	}

	/**
	 * GET /health-insurances/:patientId : get the "patientId" healthInsurance.
	 *
	 * @param patientid
	 *            the patientId of the healthInsuranceDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         healthInsuranceDTO, or with status 404 (Not Found)
	 */

	@GetMapping("/health-insurances/findByPatientId")
	@Timed
	public ResponseEntity<HealthInsuranceDTO> findByPatientId(Long patientId) {
		log.debug("REST request to get HealthInsurance : {}", patientId);
		HealthInsuranceDTO healthInsuranceDTO = healthInsuranceService.findByPatientId(patientId);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthInsuranceDTO));
	}

	/**
	 * GET /health-insurances/:insuranceNumber : get the "insuranceNumber"
	 * healthInsurance.
	 *
	 * @param insuranceNumber
	 *            the insuranceNumber of the healthInsuranceDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         healthInsuranceDTO, or with status 404 (Not Found)
	 */

	@GetMapping("/health-insurances/findByInsuranceNumber")
	@Timed
	public ResponseEntity<HealthInsuranceDTO> findByInsuranceNumber(Long insuranceNumber) {
		log.debug("REST request to get HealthInsurance : {}", insuranceNumber);
		HealthInsuranceDTO healthInsuranceDTO = healthInsuranceService.findByInsuranceNumber(insuranceNumber);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthInsuranceDTO));
	}

	/**
	 * GET /health-insurances/:insurerId : get the "insurerId" healthInsurance.
	 *
	 * @param insurerId
	 *            the insurerId of the healthInsuranceDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         healthInsuranceDTO, or with status 404 (Not Found)
	 */

	@GetMapping("/health-insurances/findByInsurer")
	@Timed
	public ResponseEntity<List<HealthInsuranceDTO>> findByInsurer(Long insurerId) {
		log.debug("REST request to get HealthInsurance : {}", insurerId);
		List<HealthInsuranceDTO> healthInsuranceDTO = healthInsuranceService.findByInsurer(insurerId);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthInsuranceDTO));
	}

	/**
	 * GET /health-insurances/:insuranceNumber : get the "insuranceNumber"
	 * healthInsurance.
	 *
	 * @param patientid
	 *            the insuranceNumber of the healthInsuranceDTO to retrieve
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         healthInsuranceDTO, or with status 404 (Not Found)
	 */

	@GetMapping("/health-insurances/	")
	@Timed
	public ResponseEntity<List<HealthInsuranceDTO>> findByCreatedDateBetween(@RequestParam LocalDate startDate,
			@RequestParam LocalDate endDate) {
		log.debug("REST request to get HealthInsurance : {}", startDate, endDate);
		List<HealthInsuranceDTO> healthInsuranceDTO = healthInsuranceService.findByCreatedDateBetween(startDate,
				endDate);
		return ResponseUtil.wrapOrNotFound(Optional.ofNullable(healthInsuranceDTO));
	}

	/**
	 * DELETE /health-insurances/:id : delete the "id" healthInsurance.
	 *
	 * @param id
	 *            the id of the healthInsuranceDTO to delete
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@DeleteMapping("/health-insurances/{id}")
	@Timed
	public ResponseEntity<Void> deleteHealthInsurance(@PathVariable Long id) {
		log.debug("REST request to delete HealthInsurance : {}", id);
		healthInsuranceService.delete(id);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	}
}