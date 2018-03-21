package com.byta.aayos.service.impl;

import com.byta.aayos.service.CoveredActivityService;
import com.byta.aayos.domain.CoveredActivity;
import com.byta.aayos.repository.CoveredActivityRepository;
import com.byta.aayos.service.dto.CoveredActivityDTO;
import com.byta.aayos.service.mapper.CoveredActivityMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing CoveredActivity.
 */
@Service
@Transactional
public class CoveredActivityServiceImpl implements CoveredActivityService {

    private final Logger log = LoggerFactory.getLogger(CoveredActivityServiceImpl.class);

    private final CoveredActivityRepository coveredActivityRepository;

    private final CoveredActivityMapper coveredActivityMapper;

    public CoveredActivityServiceImpl(CoveredActivityRepository coveredActivityRepository, CoveredActivityMapper coveredActivityMapper) {
        this.coveredActivityRepository = coveredActivityRepository;
        this.coveredActivityMapper = coveredActivityMapper;
    }

    /**
     * Save a coveredActivity.
     *
     * @param coveredActivityDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CoveredActivityDTO save(CoveredActivityDTO coveredActivityDTO) {
        log.debug("Request to save CoveredActivity : {}", coveredActivityDTO);
        CoveredActivity coveredActivity = coveredActivityMapper.toEntity(coveredActivityDTO);
        coveredActivity = coveredActivityRepository.save(coveredActivity);
        return coveredActivityMapper.toDto(coveredActivity);
    }

    /**
     * Get all the coveredActivities.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CoveredActivityDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CoveredActivities");
        return coveredActivityRepository.findAll(pageable)
            .map(coveredActivityMapper::toDto);
    }

    /**
     * Get one coveredActivity by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CoveredActivityDTO findOne(Long id) {
        log.debug("Request to get CoveredActivity : {}", id);
        CoveredActivity coveredActivity = coveredActivityRepository.findOne(id);
        return coveredActivityMapper.toDto(coveredActivity);
    }

    /**
     * Delete the coveredActivity by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CoveredActivity : {}", id);
        coveredActivityRepository.delete(id);
    }
}
