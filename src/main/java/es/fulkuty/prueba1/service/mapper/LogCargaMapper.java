package es.fulkuty.prueba1.service.mapper;

import es.fulkuty.prueba1.domain.*;
import es.fulkuty.prueba1.service.dto.LogCargaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity LogCarga and its DTO LogCargaDTO.
 */
@Mapper(componentModel = "spring", uses = {ZonaCargaMapper.class, ProcesoCargaMapper.class})
public interface LogCargaMapper extends EntityMapper<LogCargaDTO, LogCarga> {

    @Mapping(source = "zona.id", target = "zonaId")
    @Mapping(source = "proceso.id", target = "procesoId")
    LogCargaDTO toDto(LogCarga logCarga);

    @Mapping(source = "zonaId", target = "zona")
    @Mapping(source = "procesoId", target = "proceso")
    LogCarga toEntity(LogCargaDTO logCargaDTO);

    default LogCarga fromId(Long id) {
        if (id == null) {
            return null;
        }
        LogCarga logCarga = new LogCarga();
        logCarga.setId(id);
        return logCarga;
    }
}
