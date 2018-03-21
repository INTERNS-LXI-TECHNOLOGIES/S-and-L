package com.byta.aayos.repository;

import com.byta.aayos.domain.InsurerInvoiceStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InsurerInvoiceStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsurerInvoiceStatusRepository extends JpaRepository<InsurerInvoiceStatus, Long> {

}
