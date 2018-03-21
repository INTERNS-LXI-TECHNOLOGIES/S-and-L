package com.byta.aayos.service.mapper;

import com.byta.aayos.domain.*;
import com.byta.aayos.service.dto.HealthInsuranceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HealthInsurance and its DTO HealthInsuranceDTO.
 */
@Mapper(componentModel = "spring", uses = {InsuranceCategoryMapper.class, RelationshipMapper.class, InsurerMapper.class})
public interface HealthInsuranceMapper extends EntityMapper<HealthInsuranceDTO, HealthInsurance> {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "status.id", target = "statusId")
    @Mapping(source = "insurer.id", target = "insurerId")
    HealthInsuranceDTO toDto(HealthInsurance healthInsurance);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(source = "statusId", target = "status")
    @Mapping(source = "insurerId", target = "insurer")
    HealthInsurance toEntity(HealthInsuranceDTO healthInsuranceDTO);

    default HealthInsurance fromId(Long id) {
        if (id == null) {
            return null;
        }
        HealthInsurance healthInsurance = new HealthInsurance();
        healthInsurance.setId(id);
        return healthInsurance;
    }
}
