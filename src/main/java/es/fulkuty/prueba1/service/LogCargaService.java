package es.fulkuty.prueba1.service;

import es.fulkuty.prueba1.service.dto.LogCargaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing LogCarga.
 */
public interface LogCargaService {

    /**
     * Save a logCarga.
     *
     * @param logCargaDTO the entity to save
     * @return the persisted entity
     */
    LogCargaDTO save(LogCargaDTO logCargaDTO);

    /**
     * Get all the logCargas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LogCargaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" logCarga.
     *
     * @param id the id of the entity
     * @return the entity
     */
    LogCargaDTO findOne(Long id);

    /**
     * Delete the "id" logCarga.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
