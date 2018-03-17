package es.fulkuty.prueba1.web.rest;

import es.fulkuty.prueba1.Prueba1App;

import es.fulkuty.prueba1.domain.ProcesoCarga;
import es.fulkuty.prueba1.repository.ProcesoCargaRepository;
import es.fulkuty.prueba1.service.ProcesoCargaService;
import es.fulkuty.prueba1.service.dto.ProcesoCargaDTO;
import es.fulkuty.prueba1.service.mapper.ProcesoCargaMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static es.fulkuty.prueba1.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import es.fulkuty.prueba1.domain.enumeration.Estado;
/**
 * Test class for the ProcesoCargaResource REST controller.
 *
 * @see ProcesoCargaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Prueba1App.class)
public class ProcesoCargaResourceIntTest {

    private static final Estado DEFAULT_ESTADO = Estado.CARGANDO;
    private static final Estado UPDATED_ESTADO = Estado.CARGADO;

    private static final Instant DEFAULT_PROGRAMADO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_PROGRAMADO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_INICIO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_INICIO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FIN = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FIN = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_COMENTARIO = "AAAAAAAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBBBBBBB";

    @Autowired
    private ProcesoCargaRepository procesoCargaRepository;

    @Autowired
    private ProcesoCargaMapper procesoCargaMapper;

    @Autowired
    private ProcesoCargaService procesoCargaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restProcesoCargaMockMvc;

    private ProcesoCarga procesoCarga;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProcesoCargaResource procesoCargaResource = new ProcesoCargaResource(procesoCargaService);
        this.restProcesoCargaMockMvc = MockMvcBuilders.standaloneSetup(procesoCargaResource)
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
    public static ProcesoCarga createEntity(EntityManager em) {
        ProcesoCarga procesoCarga = new ProcesoCarga()
            .estado(DEFAULT_ESTADO)
            .programado(DEFAULT_PROGRAMADO)
            .inicio(DEFAULT_INICIO)
            .fin(DEFAULT_FIN)
            .comentario(DEFAULT_COMENTARIO);
        return procesoCarga;
    }

    @Before
    public void initTest() {
        procesoCarga = createEntity(em);
    }

    @Test
    @Transactional
    public void createProcesoCarga() throws Exception {
        int databaseSizeBeforeCreate = procesoCargaRepository.findAll().size();

        // Create the ProcesoCarga
        ProcesoCargaDTO procesoCargaDTO = procesoCargaMapper.toDto(procesoCarga);
        restProcesoCargaMockMvc.perform(post("/api/proceso-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procesoCargaDTO)))
            .andExpect(status().isCreated());

        // Validate the ProcesoCarga in the database
        List<ProcesoCarga> procesoCargaList = procesoCargaRepository.findAll();
        assertThat(procesoCargaList).hasSize(databaseSizeBeforeCreate + 1);
        ProcesoCarga testProcesoCarga = procesoCargaList.get(procesoCargaList.size() - 1);
        assertThat(testProcesoCarga.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testProcesoCarga.getProgramado()).isEqualTo(DEFAULT_PROGRAMADO);
        assertThat(testProcesoCarga.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testProcesoCarga.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testProcesoCarga.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createProcesoCargaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = procesoCargaRepository.findAll().size();

        // Create the ProcesoCarga with an existing ID
        procesoCarga.setId(1L);
        ProcesoCargaDTO procesoCargaDTO = procesoCargaMapper.toDto(procesoCarga);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcesoCargaMockMvc.perform(post("/api/proceso-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procesoCargaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProcesoCarga in the database
        List<ProcesoCarga> procesoCargaList = procesoCargaRepository.findAll();
        assertThat(procesoCargaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkProgramadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = procesoCargaRepository.findAll().size();
        // set the field null
        procesoCarga.setProgramado(null);

        // Create the ProcesoCarga, which fails.
        ProcesoCargaDTO procesoCargaDTO = procesoCargaMapper.toDto(procesoCarga);

        restProcesoCargaMockMvc.perform(post("/api/proceso-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procesoCargaDTO)))
            .andExpect(status().isBadRequest());

        List<ProcesoCarga> procesoCargaList = procesoCargaRepository.findAll();
        assertThat(procesoCargaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComentarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = procesoCargaRepository.findAll().size();
        // set the field null
        procesoCarga.setComentario(null);

        // Create the ProcesoCarga, which fails.
        ProcesoCargaDTO procesoCargaDTO = procesoCargaMapper.toDto(procesoCarga);

        restProcesoCargaMockMvc.perform(post("/api/proceso-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procesoCargaDTO)))
            .andExpect(status().isBadRequest());

        List<ProcesoCarga> procesoCargaList = procesoCargaRepository.findAll();
        assertThat(procesoCargaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProcesoCargas() throws Exception {
        // Initialize the database
        procesoCargaRepository.saveAndFlush(procesoCarga);

        // Get all the procesoCargaList
        restProcesoCargaMockMvc.perform(get("/api/proceso-cargas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(procesoCarga.getId().intValue())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].programado").value(hasItem(DEFAULT_PROGRAMADO.toString())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())));
    }

    @Test
    @Transactional
    public void getProcesoCarga() throws Exception {
        // Initialize the database
        procesoCargaRepository.saveAndFlush(procesoCarga);

        // Get the procesoCarga
        restProcesoCargaMockMvc.perform(get("/api/proceso-cargas/{id}", procesoCarga.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(procesoCarga.getId().intValue()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.programado").value(DEFAULT_PROGRAMADO.toString()))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProcesoCarga() throws Exception {
        // Get the procesoCarga
        restProcesoCargaMockMvc.perform(get("/api/proceso-cargas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProcesoCarga() throws Exception {
        // Initialize the database
        procesoCargaRepository.saveAndFlush(procesoCarga);
        int databaseSizeBeforeUpdate = procesoCargaRepository.findAll().size();

        // Update the procesoCarga
        ProcesoCarga updatedProcesoCarga = procesoCargaRepository.findOne(procesoCarga.getId());
        // Disconnect from session so that the updates on updatedProcesoCarga are not directly saved in db
        em.detach(updatedProcesoCarga);
        updatedProcesoCarga
            .estado(UPDATED_ESTADO)
            .programado(UPDATED_PROGRAMADO)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .comentario(UPDATED_COMENTARIO);
        ProcesoCargaDTO procesoCargaDTO = procesoCargaMapper.toDto(updatedProcesoCarga);

        restProcesoCargaMockMvc.perform(put("/api/proceso-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procesoCargaDTO)))
            .andExpect(status().isOk());

        // Validate the ProcesoCarga in the database
        List<ProcesoCarga> procesoCargaList = procesoCargaRepository.findAll();
        assertThat(procesoCargaList).hasSize(databaseSizeBeforeUpdate);
        ProcesoCarga testProcesoCarga = procesoCargaList.get(procesoCargaList.size() - 1);
        assertThat(testProcesoCarga.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testProcesoCarga.getProgramado()).isEqualTo(UPDATED_PROGRAMADO);
        assertThat(testProcesoCarga.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testProcesoCarga.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testProcesoCarga.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingProcesoCarga() throws Exception {
        int databaseSizeBeforeUpdate = procesoCargaRepository.findAll().size();

        // Create the ProcesoCarga
        ProcesoCargaDTO procesoCargaDTO = procesoCargaMapper.toDto(procesoCarga);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restProcesoCargaMockMvc.perform(put("/api/proceso-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(procesoCargaDTO)))
            .andExpect(status().isCreated());

        // Validate the ProcesoCarga in the database
        List<ProcesoCarga> procesoCargaList = procesoCargaRepository.findAll();
        assertThat(procesoCargaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteProcesoCarga() throws Exception {
        // Initialize the database
        procesoCargaRepository.saveAndFlush(procesoCarga);
        int databaseSizeBeforeDelete = procesoCargaRepository.findAll().size();

        // Get the procesoCarga
        restProcesoCargaMockMvc.perform(delete("/api/proceso-cargas/{id}", procesoCarga.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<ProcesoCarga> procesoCargaList = procesoCargaRepository.findAll();
        assertThat(procesoCargaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcesoCarga.class);
        ProcesoCarga procesoCarga1 = new ProcesoCarga();
        procesoCarga1.setId(1L);
        ProcesoCarga procesoCarga2 = new ProcesoCarga();
        procesoCarga2.setId(procesoCarga1.getId());
        assertThat(procesoCarga1).isEqualTo(procesoCarga2);
        procesoCarga2.setId(2L);
        assertThat(procesoCarga1).isNotEqualTo(procesoCarga2);
        procesoCarga1.setId(null);
        assertThat(procesoCarga1).isNotEqualTo(procesoCarga2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProcesoCargaDTO.class);
        ProcesoCargaDTO procesoCargaDTO1 = new ProcesoCargaDTO();
        procesoCargaDTO1.setId(1L);
        ProcesoCargaDTO procesoCargaDTO2 = new ProcesoCargaDTO();
        assertThat(procesoCargaDTO1).isNotEqualTo(procesoCargaDTO2);
        procesoCargaDTO2.setId(procesoCargaDTO1.getId());
        assertThat(procesoCargaDTO1).isEqualTo(procesoCargaDTO2);
        procesoCargaDTO2.setId(2L);
        assertThat(procesoCargaDTO1).isNotEqualTo(procesoCargaDTO2);
        procesoCargaDTO1.setId(null);
        assertThat(procesoCargaDTO1).isNotEqualTo(procesoCargaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(procesoCargaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(procesoCargaMapper.fromId(null)).isNull();
    }
}
