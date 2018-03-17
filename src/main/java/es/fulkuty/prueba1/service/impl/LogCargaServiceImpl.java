package es.fulkuty.prueba1.service.impl;

import es.fulkuty.prueba1.service.LogCargaService;
import es.fulkuty.prueba1.domain.LogCarga;
import es.fulkuty.prueba1.repository.LogCargaRepository;
import es.fulkuty.prueba1.service.dto.LogCargaDTO;
import es.fulkuty.prueba1.service.mapper.LogCargaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing LogCarga.
 */
@Service
@Transactional
public class LogCargaServiceImpl implements LogCargaService {

    private final Logger log = LoggerFactory.getLogger(LogCargaServiceImpl.class);

    private final LogCargaRepository logCargaRepository;

    private final LogCargaMapper logCargaMapper;

    public LogCargaServiceImpl(LogCargaRepository logCargaRepository, LogCargaMapper logCargaMapper) {
        this.logCargaRepository = logCargaRepository;
        this.logCargaMapper = logCargaMapper;
    }

    /**
     * Save a logCarga.
     *
     * @param logCargaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public LogCargaDTO save(LogCargaDTO logCargaDTO) {
        log.debug("Request to save LogCarga : {}", logCargaDTO);
        LogCarga logCarga = logCargaMapper.toEntity(logCargaDTO);
        logCarga = logCargaRepository.save(logCarga);
        return logCargaMapper.toDto(logCarga);
    }

    /**
     * Get all the logCargas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LogCargaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all LogCargas");
        return logCargaRepository.findAll(pageable)
            .map(logCargaMapper::toDto);
    }

    /**
     * Get one logCarga by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LogCargaDTO findOne(Long id) {
        log.debug("Request to get LogCarga : {}", id);
        LogCarga logCarga = logCargaRepository.findOne(id);
        return logCargaMapper.toDto(logCarga);
    }

    /**
     * Delete the logCarga by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LogCarga : {}", id);
        logCargaRepository.delete(id);
    }
}
