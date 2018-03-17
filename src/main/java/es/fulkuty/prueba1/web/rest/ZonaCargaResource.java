package es.fulkuty.prueba1.web.rest;

import com.codahale.metrics.annotation.Timed;
import es.fulkuty.prueba1.service.ZonaCargaService;
import es.fulkuty.prueba1.web.rest.errors.BadRequestAlertException;
import es.fulkuty.prueba1.web.rest.util.HeaderUtil;
import es.fulkuty.prueba1.service.dto.ZonaCargaDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ZonaCarga.
 */
@RestController
@RequestMapping("/api")
public class ZonaCargaResource {

    private final Logger log = LoggerFactory.getLogger(ZonaCargaResource.class);

    private static final String ENTITY_NAME = "zonaCarga";

    private final ZonaCargaService zonaCargaService;

    public ZonaCargaResource(ZonaCargaService zonaCargaService) {
        this.zonaCargaService = zonaCargaService;
    }

    /**
     * POST  /zona-cargas : Create a new zonaCarga.
     *
     * @param zonaCargaDTO the zonaCargaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new zonaCargaDTO, or with status 400 (Bad Request) if the zonaCarga has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/zona-cargas")
    @Timed
    public ResponseEntity<ZonaCargaDTO> createZonaCarga(@Valid @RequestBody ZonaCargaDTO zonaCargaDTO) throws URISyntaxException {
        log.debug("REST request to save ZonaCarga : {}", zonaCargaDTO);
        if (zonaCargaDTO.getId() != null) {
            throw new BadRequestAlertException("A new zonaCarga cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZonaCargaDTO result = zonaCargaService.save(zonaCargaDTO);
        return ResponseEntity.created(new URI("/api/zona-cargas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /zona-cargas : Updates an existing zonaCarga.
     *
     * @param zonaCargaDTO the zonaCargaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated zonaCargaDTO,
     * or with status 400 (Bad Request) if the zonaCargaDTO is not valid,
     * or with status 500 (Internal Server Error) if the zonaCargaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/zona-cargas")
    @Timed
    public ResponseEntity<ZonaCargaDTO> updateZonaCarga(@Valid @RequestBody ZonaCargaDTO zonaCargaDTO) throws URISyntaxException {
        log.debug("REST request to update ZonaCarga : {}", zonaCargaDTO);
        if (zonaCargaDTO.getId() == null) {
            return createZonaCarga(zonaCargaDTO);
        }
        ZonaCargaDTO result = zonaCargaService.save(zonaCargaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, zonaCargaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /zona-cargas : get all the zonaCargas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of zonaCargas in body
     */
    @GetMapping("/zona-cargas")
    @Timed
    public List<ZonaCargaDTO> getAllZonaCargas() {
        log.debug("REST request to get all ZonaCargas");
        return zonaCargaService.findAll();
        }

    /**
     * GET  /zona-cargas/:id : get the "id" zonaCarga.
     *
     * @param id the id of the zonaCargaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the zonaCargaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/zona-cargas/{id}")
    @Timed
    public ResponseEntity<ZonaCargaDTO> getZonaCarga(@PathVariable Long id) {
        log.debug("REST request to get ZonaCarga : {}", id);
        ZonaCargaDTO zonaCargaDTO = zonaCargaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(zonaCargaDTO));
    }

    /**
     * DELETE  /zona-cargas/:id : delete the "id" zonaCarga.
     *
     * @param id the id of the zonaCargaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/zona-cargas/{id}")
    @Timed
    public ResponseEntity<Void> deleteZonaCarga(@PathVariable Long id) {
        log.debug("REST request to delete ZonaCarga : {}", id);
        zonaCargaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
