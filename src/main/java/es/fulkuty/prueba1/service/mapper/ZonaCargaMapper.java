package es.fulkuty.prueba1.service.mapper;

import es.fulkuty.prueba1.domain.*;
import es.fulkuty.prueba1.service.dto.ZonaCargaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ZonaCarga and its DTO ZonaCargaDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ZonaCargaMapper extends EntityMapper<ZonaCargaDTO, ZonaCarga> {



    default ZonaCarga fromId(Long id) {
        if (id == null) {
            return null;
        }
        ZonaCarga zonaCarga = new ZonaCarga();
        zonaCarga.setId(id);
        return zonaCarga;
    }
}
