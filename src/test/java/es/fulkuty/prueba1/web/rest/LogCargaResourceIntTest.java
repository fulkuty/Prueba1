package es.fulkuty.prueba1.web.rest;

import es.fulkuty.prueba1.Prueba1App;

import es.fulkuty.prueba1.domain.LogCarga;
import es.fulkuty.prueba1.repository.LogCargaRepository;
import es.fulkuty.prueba1.service.LogCargaService;
import es.fulkuty.prueba1.service.dto.LogCargaDTO;
import es.fulkuty.prueba1.service.mapper.LogCargaMapper;
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
 * Test class for the LogCargaResource REST controller.
 *
 * @see LogCargaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Prueba1App.class)
public class LogCargaResourceIntTest {

    private static final Instant DEFAULT_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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
    private LogCargaRepository logCargaRepository;

    @Autowired
    private LogCargaMapper logCargaMapper;

    @Autowired
    private LogCargaService logCargaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restLogCargaMockMvc;

    private LogCarga logCarga;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LogCargaResource logCargaResource = new LogCargaResource(logCargaService);
        this.restLogCargaMockMvc = MockMvcBuilders.standaloneSetup(logCargaResource)
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
    public static LogCarga createEntity(EntityManager em) {
        LogCarga logCarga = new LogCarga()
            .time(DEFAULT_TIME)
            .estado(DEFAULT_ESTADO)
            .programado(DEFAULT_PROGRAMADO)
            .inicio(DEFAULT_INICIO)
            .fin(DEFAULT_FIN)
            .comentario(DEFAULT_COMENTARIO);
        return logCarga;
    }

    @Before
    public void initTest() {
        logCarga = createEntity(em);
    }

