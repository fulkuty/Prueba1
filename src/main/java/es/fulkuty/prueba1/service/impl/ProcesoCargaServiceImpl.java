package es.fulkuty.prueba1.service.impl;

import es.fulkuty.prueba1.service.ProcesoCargaService;
import es.fulkuty.prueba1.domain.ProcesoCarga;
import es.fulkuty.prueba1.repository.ProcesoCargaRepository;
import es.fulkuty.prueba1.service.dto.ProcesoCargaDTO;
import es.fulkuty.prueba1.service.mapper.ProcesoCargaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ProcesoCarga.
 */
@Service
@Transactional
public class ProcesoCargaServiceImpl implements ProcesoCargaService {

    private final Logger log = LoggerFactory.getLogger(ProcesoCargaServiceImpl.class);

    private final ProcesoCargaRepository procesoCargaRepository;

    private final ProcesoCargaMapper procesoCargaMapper;

    public ProcesoCargaServiceImpl(ProcesoCargaRepository procesoCargaRepository, ProcesoCargaMapper procesoCargaMapper) {
        this.procesoCargaRepository = procesoCargaRepository;
        this.procesoCargaMapper = procesoCargaMapper;
    }

    /**
     * Save a procesoCarga.
     *
     * @param procesoCargaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProcesoCargaDTO save(ProcesoCargaDTO procesoCargaDTO) {
        log.debug("Request to save ProcesoCarga : {}", procesoCargaDTO);
        ProcesoCarga procesoCarga = procesoCargaMapper.toEntity(procesoCargaDTO);
        procesoCarga = procesoCargaRepository.save(procesoCarga);
        return procesoCargaMapper.toDto(procesoCarga);
    }

    /**
     * Get all the procesoCargas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProcesoCargaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProcesoCargas");
        return procesoCargaRepository.findAll(pageable)
            .map(procesoCargaMapper::toDto);
    }

    /**
     * Get one procesoCarga by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ProcesoCargaDTO findOne(Long id) {
        log.debug("Request to get ProcesoCarga : {}", id);
        ProcesoCarga procesoCarga = procesoCargaRepository.findOne(id);
        return procesoCargaMapper.toDto(procesoCarga);
    }

    /**
     * Delete the procesoCarga by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProcesoCarga : {}", id);
        procesoCargaRepository.delete(id);
    }
}
