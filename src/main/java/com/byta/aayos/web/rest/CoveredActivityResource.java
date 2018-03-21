package com.byta.aayos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.aayos.service.CoveredActivityService;
import com.byta.aayos.web.rest.errors.BadRequestAlertException;
import com.byta.aayos.web.rest.util.HeaderUtil;
import com.byta.aayos.web.rest.util.PaginationUtil;
import com.byta.aayos.service.dto.CoveredActivityDTO;
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
 * REST controller for managing CoveredActivity.
 */
@RestController
@RequestMapping("/api")
public class CoveredActivityResource {

    private final Logger log = LoggerFactory.getLogger(CoveredActivityResource.class);

    private static final String ENTITY_NAME = "coveredActivity";

    private final CoveredActivityService coveredActivityService;

    public CoveredActivityResource(CoveredActivityService coveredActivityService) {
        this.coveredActivityService = coveredActivityService;
    }

    /**
     * POST  /covered-activities : Create a new coveredActivity.
     *
     * @param coveredActivityDTO the coveredActivityDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new coveredActivityDTO, or with status 400 (Bad Request) if the coveredActivity has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/covered-activities")
    @Timed
    public ResponseEntity<CoveredActivityDTO> createCoveredActivity(@RequestBody CoveredActivityDTO coveredActivityDTO) throws URISyntaxException {
        log.debug("REST request to save CoveredActivity : {}", coveredActivityDTO);
        if (coveredActivityDTO.getId() != null) {
            throw new BadRequestAlertException("A new coveredActivity cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CoveredActivityDTO result = coveredActivityService.save(coveredActivityDTO);
        return ResponseEntity.created(new URI("/api/covered-activities/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /covered-activities : Updates an existing coveredActivity.
     *
     * @param coveredActivityDTO the coveredActivityDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated coveredActivityDTO,
     * or with status 400 (Bad Request) if the coveredActivityDTO is not valid,
     * or with status 500 (Internal Server Error) if the coveredActivityDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/covered-activities")
    @Timed
    public ResponseEntity<CoveredActivityDTO> updateCoveredActivity(@RequestBody CoveredActivityDTO coveredActivityDTO) throws URISyntaxException {
        log.debug("REST request to update CoveredActivity : {}", coveredActivityDTO);
        if (coveredActivityDTO.getId() == null) {
            return createCoveredActivity(coveredActivityDTO);
        }
        CoveredActivityDTO result = coveredActivityService.save(coveredActivityDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, coveredActivityDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /covered-activities : get all the coveredActivities.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of coveredActivities in body
     */
    @GetMapping("/covered-activities")
    @Timed
    public ResponseEntity<List<CoveredActivityDTO>> getAllCoveredActivities(Pageable pageable) {
        log.debug("REST request to get a page of CoveredActivities");
        Page<CoveredActivityDTO> page = coveredActivityService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/covered-activities");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /covered-activities/:id : get the "id" coveredActivity.
     *
     * @param id the id of the coveredActivityDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the coveredActivityDTO, or with status 404 (Not Found)
     */
    @GetMapping("/covered-activities/{id}")
    @Timed
    public ResponseEntity<CoveredActivityDTO> getCoveredActivity(@PathVariable Long id) {
        log.debug("REST request to get CoveredActivity : {}", id);
        CoveredActivityDTO coveredActivityDTO = coveredActivityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(coveredActivityDTO));
    }

    /**
     * DELETE  /covered-activities/:id : delete the "id" coveredActivity.
     *
     * @param id the id of the coveredActivityDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/covered-activities/{id}")
    @Timed
    public ResponseEntity<Void> deleteCoveredActivity(@PathVariable Long id) {
        log.debug("REST request to delete CoveredActivity : {}", id);
        coveredActivityService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
