package com.byta.aayos.service.mapper;

import com.byta.aayos.domain.*;
import com.byta.aayos.service.dto.RelationshipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Relationship and its DTO RelationshipDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RelationshipMapper extends EntityMapper<RelationshipDTO, Relationship> {



    default Relationship fromId(Long id) {
        if (id == null) {
            return null;
        }
        Relationship relationship = new Relationship();
        relationship.setId(id);
        return relationship;
    }
}
