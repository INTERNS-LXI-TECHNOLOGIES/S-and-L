package com.byta.aayos.service.mapper;

import com.byta.aayos.domain.*;
import com.byta.aayos.service.dto.CoveredActivityDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity CoveredActivity and its DTO CoveredActivityDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CoveredActivityMapper extends EntityMapper<CoveredActivityDTO, CoveredActivity> {



    default CoveredActivity fromId(Long id) {
        if (id == null) {
            return null;
        }
        CoveredActivity coveredActivity = new CoveredActivity();
        coveredActivity.setId(id);
        return coveredActivity;
    }
}
