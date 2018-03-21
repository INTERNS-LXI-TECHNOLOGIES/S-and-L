package com.byta.aayos.service.impl;

import com.byta.aayos.service.InsurerInvoiceService;
import com.byta.aayos.domain.Insurer;
import com.byta.aayos.domain.InsurerInvoice;
import com.byta.aayos.domain.InsurerInvoiceStatus;
import com.byta.aayos.repository.InsurerInvoiceRepository;
import com.byta.aayos.service.dto.InsurerInvoiceDTO;
import com.byta.aayos.service.mapper.InsurerInvoiceMapper;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing InsurerInvoice.
 */
@Service
@Transactional
public class InsurerInvoiceServiceImpl implements InsurerInvoiceService {

    private final Logger log = LoggerFactory.getLogger(InsurerInvoiceServiceImpl.class);

    private final InsurerInvoiceRepository insurerInvoiceRepository;

    private final InsurerInvoiceMapper insurerInvoiceMapper;

    public InsurerInvoiceServiceImpl(InsurerInvoiceRepository insurerInvoiceRepository, InsurerInvoiceMapper insurerInvoiceMapper) {
        this.insurerInvoiceRepository = insurerInvoiceRepository;
        this.insurerInvoiceMapper = insurerInvoiceMapper;
    }

    /**
     * Save a insurerInvoice.
     *
     * @param insurerInvoiceDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InsurerInvoiceDTO save(InsurerInvoiceDTO insurerInvoiceDTO) {
        log.debug("Request to save InsurerInvoice : {}", insurerInvoiceDTO);
        InsurerInvoice insurerInvoice = insurerInvoiceMapper.toEntity(insurerInvoiceDTO);
        insurerInvoice = insurerInvoiceRepository.save(insurerInvoice);
        return insurerInvoiceMapper.toDto(insurerInvoice);
    }

    /**
     * Get all the insurerInvoices.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InsurerInvoiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all InsurerInvoices");
        return insurerInvoiceRepository.findAll(pageable)
            .map(insurerInvoiceMapper::toDto);
    }

    /**
     * Get one insurerInvoice by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InsurerInvoiceDTO findOne(Long id) {
        log.debug("Request to get InsurerInvoice : {}", id);
        InsurerInvoice insurerInvoice = insurerInvoiceRepository.findOne(id);
        return insurerInvoiceMapper.toDto(insurerInvoice);
    }

    /**
     * Delete the insurerInvoice by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete InsurerInvoice : {}", id);
        insurerInvoiceRepository.delete(id);
    }
    
    @Override
    public List<InsurerInvoiceDTO> findByInsurerInvoiceStatus(InsurerInvoiceStatus insurerInvoiceStatus){
    	log.debug("Request to get insurerInvoices by status");
    	 
    	return insurerInvoiceRepository.findByInsurerInvoiceStatus(insurerInvoiceStatus)
    			.stream().map(insurerInvoiceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    	
    }
    
    @Override
    public InsurerInvoiceDTO findByInvoiceNumber(Long invoiceNumber){
    	 log.debug("Request to get InsurerInvoice based on invoice number: {}",invoiceNumber );
         InsurerInvoice insurerInvoice = insurerInvoiceRepository.findByInvoiceNumber(invoiceNumber);
         return insurerInvoiceMapper.toDto(insurerInvoice);
   
    }
    
    public List<InsurerInvoiceDTO> findByInvoiceDate(LocalDate invoiceDate){
    	 log.debug("Request to get insurerInvoices by date");
    	 return insurerInvoiceRepository.findByInvoiceDate(invoiceDate).stream().map(insurerInvoiceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
  
    }
    
  public List<InsurerInvoiceDTO> findByInsurer(Insurer insurer){
    	log.debug("Request to get insurerinvoices by insurers");
    	  return insurerInvoiceRepository.findByInsurer(insurer)
    			  .stream().map(insurerInvoiceMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    	  
       		
  }
   
}
