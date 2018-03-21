package com.byta.aayos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.aayos.service.InsuranceCategoryService;
import com.byta.aayos.web.rest.errors.BadRequestAlertException;
import com.byta.aayos.web.rest.util.HeaderUtil;
import com.byta.aayos.web.rest.util.PaginationUtil;
import com.byta.aayos.service.dto.InsuranceCategoryDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InsuranceCategory.
 */
@RestController
@RequestMapping("/api")
public class InsuranceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(InsuranceCategoryResource.class);

    private static final String ENTITY_NAME = "insuranceCategory";

    private final InsuranceCategoryService insuranceCategoryService;

    public InsuranceCategoryResource(InsuranceCategoryService insuranceCategoryService) {
        this.insuranceCategoryService = insuranceCategoryService;
    }

    /**
     * POST  /insurance-categories : Create a new insuranceCategory.
     *
     * @param insuranceCategoryDTO the insuranceCategoryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insuranceCategoryDTO, or with status 400 (Bad Request) if the insuranceCategory has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurance-categories")
    @Timed
    public ResponseEntity<InsuranceCategoryDTO> createInsuranceCategory(@RequestBody InsuranceCategoryDTO insuranceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save InsuranceCategory : {}", insuranceCategoryDTO);
        if (insuranceCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new insuranceCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsuranceCategoryDTO result = insuranceCategoryService.save(insuranceCategoryDTO);
        return ResponseEntity.created(new URI("/api/insurance-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurance-categories : Updates an existing insuranceCategory.
     *
     * @param insuranceCategoryDTO the insuranceCategoryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insuranceCategoryDTO,
     * or with status 400 (Bad Request) if the insuranceCategoryDTO is not valid,
     * or with status 500 (Internal Server Error) if the insuranceCategoryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurance-categories")
    @Timed
    public ResponseEntity<InsuranceCategoryDTO> updateInsuranceCategory(@RequestBody InsuranceCategoryDTO insuranceCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update InsuranceCategory : {}", insuranceCategoryDTO);
        if (insuranceCategoryDTO.getId() == null) {
            return createInsuranceCategory(insuranceCategoryDTO);
        }
        InsuranceCategoryDTO result = insuranceCategoryService.save(insuranceCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insuranceCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurance-categories : get all the insuranceCategories.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of insuranceCategories in body
     */
    @GetMapping("/insurance-categories")
    @Timed
    public ResponseEntity<List<InsuranceCategoryDTO>> getAllInsuranceCategories(Pageable pageable) {
        log.debug("REST request to get a page of InsuranceCategories");
        Page<InsuranceCategoryDTO> page = insuranceCategoryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/insurance-categories");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /insurance-categories/:id : get the "id" insuranceCategory.
     *
     * @param id the id of the insuranceCategoryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insuranceCategoryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/insurance-categories/{id}")
    @Timed
    public ResponseEntity<InsuranceCategoryDTO> getInsuranceCategory(@PathVariable Long id) {
        log.debug("REST request to get InsuranceCategory : {}", id);
        InsuranceCategoryDTO insuranceCategoryDTO = insuranceCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insuranceCategoryDTO));
    }

    /**
     * DELETE  /insurance-categories/:id : delete the "id" insuranceCategory.
     *
     * @param id the id of the insuranceCategoryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurance-categories/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsuranceCategory(@PathVariable Long id) {
        log.debug("REST request to delete InsuranceCategory : {}", id);
        insuranceCategoryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
