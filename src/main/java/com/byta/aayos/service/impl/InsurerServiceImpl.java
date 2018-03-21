package com.byta.aayos.service.impl;

import com.byta.aayos.service.InsurerService;
import com.byta.aayos.domain.Insurer;
import com.byta.aayos.repository.InsurerRepository;
import com.byta.aayos.service.dto.InsurerDTO;
import com.byta.aayos.service.mapper.InsurerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Insurer.
 */
@Service
@Transactional
public class InsurerServiceImpl implements InsurerService {

    private final Logger log = LoggerFactory.getLogger(InsurerServiceImpl.class);

    private final InsurerRepository insurerRepository;

    private final InsurerMapper insurerMapper;

    public InsurerServiceImpl(InsurerRepository insurerRepository, InsurerMapper insurerMapper) {
        this.insurerRepository = insurerRepository;
        this.insurerMapper = insurerMapper;
    }

    /**
     * Save a insurer.
     *
     * @param insurerDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InsurerDTO save(InsurerDTO insurerDTO) {
        log.debug("Request to save Insurer : {}", insurerDTO);
        Insurer insurer = insurerMapper.toEntity(insurerDTO);
        insurer = insurerRepository.save(insurer);
        return insurerMapper.toDto(insurer);
    }

    /**
     * Get all the insurers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsurerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Insurers");
        return insurerRepository.findAll(pageable)
            .map(insurerMapper::toDto);
    }

    /**
     * Get one insurer by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InsurerDTO findOne(Long id) {
        log.debug("Request to get Insurer : {}", id);
        Insurer insurer = insurerRepository.findOne(id);
        return insurerMapper.toDto(insurer);
    }

    /**
     * Delete the insurer by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Insurer : {}", id);
        insurerRepository.delete(id);
    }
}
