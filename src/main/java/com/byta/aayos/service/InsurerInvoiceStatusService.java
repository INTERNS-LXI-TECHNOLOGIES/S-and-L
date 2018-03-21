package com.byta.aayos.service;

import com.byta.aayos.service.dto.InsurerInvoiceStatusDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing InsurerInvoiceStatus.
 */
public interface InsurerInvoiceStatusService {

    /**
     * Save a insurerInvoiceStatus.
     *
     * @param insurerInvoiceStatusDTO the entity to save
     * @return the persisted entity
     */
    InsurerInvoiceStatusDTO save(InsurerInvoiceStatusDTO insurerInvoiceStatusDTO);

    /**
     * Get all the insurerInvoiceStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InsurerInvoiceStatusDTO> findAll(Pageable pageable);

    /**
     * Get the "id" insurerInvoiceStatus.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InsurerInvoiceStatusDTO findOne(Long id);

    /**
     * Delete the "id" insurerInvoiceStatus.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
