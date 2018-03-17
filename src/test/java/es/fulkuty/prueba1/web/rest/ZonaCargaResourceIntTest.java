package es.fulkuty.prueba1.web.rest;

import es.fulkuty.prueba1.Prueba1App;

import es.fulkuty.prueba1.domain.ZonaCarga;
import es.fulkuty.prueba1.repository.ZonaCargaRepository;
import es.fulkuty.prueba1.service.ZonaCargaService;
import es.fulkuty.prueba1.service.dto.ZonaCargaDTO;
import es.fulkuty.prueba1.service.mapper.ZonaCargaMapper;
import es.fulkuty.prueba1.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static es.fulkuty.prueba1.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ZonaCargaResource REST controller.
 *
 * @see ZonaCargaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Prueba1App.class)
public class ZonaCargaResourceIntTest {

    private static final String DEFAULT_ZONA = "AAAAAAAAAA";
    private static final String UPDATED_ZONA = "BBBBBBBBBB";

    @Autowired
    private ZonaCargaRepository zonaCargaRepository;

    @Autowired
    private ZonaCargaMapper zonaCargaMapper;

    @Autowired
    private ZonaCargaService zonaCargaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restZonaCargaMockMvc;

    private ZonaCarga zonaCarga;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZonaCargaResource zonaCargaResource = new ZonaCargaResource(zonaCargaService);
        this.restZonaCargaMockMvc = MockMvcBuilders.standaloneSetup(zonaCargaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZonaCarga createEntity(EntityManager em) {
        ZonaCarga zonaCarga = new ZonaCarga()
            .zona(DEFAULT_ZONA);
        return zonaCarga;
    }

    @Before
    public void initTest() {
        zonaCarga = createEntity(em);
    }

    @Test
    @Transactional
    public void createZonaCarga() throws Exception {
        int databaseSizeBeforeCreate = zonaCargaRepository.findAll().size();

        // Create the ZonaCarga
        ZonaCargaDTO zonaCargaDTO = zonaCargaMapper.toDto(zonaCarga);
        restZonaCargaMockMvc.perform(post("/api/zona-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonaCargaDTO)))
            .andExpect(status().isCreated());

        // Validate the ZonaCarga in the database
        List<ZonaCarga> zonaCargaList = zonaCargaRepository.findAll();
        assertThat(zonaCargaList).hasSize(databaseSizeBeforeCreate + 1);
        ZonaCarga testZonaCarga = zonaCargaList.get(zonaCargaList.size() - 1);
        assertThat(testZonaCarga.getZona()).isEqualTo(DEFAULT_ZONA);
    }

    @Test
    @Transactional
    public void createZonaCargaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zonaCargaRepository.findAll().size();

        // Create the ZonaCarga with an existing ID
        zonaCarga.setId(1L);
        ZonaCargaDTO zonaCargaDTO = zonaCargaMapper.toDto(zonaCarga);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZonaCargaMockMvc.perform(post("/api/zona-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonaCargaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ZonaCarga in the database
        List<ZonaCarga> zonaCargaList = zonaCargaRepository.findAll();
        assertThat(zonaCargaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkZonaIsRequired() throws Exception {
        int databaseSizeBeforeTest = zonaCargaRepository.findAll().size();
        // set the field null
        zonaCarga.setZona(null);

        // Create the ZonaCarga, which fails.
        ZonaCargaDTO zonaCargaDTO = zonaCargaMapper.toDto(zonaCarga);

        restZonaCargaMockMvc.perform(post("/api/zona-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonaCargaDTO)))
            .andExpect(status().isBadRequest());

        List<ZonaCarga> zonaCargaList = zonaCargaRepository.findAll();
        assertThat(zonaCargaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllZonaCargas() throws Exception {
        // Initialize the database
        zonaCargaRepository.saveAndFlush(zonaCarga);

        // Get all the zonaCargaList
        restZonaCargaMockMvc.perform(get("/api/zona-cargas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zonaCarga.getId().intValue())))
            .andExpect(jsonPath("$.[*].zona").value(hasItem(DEFAULT_ZONA.toString())));
    }

    @Test
    @Transactional
    public void getZonaCarga() throws Exception {
        // Initialize the database
        zonaCargaRepository.saveAndFlush(zonaCarga);

        // Get the zonaCarga
        restZonaCargaMockMvc.perform(get("/api/zona-cargas/{id}", zonaCarga.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zonaCarga.getId().intValue()))
            .andExpect(jsonPath("$.zona").value(DEFAULT_ZONA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZonaCarga() throws Exception {
        // Get the zonaCarga
        restZonaCargaMockMvc.perform(get("/api/zona-cargas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZonaCarga() throws Exception {
        // Initialize the database
        zonaCargaRepository.saveAndFlush(zonaCarga);
        int databaseSizeBeforeUpdate = zonaCargaRepository.findAll().size();

        // Update the zonaCarga
        ZonaCarga updatedZonaCarga = zonaCargaRepository.findOne(zonaCarga.getId());
        // Disconnect from session so that the updates on updatedZonaCarga are not directly saved in db
        em.detach(updatedZonaCarga);
        updatedZonaCarga
            .zona(UPDATED_ZONA);
        ZonaCargaDTO zonaCargaDTO = zonaCargaMapper.toDto(updatedZonaCarga);

        restZonaCargaMockMvc.perform(put("/api/zona-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonaCargaDTO)))
            .andExpect(status().isOk());

        // Validate the ZonaCarga in the database
        List<ZonaCarga> zonaCargaList = zonaCargaRepository.findAll();
        assertThat(zonaCargaList).hasSize(databaseSizeBeforeUpdate);
        ZonaCarga testZonaCarga = zonaCargaList.get(zonaCargaList.size() - 1);
        assertThat(testZonaCarga.getZona()).isEqualTo(UPDATED_ZONA);
    }

    @Test
    @Transactional
    public void updateNonExistingZonaCarga() throws Exception {
        int databaseSizeBeforeUpdate = zonaCargaRepository.findAll().size();

        // Create the ZonaCarga
        ZonaCargaDTO zonaCargaDTO = zonaCargaMapper.toDto(zonaCarga);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restZonaCargaMockMvc.perform(put("/api/zona-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zonaCargaDTO)))
            .andExpect(status().isCreated());

        // Validate the ZonaCarga in the database
        List<ZonaCarga> zonaCargaList = zonaCargaRepository.findAll();
        assertThat(zonaCargaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteZonaCarga() throws Exception {
        // Initialize the database
        zonaCargaRepository.saveAndFlush(zonaCarga);
        int databaseSizeBeforeDelete = zonaCargaRepository.findAll().size();

        // Get the zonaCarga
        restZonaCargaMockMvc.perform(delete("/api/zona-cargas/{id}", zonaCarga.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ZonaCarga> zonaCargaList = zonaCargaRepository.findAll();
        assertThat(zonaCargaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZonaCarga.class);
        ZonaCarga zonaCarga1 = new ZonaCarga();
        zonaCarga1.setId(1L);
        ZonaCarga zonaCarga2 = new ZonaCarga();
        zonaCarga2.setId(zonaCarga1.getId());
        assertThat(zonaCarga1).isEqualTo(zonaCarga2);
        zonaCarga2.setId(2L);
        assertThat(zonaCarga1).isNotEqualTo(zonaCarga2);
        zonaCarga1.setId(null);
        assertThat(zonaCarga1).isNotEqualTo(zonaCarga2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ZonaCargaDTO.class);
        ZonaCargaDTO zonaCargaDTO1 = new ZonaCargaDTO();
        zonaCargaDTO1.setId(1L);
        ZonaCargaDTO zonaCargaDTO2 = new ZonaCargaDTO();
        assertThat(zonaCargaDTO1).isNotEqualTo(zonaCargaDTO2);
        zonaCargaDTO2.setId(zonaCargaDTO1.getId());
        assertThat(zonaCargaDTO1).isEqualTo(zonaCargaDTO2);
        zonaCargaDTO2.setId(2L);
        assertThat(zonaCargaDTO1).isNotEqualTo(zonaCargaDTO2);
        zonaCargaDTO1.setId(null);
        assertThat(zonaCargaDTO1).isNotEqualTo(zonaCargaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(zonaCargaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(zonaCargaMapper.fromId(null)).isNull();
    }
}
