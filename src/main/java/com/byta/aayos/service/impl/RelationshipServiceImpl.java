package com.byta.aayos.service.impl;

import com.byta.aayos.service.RelationshipService;
import com.byta.aayos.domain.Relationship;
import com.byta.aayos.repository.RelationshipRepository;
import com.byta.aayos.service.dto.RelationshipDTO;
import com.byta.aayos.service.mapper.RelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Relationship.
 */
@Service
@Transactional
public class RelationshipServiceImpl implements RelationshipService {

    private final Logger log = LoggerFactory.getLogger(RelationshipServiceImpl.class);

    private final RelationshipRepository relationshipRepository;

    private final RelationshipMapper relationshipMapper;

    public RelationshipServiceImpl(RelationshipRepository relationshipRepository, RelationshipMapper relationshipMapper) {
        this.relationshipRepository = relationshipRepository;
        this.relationshipMapper = relationshipMapper;
    }

    /**
     * Save a relationship.
     *
     * @param relationshipDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RelationshipDTO save(RelationshipDTO relationshipDTO) {
        log.debug("Request to save Relationship : {}", relationshipDTO);
        Relationship relationship = relationshipMapper.toEntity(relationshipDTO);
        relationship = relationshipRepository.save(relationship);
        return relationshipMapper.toDto(relationship);
    }

    /**
     * Get all the relationships.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RelationshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Relationships");
        return relationshipRepository.findAll(pageable)
            .map(relationshipMapper::toDto);
    }

    /**
     * Get one relationship by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RelationshipDTO findOne(Long id) {
        log.debug("Request to get Relationship : {}", id);
        Relationship relationship = relationshipRepository.findOne(id);
        return relationshipMapper.toDto(relationship);
    }

    /**
     * Delete the relationship by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Relationship : {}", id);
        relationshipRepository.delete(id);
    }
}
