package com.byta.aayos.service.mapper;

import com.byta.aayos.domain.*;
import com.byta.aayos.service.dto.InsurerInvoiceStatusDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InsurerInvoiceStatus and its DTO InsurerInvoiceStatusDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InsurerInvoiceStatusMapper extends EntityMapper<InsurerInvoiceStatusDTO, InsurerInvoiceStatus> {



    default InsurerInvoiceStatus fromId(Long id) {
        if (id == null) {
            return null;
        }
        InsurerInvoiceStatus insurerInvoiceStatus = new InsurerInvoiceStatus();
        insurerInvoiceStatus.setId(id);
        return insurerInvoiceStatus;
    }
}
