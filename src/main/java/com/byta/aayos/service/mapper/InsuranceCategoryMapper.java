package com.byta.aayos.service.mapper;

import com.byta.aayos.domain.*;
import com.byta.aayos.service.dto.InsuranceCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity InsuranceCategory and its DTO InsuranceCategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {CoveredActivityMapper.class})
public interface InsuranceCategoryMapper extends EntityMapper<InsuranceCategoryDTO, InsuranceCategory> {



    default InsuranceCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        InsuranceCategory insuranceCategory = new InsuranceCategory();
        insuranceCategory.setId(id);
        return insuranceCategory;
    }
}