    @Test
    @Transactional
    public void createLogCarga() throws Exception {
        int databaseSizeBeforeCreate = logCargaRepository.findAll().size();

        // Create the LogCarga
        LogCargaDTO logCargaDTO = logCargaMapper.toDto(logCarga);
        restLogCargaMockMvc.perform(post("/api/log-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logCargaDTO)))
            .andExpect(status().isCreated());

        // Validate the LogCarga in the database
        List<LogCarga> logCargaList = logCargaRepository.findAll();
        assertThat(logCargaList).hasSize(databaseSizeBeforeCreate + 1);
        LogCarga testLogCarga = logCargaList.get(logCargaList.size() - 1);
        assertThat(testLogCarga.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testLogCarga.getEstado()).isEqualTo(DEFAULT_ESTADO);
        assertThat(testLogCarga.getProgramado()).isEqualTo(DEFAULT_PROGRAMADO);
        assertThat(testLogCarga.getInicio()).isEqualTo(DEFAULT_INICIO);
        assertThat(testLogCarga.getFin()).isEqualTo(DEFAULT_FIN);
        assertThat(testLogCarga.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
    }

    @Test
    @Transactional
    public void createLogCargaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = logCargaRepository.findAll().size();

        // Create the LogCarga with an existing ID
        logCarga.setId(1L);
        LogCargaDTO logCargaDTO = logCargaMapper.toDto(logCarga);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLogCargaMockMvc.perform(post("/api/log-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logCargaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the LogCarga in the database
        List<LogCarga> logCargaList = logCargaRepository.findAll();
        assertThat(logCargaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkTimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = logCargaRepository.findAll().size();
        // set the field null
        logCarga.setTime(null);

        // Create the LogCarga, which fails.
        LogCargaDTO logCargaDTO = logCargaMapper.toDto(logCarga);

        restLogCargaMockMvc.perform(post("/api/log-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logCargaDTO)))
            .andExpect(status().isBadRequest());

        List<LogCarga> logCargaList = logCargaRepository.findAll();
        assertThat(logCargaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkProgramadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = logCargaRepository.findAll().size();
        // set the field null
        logCarga.setProgramado(null);

        // Create the LogCarga, which fails.
        LogCargaDTO logCargaDTO = logCargaMapper.toDto(logCarga);

        restLogCargaMockMvc.perform(post("/api/log-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logCargaDTO)))
            .andExpect(status().isBadRequest());

        List<LogCarga> logCargaList = logCargaRepository.findAll();
        assertThat(logCargaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComentarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = logCargaRepository.findAll().size();
        // set the field null
        logCarga.setComentario(null);

        // Create the LogCarga, which fails.
        LogCargaDTO logCargaDTO = logCargaMapper.toDto(logCarga);

        restLogCargaMockMvc.perform(post("/api/log-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logCargaDTO)))
            .andExpect(status().isBadRequest());

        List<LogCarga> logCargaList = logCargaRepository.findAll();
        assertThat(logCargaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllLogCargas() throws Exception {
        // Initialize the database
        logCargaRepository.saveAndFlush(logCarga);

        // Get all the logCargaList
        restLogCargaMockMvc.perform(get("/api/log-cargas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(logCarga.getId().intValue())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].estado").value(hasItem(DEFAULT_ESTADO.toString())))
            .andExpect(jsonPath("$.[*].programado").value(hasItem(DEFAULT_PROGRAMADO.toString())))
            .andExpect(jsonPath("$.[*].inicio").value(hasItem(DEFAULT_INICIO.toString())))
            .andExpect(jsonPath("$.[*].fin").value(hasItem(DEFAULT_FIN.toString())))
            .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())));
    }

    @Test
    @Transactional
    public void getLogCarga() throws Exception {
        // Initialize the database
        logCargaRepository.saveAndFlush(logCarga);

        // Get the logCarga
        restLogCargaMockMvc.perform(get("/api/log-cargas/{id}", logCarga.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(logCarga.getId().intValue()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.estado").value(DEFAULT_ESTADO.toString()))
            .andExpect(jsonPath("$.programado").value(DEFAULT_PROGRAMADO.toString()))
            .andExpect(jsonPath("$.inicio").value(DEFAULT_INICIO.toString()))
            .andExpect(jsonPath("$.fin").value(DEFAULT_FIN.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLogCarga() throws Exception {
        // Get the logCarga
        restLogCargaMockMvc.perform(get("/api/log-cargas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLogCarga() throws Exception {
        // Initialize the database
        logCargaRepository.saveAndFlush(logCarga);
        int databaseSizeBeforeUpdate = logCargaRepository.findAll().size();

        // Update the logCarga
        LogCarga updatedLogCarga = logCargaRepository.findOne(logCarga.getId());
        // Disconnect from session so that the updates on updatedLogCarga are not directly saved in db
        em.detach(updatedLogCarga);
        updatedLogCarga
            .time(UPDATED_TIME)
            .estado(UPDATED_ESTADO)
            .programado(UPDATED_PROGRAMADO)
            .inicio(UPDATED_INICIO)
            .fin(UPDATED_FIN)
            .comentario(UPDATED_COMENTARIO);
        LogCargaDTO logCargaDTO = logCargaMapper.toDto(updatedLogCarga);

        restLogCargaMockMvc.perform(put("/api/log-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logCargaDTO)))
            .andExpect(status().isOk());

        // Validate the LogCarga in the database
        List<LogCarga> logCargaList = logCargaRepository.findAll();
        assertThat(logCargaList).hasSize(databaseSizeBeforeUpdate);
        LogCarga testLogCarga = logCargaList.get(logCargaList.size() - 1);
        assertThat(testLogCarga.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testLogCarga.getEstado()).isEqualTo(UPDATED_ESTADO);
        assertThat(testLogCarga.getProgramado()).isEqualTo(UPDATED_PROGRAMADO);
        assertThat(testLogCarga.getInicio()).isEqualTo(UPDATED_INICIO);
        assertThat(testLogCarga.getFin()).isEqualTo(UPDATED_FIN);
        assertThat(testLogCarga.getComentario()).isEqualTo(UPDATED_COMENTARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingLogCarga() throws Exception {
        int databaseSizeBeforeUpdate = logCargaRepository.findAll().size();

        // Create the LogCarga
        LogCargaDTO logCargaDTO = logCargaMapper.toDto(logCarga);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restLogCargaMockMvc.perform(put("/api/log-cargas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(logCargaDTO)))
            .andExpect(status().isCreated());

        // Validate the LogCarga in the database
        List<LogCarga> logCargaList = logCargaRepository.findAll();
        assertThat(logCargaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteLogCarga() throws Exception {
        // Initialize the database
        logCargaRepository.saveAndFlush(logCarga);
        int databaseSizeBeforeDelete = logCargaRepository.findAll().size();

        // Get the logCarga
        restLogCargaMockMvc.perform(delete("/api/log-cargas/{id}", logCarga.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<LogCarga> logCargaList = logCargaRepository.findAll();
        assertThat(logCargaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogCarga.class);
        LogCarga logCarga1 = new LogCarga();
        logCarga1.setId(1L);
        LogCarga logCarga2 = new LogCarga();
        logCarga2.setId(logCarga1.getId());
        assertThat(logCarga1).isEqualTo(logCarga2);
        logCarga2.setId(2L);
        assertThat(logCarga1).isNotEqualTo(logCarga2);
        logCarga1.setId(null);
        assertThat(logCarga1).isNotEqualTo(logCarga2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LogCargaDTO.class);
        LogCargaDTO logCargaDTO1 = new LogCargaDTO();
        logCargaDTO1.setId(1L);
        LogCargaDTO logCargaDTO2 = new LogCargaDTO();
        assertThat(logCargaDTO1).isNotEqualTo(logCargaDTO2);
        logCargaDTO2.setId(logCargaDTO1.getId());
        assertThat(logCargaDTO1).isEqualTo(logCargaDTO2);
        logCargaDTO2.setId(2L);
        assertThat(logCargaDTO1).isNotEqualTo(logCargaDTO2);
        logCargaDTO1.setId(null);
        assertThat(logCargaDTO1).isNotEqualTo(logCargaDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(logCargaMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(logCargaMapper.fromId(null)).isNull();
    }
}
