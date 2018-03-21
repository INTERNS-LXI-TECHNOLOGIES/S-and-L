package com.byta.aayos.service.impl;

import com.byta.aayos.service.InsuranceCategoryService;
import com.byta.aayos.domain.InsuranceCategory;
import com.byta.aayos.repository.InsuranceCategoryRepository;
import com.byta.aayos.service.dto.InsuranceCategoryDTO;
import com.byta.aayos.service.mapper.InsuranceCategoryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing InsuranceCategory.
 */
@Service
@Transactional
public class InsuranceCategoryServiceImpl implements InsuranceCategoryService {

    private final Logger log = LoggerFactory.getLogger(InsuranceCategoryServiceImpl.class);

    private final InsuranceCategoryRepository insuranceCategoryRepository;

    private final InsuranceCategoryMapper insuranceCategoryMapper;

    public InsuranceCategoryServiceImpl(InsuranceCategoryRepository insuranceCategoryRepository, InsuranceCategoryMapper insuranceCategoryMapper) {
        this.insuranceCategoryRepository = insuranceCategoryRepository;
        this.insuranceCategoryMapper = insuranceCategoryMapper;
    }

    /**
     * Save a insuranceCategory.
     *
     * @param insuranceCategoryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InsuranceCategoryDTO save(InsuranceCategoryDTO insuranceCategoryDTO) {
        log.debug("Request to save InsuranceCategory : {}", insuranceCategoryDTO);
        InsuranceCategory insuranceCategory = insuranceCategoryMapper.toEntity(insuranceCategoryDTO);
        insuranceCategory = insuranceCategoryRepository.save(insuranceCategory);
        return insuranceCategoryMapper.toDto(insuranceCategory);
    }

    /**
     * Get all the insuranceCategories.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsuranceCategoryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InsuranceCategories");
        return insuranceCategoryRepository.findAll(pageable)
            .map(insuranceCategoryMapper::toDto);
    }

    /**
     * Get one insuranceCategory by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InsuranceCategoryDTO findOne(Long id) {
        log.debug("Request to get InsuranceCategory : {}", id);
        InsuranceCategory insuranceCategory = insuranceCategoryRepository.findOneWithEagerRelationships(id);
        return insuranceCategoryMapper.toDto(insuranceCategory);
    }

    /**
     * Delete the insuranceCategory by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InsuranceCategory : {}", id);
        insuranceCategoryRepository.delete(id);
    }
}
