package com.byta.aayos.service;

import com.byta.aayos.service.dto.CoveredActivityDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing CoveredActivity.
 */
public interface CoveredActivityService {

    /**
     * Save a coveredActivity.
     *
     * @param coveredActivityDTO the entity to save
     * @return the persisted entity
     */
    CoveredActivityDTO save(CoveredActivityDTO coveredActivityDTO);

    /**
     * Get all the coveredActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<CoveredActivityDTO> findAll(Pageable pageable);

    /**
     * Get the "id" coveredActivity.
     *
     * @param id the id of the entity
     * @return the entity
     */
    CoveredActivityDTO findOne(Long id);

    /**
     * Delete the "id" coveredActivity.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
