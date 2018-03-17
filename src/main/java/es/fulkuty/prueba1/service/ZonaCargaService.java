package es.fulkuty.prueba1.service;

import es.fulkuty.prueba1.service.dto.ZonaCargaDTO;
import java.util.List;

/**
 * Service Interface for managing ZonaCarga.
 */
public interface ZonaCargaService {

    /**
     * Save a zonaCarga.
     *
     * @param zonaCargaDTO the entity to save
     * @return the persisted entity
     */
    ZonaCargaDTO save(ZonaCargaDTO zonaCargaDTO);

    /**
     * Get all the zonaCargas.
     *
     * @return the list of entities
     */
    List<ZonaCargaDTO> findAll();

    /**
     * Get the "id" zonaCarga.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ZonaCargaDTO findOne(Long id);

    /**
     * Delete the "id" zonaCarga.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
