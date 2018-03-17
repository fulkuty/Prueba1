package es.fulkuty.prueba1.service.mapper;

import es.fulkuty.prueba1.domain.*;
import es.fulkuty.prueba1.service.dto.ProcesoCargaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity ProcesoCarga and its DTO ProcesoCargaDTO.
 */
@Mapper(componentModel = "spring", uses = {ZonaCargaMapper.class})
public interface ProcesoCargaMapper extends EntityMapper<ProcesoCargaDTO, ProcesoCarga> {

    @Mapping(source = "zona.id", target = "zonaId")
    ProcesoCargaDTO toDto(ProcesoCarga procesoCarga);

    @Mapping(source = "zonaId", target = "zona")
    @Mapping(target = "logs", ignore = true)
    ProcesoCarga toEntity(ProcesoCargaDTO procesoCargaDTO);

    default ProcesoCarga fromId(Long id) {
        if (id == null) {
            return null;
        }
        ProcesoCarga procesoCarga = new ProcesoCarga();
        procesoCarga.setId(id);
        return procesoCarga;
    }
}
