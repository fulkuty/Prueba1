package es.fulkuty.prueba1.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.fulkuty.prueba1.service.ProcesoCargaService;
import es.fulkuty.prueba1.web.rest.errors.BadRequestAlertException;
import es.fulkuty.prueba1.web.rest.util.HeaderUtil;
import es.fulkuty.prueba1.web.rest.util.PaginationUtil;
import es.fulkuty.prueba1.service.dto.ProcesoCargaDTO;
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
 * REST controller for managing ProcesoCarga.
 */
@RestController
@RequestMapping("/api")
public class ProcesoCargaResource {

    private final Logger log = LoggerFactory.getLogger(ProcesoCargaResource.class);

    private static final String ENTITY_NAME = "procesoCarga";

    private final ProcesoCargaService procesoCargaService;

    public ProcesoCargaResource(ProcesoCargaService procesoCargaService) {
        this.procesoCargaService = procesoCargaService;
    }

    /**
     * POST  /proceso-cargas : Create a new procesoCarga.
     *
     * @param procesoCargaDTO the procesoCargaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new procesoCargaDTO, or with status 400 (Bad Request) if the procesoCarga has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/proceso-cargas")
    @Timed
    public ResponseEntity<ProcesoCargaDTO> createProcesoCarga(@Valid @RequestBody ProcesoCargaDTO procesoCargaDTO) throws URISyntaxException {
        log.debug("REST request to save ProcesoCarga : {}", procesoCargaDTO);
        if (procesoCargaDTO.getId() != null) {
            throw new BadRequestAlertException("A new procesoCarga cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProcesoCargaDTO result = procesoCargaService.save(procesoCargaDTO);
        return ResponseEntity.created(new URI("/api/proceso-cargas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proceso-cargas : Updates an existing procesoCarga.
     *
     * @param procesoCargaDTO the procesoCargaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated procesoCargaDTO,
     * or with status 400 (Bad Request) if the procesoCargaDTO is not valid,
     * or with status 500 (Internal Server Error) if the procesoCargaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/proceso-cargas")
    @Timed
    public ResponseEntity<ProcesoCargaDTO> updateProcesoCarga(@Valid @RequestBody ProcesoCargaDTO procesoCargaDTO) throws URISyntaxException {
        log.debug("REST request to update ProcesoCarga : {}", procesoCargaDTO);
        if (procesoCargaDTO.getId() == null) {
            return createProcesoCarga(procesoCargaDTO);
        }
        ProcesoCargaDTO result = procesoCargaService.save(procesoCargaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, procesoCargaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proceso-cargas : get all the procesoCargas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of procesoCargas in body
     */
    @GetMapping("/proceso-cargas")
    @Timed
    public ResponseEntity<List<ProcesoCargaDTO>> getAllProcesoCargas(Pageable pageable) {
        log.debug("REST request to get a page of ProcesoCargas");
        Page<ProcesoCargaDTO> page = procesoCargaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/proceso-cargas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /proceso-cargas/:id : get the "id" procesoCarga.
     *
     * @param id the id of the procesoCargaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the procesoCargaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/proceso-cargas/{id}")
    @Timed
    public ResponseEntity<ProcesoCargaDTO> getProcesoCarga(@PathVariable Long id) {
        log.debug("REST request to get ProcesoCarga : {}", id);
        ProcesoCargaDTO procesoCargaDTO = procesoCargaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(procesoCargaDTO));
    }

    /**
     * DELETE  /proceso-cargas/:id : delete the "id" procesoCarga.
     *
     * @param id the id of the procesoCargaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/proceso-cargas/{id}")
    @Timed
    public ResponseEntity<Void> deleteProcesoCarga(@PathVariable Long id) {
        log.debug("REST request to delete ProcesoCarga : {}", id);
        procesoCargaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
