package com.byta.aayos.service;

import com.byta.aayos.service.dto.RelationshipDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Relationship.
 */
public interface RelationshipService {

    /**
     * Save a relationship.
     *
     * @param relationshipDTO the entity to save
     * @return the persisted entity
     */
    RelationshipDTO save(RelationshipDTO relationshipDTO);

    /**
     * Get all the relationships.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RelationshipDTO> findAll(Pageable pageable);

    /**
     * Get the "id" relationship.
     *
     * @param id the id of the entity
     * @return the entity
     */
    RelationshipDTO findOne(Long id);

    /**
     * Delete the "id" relationship.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
