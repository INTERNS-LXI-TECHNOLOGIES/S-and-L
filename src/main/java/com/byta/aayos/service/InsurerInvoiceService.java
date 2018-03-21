package com.byta.aayos.service;

import com.byta.aayos.domain.Insurer;
import com.byta.aayos.domain.InsurerInvoiceStatus;
import com.byta.aayos.service.dto.InsurerInvoiceDTO;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing InsurerInvoice.
 */
public interface InsurerInvoiceService {

    /**
     * Save a insurerInvoice.
     *
     * @param insurerInvoiceDTO the entity to save
     * @return the persisted entity
     */
    InsurerInvoiceDTO save(InsurerInvoiceDTO insurerInvoiceDTO);

    /**
     * Get all the insurerInvoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InsurerInvoiceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" insurerInvoice.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InsurerInvoiceDTO findOne(Long id);

    /**
     * Delete the "id" insurerInvoice.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
    
	List<InsurerInvoiceDTO> findByInsurerInvoiceStatus(InsurerInvoiceStatus insurerInvoiceStatus);

	List<InsurerInvoiceDTO> findByInvoiceDate(LocalDate invoiceDate);

	List<InsurerInvoiceDTO> findByInsurer(Insurer insurer);

	InsurerInvoiceDTO findByInvoiceNumber(Long invoiceNumber);

	//List<InsurerInvoiceDTO> findByInsurerInvoiceStatus(String insurerInvoiceStatus);

    
}
