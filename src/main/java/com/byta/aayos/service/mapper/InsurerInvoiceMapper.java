package com.byta.aayos.service.mapper;

import com.byta.aayos.domain.*;
import com.byta.aayos.service.dto.InsurerInvoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InsurerInvoice and its DTO InsurerInvoiceDTO.
 */
@Mapper(componentModel = "spring", uses = {InsurerMapper.class, InsurerInvoiceStatusMapper.class, DepartmentMapper.class})
public interface InsurerInvoiceMapper extends EntityMapper<InsurerInvoiceDTO, InsurerInvoice> {

    @Mapping(source = "insurer.id", target = "insurerId")
    @Mapping(source = "insurerInvoiceStatus.id", target = "insurerInvoiceStatusId")
    @Mapping(source = "department.id", target = "departmentId")
    InsurerInvoiceDTO toDto(InsurerInvoice insurerInvoice);

    @Mapping(source = "insurerId", target = "insurer")
    @Mapping(source = "insurerInvoiceStatusId", target = "insurerInvoiceStatus")
    @Mapping(source = "departmentId", target = "department")
    InsurerInvoice toEntity(InsurerInvoiceDTO insurerInvoiceDTO);

    default InsurerInvoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        InsurerInvoice insurerInvoice = new InsurerInvoice();
        insurerInvoice.setId(id);
        return insurerInvoice;
    }
}
