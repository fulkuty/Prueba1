package es.fulkuty.prueba1.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.fulkuty.prueba1.service.LogCargaService;
import es.fulkuty.prueba1.web.rest.errors.BadRequestAlertException;
import es.fulkuty.prueba1.web.rest.util.HeaderUtil;
import es.fulkuty.prueba1.web.rest.util.PaginationUtil;
import es.fulkuty.prueba1.service.dto.LogCargaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LogCarga.
 */
@RestController
@RequestMapping("/api")
public class LogCargaResource {

    private final Logger log = LoggerFactory.getLogger(LogCargaResource.class);

    private static final String ENTITY_NAME = "logCarga";

    private final LogCargaService logCargaService;

    public LogCargaResource(LogCargaService logCargaService) {
        this.logCargaService = logCargaService;
    }

    /**
     * POST  /log-cargas : Create a new logCarga.
     *
     * @param logCargaDTO the logCargaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new logCargaDTO, or with status 400 (Bad Request) if the logCarga has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/log-cargas")
    @Timed
    public ResponseEntity<LogCargaDTO> createLogCarga(@Valid @RequestBody LogCargaDTO logCargaDTO) throws URISyntaxException {
        log.debug("REST request to save LogCarga : {}", logCargaDTO);
        if (logCargaDTO.getId() != null) {
            throw new BadRequestAlertException("A new logCarga cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LogCargaDTO result = logCargaService.save(logCargaDTO);
        return ResponseEntity.created(new URI("/api/log-cargas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /log-cargas : Updates an existing logCarga.
     *
     * @param logCargaDTO the logCargaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated logCargaDTO,
     * or with status 400 (Bad Request) if the logCargaDTO is not valid,
     * or with status 500 (Internal Server Error) if the logCargaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/log-cargas")
    @Timed
    public ResponseEntity<LogCargaDTO> updateLogCarga(@Valid @RequestBody LogCargaDTO logCargaDTO) throws URISyntaxException {
        log.debug("REST request to update LogCarga : {}", logCargaDTO);
        if (logCargaDTO.getId() == null) {
            return createLogCarga(logCargaDTO);
        }
        LogCargaDTO result = logCargaService.save(logCargaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, logCargaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /log-cargas : get all the logCargas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of logCargas in body
     */
    @GetMapping("/log-cargas")
    @Timed
    public ResponseEntity<List<LogCargaDTO>> getAllLogCargas(Pageable pageable) {
        log.debug("REST request to get a page of LogCargas");
        Page<LogCargaDTO> page = logCargaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/log-cargas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /log-cargas/:id : get the "id" logCarga.
     *
     * @param id the id of the logCargaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the logCargaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/log-cargas/{id}")
    @Timed
    public ResponseEntity<LogCargaDTO> getLogCarga(@PathVariable Long id) {
        log.debug("REST request to get LogCarga : {}", id);
        LogCargaDTO logCargaDTO = logCargaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(logCargaDTO));
    }

    /**
     * DELETE  /log-cargas/:id : delete the "id" logCarga.
     *
     * @param id the id of the logCargaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/log-cargas/{id}")
    @Timed
    public ResponseEntity<Void> deleteLogCarga(@PathVariable Long id) {
        log.debug("REST request to delete LogCarga : {}", id);
        logCargaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
