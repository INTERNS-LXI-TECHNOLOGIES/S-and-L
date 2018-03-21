package com.byta.aayos.service;

import com.byta.aayos.service.dto.InsuranceCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing InsuranceCategory.
 */
public interface InsuranceCategoryService {

    /**
     * Save a insuranceCategory.
     *
     * @param insuranceCategoryDTO the entity to save
     * @return the persisted entity
     */
    InsuranceCategoryDTO save(InsuranceCategoryDTO insuranceCategoryDTO);

    /**
     * Get all the insuranceCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InsuranceCategoryDTO> findAll(Pageable pageable);

    /**
     * Get the "id" insuranceCategory.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InsuranceCategoryDTO findOne(Long id);

    /**
     * Delete the "id" insuranceCategory.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
