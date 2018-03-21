package com.byta.aayos.service.impl;

import com.byta.aayos.service.InsurerInvoiceStatusService;
import com.byta.aayos.domain.InsurerInvoiceStatus;
import com.byta.aayos.repository.InsurerInvoiceStatusRepository;
import com.byta.aayos.service.dto.InsurerInvoiceStatusDTO;
import com.byta.aayos.service.mapper.InsurerInvoiceStatusMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing InsurerInvoiceStatus.
 */
@Service
@Transactional
public class InsurerInvoiceStatusServiceImpl implements InsurerInvoiceStatusService {

    private final Logger log = LoggerFactory.getLogger(InsurerInvoiceStatusServiceImpl.class);

    private final InsurerInvoiceStatusRepository insurerInvoiceStatusRepository;

    private final InsurerInvoiceStatusMapper insurerInvoiceStatusMapper;

    public InsurerInvoiceStatusServiceImpl(InsurerInvoiceStatusRepository insurerInvoiceStatusRepository, InsurerInvoiceStatusMapper insurerInvoiceStatusMapper) {
        this.insurerInvoiceStatusRepository = insurerInvoiceStatusRepository;
        this.insurerInvoiceStatusMapper = insurerInvoiceStatusMapper;
    }

    /**
     * Save a insurerInvoiceStatus.
     *
     * @param insurerInvoiceStatusDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InsurerInvoiceStatusDTO save(InsurerInvoiceStatusDTO insurerInvoiceStatusDTO) {
        log.debug("Request to save InsurerInvoiceStatus : {}", insurerInvoiceStatusDTO);
        InsurerInvoiceStatus insurerInvoiceStatus = insurerInvoiceStatusMapper.toEntity(insurerInvoiceStatusDTO);
        insurerInvoiceStatus = insurerInvoiceStatusRepository.save(insurerInvoiceStatus);
        return insurerInvoiceStatusMapper.toDto(insurerInvoiceStatus);
    }

    /**
     * Get all the insurerInvoiceStatuses.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsurerInvoiceStatusDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InsurerInvoiceStatuses");
        return insurerInvoiceStatusRepository.findAll(pageable)
            .map(insurerInvoiceStatusMapper::toDto);
    }

    /**
     * Get one insurerInvoiceStatus by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InsurerInvoiceStatusDTO findOne(Long id) {
        log.debug("Request to get InsurerInvoiceStatus : {}", id);
        InsurerInvoiceStatus insurerInvoiceStatus = insurerInvoiceStatusRepository.findOne(id);
        return insurerInvoiceStatusMapper.toDto(insurerInvoiceStatus);
    }

    /**
     * Delete the insurerInvoiceStatus by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InsurerInvoiceStatus : {}", id);
        insurerInvoiceStatusRepository.delete(id);
    }
}
