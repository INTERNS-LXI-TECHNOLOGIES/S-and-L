package com.byta.aayos.repository;

import com.byta.aayos.domain.Insurer;
import com.byta.aayos.domain.InsurerInvoice;
import com.byta.aayos.domain.InsurerInvoiceStatus;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the InsurerInvoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsurerInvoiceRepository extends JpaRepository<InsurerInvoice, Long> {

	List<InsurerInvoice> findByInsurerInvoiceStatus(InsurerInvoiceStatus insurerInvoiceStatus);

	//InsurerInvoice findByInvoiceNumber(Long invoiceNumber);

	InsurerInvoice findByInvoiceNumber(Long invoiceNumber);

	List<InsurerInvoice> findByInvoiceDate(LocalDate invoiceDate);

	List<InsurerInvoice> findByInsurer(Insurer insurer);

}
