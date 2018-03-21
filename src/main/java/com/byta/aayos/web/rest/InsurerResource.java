package com.byta.aayos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.aayos.service.InsurerService;
import com.byta.aayos.web.rest.errors.BadRequestAlertException;
import com.byta.aayos.web.rest.util.HeaderUtil;
import com.byta.aayos.web.rest.util.PaginationUtil;
import com.byta.aayos.service.dto.InsurerDTO;
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
 * REST controller for managing Insurer.
 */
@RestController
@RequestMapping("/api")
public class InsurerResource {

    private final Logger log = LoggerFactory.getLogger(InsurerResource.class);

    private static final String ENTITY_NAME = "insurer";

    private final InsurerService insurerService;

    public InsurerResource(InsurerService insurerService) {
        this.insurerService = insurerService;
    }

    /**
     * POST  /insurers : Create a new insurer.
     *
     * @param insurerDTO the insurerDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insurerDTO, or with status 400 (Bad Request) if the insurer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurers")
    @Timed
    public ResponseEntity<InsurerDTO> createInsurer(@RequestBody InsurerDTO insurerDTO) throws URISyntaxException {
        log.debug("REST request to save Insurer : {}", insurerDTO);
        if (insurerDTO.getId() != null) {
            throw new BadRequestAlertException("A new insurer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsurerDTO result = insurerService.save(insurerDTO);
        return ResponseEntity.created(new URI("/api/insurers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurers : Updates an existing insurer.
     *
     * @param insurerDTO the insurerDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insurerDTO,
     * or with status 400 (Bad Request) if the insurerDTO is not valid,
     * or with status 500 (Internal Server Error) if the insurerDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurers")
    @Timed
    public ResponseEntity<InsurerDTO> updateInsurer(@RequestBody InsurerDTO insurerDTO) throws URISyntaxException {
        log.debug("REST request to update Insurer : {}", insurerDTO);
        if (insurerDTO.getId() == null) {
            return createInsurer(insurerDTO);
        }
        InsurerDTO result = insurerService.save(insurerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insurerDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurers : get all the insurers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of insurers in body
     */
    @GetMapping("/insurers")
    @Timed
    public ResponseEntity<List<InsurerDTO>> getAllInsurers(Pageable pageable) {
        log.debug("REST request to get a page of Insurers");
        Page<InsurerDTO> page = insurerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/insurers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /insurers/:id : get the "id" insurer.
     *
     * @param id the id of the insurerDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insurerDTO, or with status 404 (Not Found)
     */
    @GetMapping("/insurers/{id}")
    @Timed
    public ResponseEntity<InsurerDTO> getInsurer(@PathVariable Long id) {
        log.debug("REST request to get Insurer : {}", id);
        InsurerDTO insurerDTO = insurerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insurerDTO));
    }

    /**
     * DELETE  /insurers/:id : delete the "id" insurer.
     *
     * @param id the id of the insurerDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurers/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsurer(@PathVariable Long id) {
        log.debug("REST request to delete Insurer : {}", id);
        insurerService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
