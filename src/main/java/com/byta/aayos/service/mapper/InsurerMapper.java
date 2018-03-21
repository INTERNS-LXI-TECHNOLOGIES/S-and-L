package com.byta.aayos.service.mapper;

import com.byta.aayos.domain.*;
import com.byta.aayos.service.dto.InsurerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Insurer and its DTO InsurerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface InsurerMapper extends EntityMapper<InsurerDTO, Insurer> {



    default Insurer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Insurer insurer = new Insurer();
        insurer.setId(id);
        return insurer;
    }
}
