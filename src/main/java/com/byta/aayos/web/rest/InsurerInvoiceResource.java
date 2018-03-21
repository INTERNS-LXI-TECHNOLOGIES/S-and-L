package com.byta.aayos.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.byta.aayos.domain.Insurer;
import com.byta.aayos.domain.InsurerInvoiceStatus;
import com.byta.aayos.service.InsurerInvoiceService;
import com.byta.aayos.web.rest.errors.BadRequestAlertException;
import com.byta.aayos.web.rest.util.HeaderUtil;
import com.byta.aayos.web.rest.util.PaginationUtil;
import com.byta.aayos.service.dto.InsurerInvoiceDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing InsurerInvoice.
 */
@RestController
@RequestMapping("/api")
public class InsurerInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(InsurerInvoiceResource.class);

    private static final String ENTITY_NAME = "insurerInvoice";

    private final InsurerInvoiceService insurerInvoiceService;

    public InsurerInvoiceResource(InsurerInvoiceService insurerInvoiceService) {
        this.insurerInvoiceService = insurerInvoiceService;
    }

    /**
     * POST  /insurer-invoices : Create a new insurerInvoice.
     *
     * @param insurerInvoiceDTO the insurerInvoiceDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insurerInvoiceDTO, or with status 400 (Bad Request) if the insurerInvoice has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insurer-invoices")
    @Timed
    public ResponseEntity<InsurerInvoiceDTO> createInsurerInvoice(@RequestBody InsurerInvoiceDTO insurerInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to save InsurerInvoice : {}", insurerInvoiceDTO);
        if (insurerInvoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new insurerInvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InsurerInvoiceDTO result = insurerInvoiceService.save(insurerInvoiceDTO);
        return ResponseEntity.created(new URI("/api/insurer-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insurer-invoices : Updates an existing insurerInvoice.
     *
     * @param insurerInvoiceDTO the insurerInvoiceDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insurerInvoiceDTO,
     * or with status 400 (Bad Request) if the insurerInvoiceDTO is not valid,
     * or with status 500 (Internal Server Error) if the insurerInvoiceDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insurer-invoices")
    @Timed
    public ResponseEntity<InsurerInvoiceDTO> updateInsurerInvoice(@RequestBody InsurerInvoiceDTO insurerInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to update InsurerInvoice : {}", insurerInvoiceDTO);
        if (insurerInvoiceDTO.getId() == null) {
            return createInsurerInvoice(insurerInvoiceDTO);
        }
        InsurerInvoiceDTO result = insurerInvoiceService.save(insurerInvoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insurerInvoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insurer-invoices : get all the insurerInvoices.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of insurerInvoices in body
     */
    @GetMapping("/insurer-invoices")
    @Timed
    public ResponseEntity<List<InsurerInvoiceDTO>> getAllInsurerInvoices(Pageable pageable) {
        log.debug("REST request to get a page of InsurerInvoices");
        Page<InsurerInvoiceDTO> page = insurerInvoiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/insurer-invoices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /insurer-invoices/:id : get the "id" insurerInvoice.
     *
     * @param id the id of the insurerInvoiceDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insurerInvoiceDTO, or with status 404 (Not Found)
     */
    @GetMapping("/insurer-invoices/{id}")
    @Timed
    public ResponseEntity<InsurerInvoiceDTO> getInsurerInvoice(@PathVariable Long id) {
        log.debug("REST request to get InsurerInvoice : {}", id);
        InsurerInvoiceDTO insurerInvoiceDTO = insurerInvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insurerInvoiceDTO));
    }

    /**
     * DELETE  /insurer-invoices/:id : delete the "id" insurerInvoice.
     *
     * @param id the id of the insurerInvoiceDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insurer-invoices/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsurerInvoice(@PathVariable Long id) {
        log.debug("REST request to delete InsurerInvoice : {}", id);
        insurerInvoiceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    

    /**
     * GET insurance invoices based on the invoice status
     * */
    @GetMapping("/insurer-invoices/findByInsurerInvoiceStatus/{insurerInvoiceStatus}")
    @Timed
    public List<InsurerInvoiceDTO> getAllInsurerInvoiceStatus(@PathVariable InsurerInvoiceStatus insurerInvoiceStatus) {
        log.debug("REST request to get a page of InsurerInvoices based on status",insurerInvoiceStatus);
        return insurerInvoiceService.findByInsurerInvoiceStatus(insurerInvoiceStatus);
       // return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insurerInvoiceDtoList));
        
    }
    
    /**
     * GET insurance invoices based on the invoice number
     */
    @GetMapping("/insurer-invoices/findByInvoiceNumber/{invoiceNumber}")
    @Timed
    public ResponseEntity<InsurerInvoiceDTO> getInsurerInvoiceNumber(@PathVariable Long invoiceNumber) {
        log.debug("REST request to get InsurerInvoice by invoice number : {}", invoiceNumber);
        InsurerInvoiceDTO insurerInvoiceDTO = insurerInvoiceService.findByInvoiceNumber(invoiceNumber);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insurerInvoiceDTO));
    }
    
    /**
     * GET insurance invoices date wise
     */
    @GetMapping("/insurer-invoices/findByInvoiceDate/{invoiceDate}")
    @Timed
    public List<InsurerInvoiceDTO> getAllByInsurerInvoiceDate(@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @PathVariable LocalDate invoiceDate)
    {
    	log.debug("REST request to get a page of InsurerInvoices based on date : {}",invoiceDate);
    	return insurerInvoiceService.findByInvoiceDate(invoiceDate);
    	
    	
    }
    
    /**
     * GET insurace invoices based on insurer
     */
    @GetMapping("/insurer-invoices/findByInsurer/{insurer}")
    @Timed
    public List<InsurerInvoiceDTO> getAllInsurerInvoicesByInsurer(@PathVariable Insurer insurer){
    	log.debug("REST request to get InsurerInvoices based on insurer : {}",insurer);
    	   return insurerInvoiceService.findByInsurer(insurer);
          
    }
    
   
    
}
