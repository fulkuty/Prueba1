package es.fulkuty.prueba1.service.impl;

import es.fulkuty.prueba1.service.ZonaCargaService;
import es.fulkuty.prueba1.domain.ZonaCarga;
import es.fulkuty.prueba1.repository.ZonaCargaRepository;
import es.fulkuty.prueba1.service.dto.ZonaCargaDTO;
import es.fulkuty.prueba1.service.mapper.ZonaCargaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ZonaCarga.
 */
@Service
@Transactional
public class ZonaCargaServiceImpl implements ZonaCargaService {

    private final Logger log = LoggerFactory.getLogger(ZonaCargaServiceImpl.class);

    private final ZonaCargaRepository zonaCargaRepository;

    private final ZonaCargaMapper zonaCargaMapper;

    public ZonaCargaServiceImpl(ZonaCargaRepository zonaCargaRepository, ZonaCargaMapper zonaCargaMapper) {
        this.zonaCargaRepository = zonaCargaRepository;
        this.zonaCargaMapper = zonaCargaMapper;
    }

    /**
     * Save a zonaCarga.
     *
     * @param zonaCargaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ZonaCargaDTO save(ZonaCargaDTO zonaCargaDTO) {
        log.debug("Request to save ZonaCarga : {}", zonaCargaDTO);
        ZonaCarga zonaCarga = zonaCargaMapper.toEntity(zonaCargaDTO);
        zonaCarga = zonaCargaRepository.save(zonaCarga);
        return zonaCargaMapper.toDto(zonaCarga);
    }

    /**
     * Get all the zonaCargas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ZonaCargaDTO> findAll() {
        log.debug("Request to get all ZonaCargas");
        return zonaCargaRepository.findAll().stream()
            .map(zonaCargaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one zonaCarga by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ZonaCargaDTO findOne(Long id) {
        log.debug("Request to get ZonaCarga : {}", id);
        ZonaCarga zonaCarga = zonaCargaRepository.findOne(id);
        return zonaCargaMapper.toDto(zonaCarga);
    }

    /**
     * Delete the zonaCarga by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ZonaCarga : {}", id);
        zonaCargaRepository.delete(id);
    }
}
