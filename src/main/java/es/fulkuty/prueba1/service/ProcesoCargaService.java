package es.fulkuty.prueba1.service;

import es.fulkuty.prueba1.service.dto.ProcesoCargaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ProcesoCarga.
 */
public interface ProcesoCargaService {

    /**
     * Save a procesoCarga.
     *
     * @param procesoCargaDTO the entity to save
     * @return the persisted entity
     */
    ProcesoCargaDTO save(ProcesoCargaDTO procesoCargaDTO);

    /**
     * Get all the procesoCargas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ProcesoCargaDTO> findAll(Pageable pageable);

    /**
     * Get the "id" procesoCarga.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ProcesoCargaDTO findOne(Long id);

    /**
     * Delete the "id" procesoCarga.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
