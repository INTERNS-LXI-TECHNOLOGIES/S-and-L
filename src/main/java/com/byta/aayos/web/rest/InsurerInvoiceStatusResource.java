package com.byta.aayos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.aayos.service.InsurerInvoiceStatusService;
import com.byta.aayos.web.rest.errors.BadRequestAlertException;
import com.byta.aayos.web.rest.util.HeaderUtil;
import com.byta.aayos.web.rest.util.PaginationUtil;
import com.byta.aayos.service.dto.InsurerInvoiceStatusDTO;
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
 * REST controller for managing InsurerInvoiceStatus.
 */
@RestController
@RequestMapping("/api")
public class InsurerInvoiceStatusResource {

    private final Logger log = LoggerFactory.getLogger(InsurerInvoiceStatusResource.class);

    private static final String ENTITY_NAME = "insurerInvoiceStatus";

    private final InsurerInvoiceStatusService insurerInvoiceStatusService;

    public InsurerInvoiceStatusResource(InsurerInvoiceStatusService insurerInvoiceStatusService) {
        this.insurerInvoiceStatusService = insurerInvoiceStatusService;
    }

    /**
     * POST  /insurer-invoice-statuses : Create a new insurerInvoiceStatus.
     *
     * @param insurerInvoiceStatusDTO the insurerInvoiceStatusDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insurerInvoiceStatusDTO, or with status 400 (Bad Request) if the insurerInvoiceStatus has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurer-invoice-statuses")
    @Timed
    public ResponseEntity<InsurerInvoiceStatusDTO> createInsurerInvoiceStatus(@RequestBody InsurerInvoiceStatusDTO insurerInvoiceStatusDTO) throws URISyntaxException {
        log.debug("REST request to save InsurerInvoiceStatus : {}", insurerInvoiceStatusDTO);
        if (insurerInvoiceStatusDTO.getId() != null) {
            throw new BadRequestAlertException("A new insurerInvoiceStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsurerInvoiceStatusDTO result = insurerInvoiceStatusService.save(insurerInvoiceStatusDTO);
        return ResponseEntity.created(new URI("/api/insurer-invoice-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurer-invoice-statuses : Updates an existing insurerInvoiceStatus.
     *
     * @param insurerInvoiceStatusDTO the insurerInvoiceStatusDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insurerInvoiceStatusDTO,
     * or with status 400 (Bad Request) if the insurerInvoiceStatusDTO is not valid,
     * or with status 500 (Internal Server Error) if the insurerInvoiceStatusDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurer-invoice-statuses")
    @Timed
    public ResponseEntity<InsurerInvoiceStatusDTO> updateInsurerInvoiceStatus(@RequestBody InsurerInvoiceStatusDTO insurerInvoiceStatusDTO) throws URISyntaxException {
        log.debug("REST request to update InsurerInvoiceStatus : {}", insurerInvoiceStatusDTO);
        if (insurerInvoiceStatusDTO.getId() == null) {
            return createInsurerInvoiceStatus(insurerInvoiceStatusDTO);
        }
        InsurerInvoiceStatusDTO result = insurerInvoiceStatusService.save(insurerInvoiceStatusDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insurerInvoiceStatusDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurer-invoice-statuses : get all the insurerInvoiceStatuses.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of insurerInvoiceStatuses in body
     */
    @GetMapping("/insurer-invoice-statuses")
    @Timed
    public ResponseEntity<List<InsurerInvoiceStatusDTO>> getAllInsurerInvoiceStatuses(Pageable pageable) {
        log.debug("REST request to get a page of InsurerInvoiceStatuses");
        Page<InsurerInvoiceStatusDTO> page = insurerInvoiceStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/insurer-invoice-statuses");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /insurer-invoice-statuses/:id : get the "id" insurerInvoiceStatus.
     *
     * @param id the id of the insurerInvoiceStatusDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insurerInvoiceStatusDTO, or with status 404 (Not Found)
     */
    @GetMapping("/insurer-invoice-statuses/{id}")
    @Timed
    public ResponseEntity<InsurerInvoiceStatusDTO> getInsurerInvoiceStatus(@PathVariable Long id) {
        log.debug("REST request to get InsurerInvoiceStatus : {}", id);
        InsurerInvoiceStatusDTO insurerInvoiceStatusDTO = insurerInvoiceStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insurerInvoiceStatusDTO));
    }

    /**
     * DELETE  /insurer-invoice-statuses/:id : delete the "id" insurerInvoiceStatus.
     *
     * @param id the id of the insurerInvoiceStatusDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurer-invoice-statuses/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsurerInvoiceStatus(@PathVariable Long id) {
        log.debug("REST request to delete InsurerInvoiceStatus : {}", id);
        insurerInvoiceStatusService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
