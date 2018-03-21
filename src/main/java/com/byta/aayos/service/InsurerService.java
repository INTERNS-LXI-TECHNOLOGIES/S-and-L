package com.byta.aayos.service;

import com.byta.aayos.service.dto.InsurerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Insurer.
 */
public interface InsurerService {

    /**
     * Save a insurer.
     *
     * @param insurerDTO the entity to save
     * @return the persisted entity
     */
    InsurerDTO save(InsurerDTO insurerDTO);

    /**
     * Get all the insurers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InsurerDTO> findAll(Pageable pageable);

    /**
     * Get the "id" insurer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InsurerDTO findOne(Long id);

    /**
     * Delete the "id" insurer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
