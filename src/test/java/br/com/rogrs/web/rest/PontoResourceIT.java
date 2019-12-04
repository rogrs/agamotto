package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.Ponto;
import br.com.rogrs.repository.PontoRepository;
import br.com.rogrs.repository.search.PontoSearchRepository;
import br.com.rogrs.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static br.com.rogrs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rogrs.domain.enumeration.TipoMotivoAjuste;
/**
 * Integration tests for the {@link PontoResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
public class PontoResourceIT {

    private static final Duration DEFAULT_MARCACAO = Duration.ofHours(6);
    private static final Duration UPDATED_MARCACAO = Duration.ofHours(12);

    private static final TipoMotivoAjuste DEFAULT_MOTIVO = TipoMotivoAjuste.AJUSTE;
    private static final TipoMotivoAjuste UPDATED_MOTIVO = TipoMotivoAjuste.FALTA;

    @Autowired
    private PontoRepository pontoRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.PontoSearchRepositoryMockConfiguration
     */
    @Autowired
    private PontoSearchRepository mockPontoSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restPontoMockMvc;

    private Ponto ponto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PontoResource pontoResource = new PontoResource(pontoRepository, mockPontoSearchRepository);
        this.restPontoMockMvc = MockMvcBuilders.standaloneSetup(pontoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ponto createEntity(EntityManager em) {
        Ponto ponto = new Ponto()
            .marcacao(DEFAULT_MARCACAO)
            .motivo(DEFAULT_MOTIVO);
        return ponto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Ponto createUpdatedEntity(EntityManager em) {
        Ponto ponto = new Ponto()
            .marcacao(UPDATED_MARCACAO)
            .motivo(UPDATED_MOTIVO);
        return ponto;
    }

    @BeforeEach
    public void initTest() {
        ponto = createEntity(em);
    }

    @Test
    @Transactional
    public void createPonto() throws Exception {
        int databaseSizeBeforeCreate = pontoRepository.findAll().size();

        // Create the Ponto
        restPontoMockMvc.perform(post("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponto)))
            .andExpect(status().isCreated());

        // Validate the Ponto in the database
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeCreate + 1);
        Ponto testPonto = pontoList.get(pontoList.size() - 1);
        assertThat(testPonto.getMarcacao()).isEqualTo(DEFAULT_MARCACAO);
        assertThat(testPonto.getMotivo()).isEqualTo(DEFAULT_MOTIVO);

        // Validate the Ponto in Elasticsearch
        verify(mockPontoSearchRepository, times(1)).save(testPonto);
    }

    @Test
    @Transactional
    public void createPontoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = pontoRepository.findAll().size();

        // Create the Ponto with an existing ID
        ponto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPontoMockMvc.perform(post("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponto)))
            .andExpect(status().isBadRequest());

        // Validate the Ponto in the database
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeCreate);

        // Validate the Ponto in Elasticsearch
        verify(mockPontoSearchRepository, times(0)).save(ponto);
    }


    @Test
    @Transactional
    public void checkMarcacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pontoRepository.findAll().size();
        // set the field null
        ponto.setMarcacao(null);

        // Create the Ponto, which fails.

        restPontoMockMvc.perform(post("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponto)))
            .andExpect(status().isBadRequest());

        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMotivoIsRequired() throws Exception {
        int databaseSizeBeforeTest = pontoRepository.findAll().size();
        // set the field null
        ponto.setMotivo(null);

        // Create the Ponto, which fails.

        restPontoMockMvc.perform(post("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponto)))
            .andExpect(status().isBadRequest());

        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPontos() throws Exception {
        // Initialize the database
        pontoRepository.saveAndFlush(ponto);

        // Get all the pontoList
        restPontoMockMvc.perform(get("/api/pontos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ponto.getId().intValue())))
            .andExpect(jsonPath("$.[*].marcacao").value(hasItem(DEFAULT_MARCACAO.toString())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO.toString())));
    }
    
    @Test
    @Transactional
    public void getPonto() throws Exception {
        // Initialize the database
        pontoRepository.saveAndFlush(ponto);

        // Get the ponto
        restPontoMockMvc.perform(get("/api/pontos/{id}", ponto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(ponto.getId().intValue()))
            .andExpect(jsonPath("$.marcacao").value(DEFAULT_MARCACAO.toString()))
            .andExpect(jsonPath("$.motivo").value(DEFAULT_MOTIVO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPonto() throws Exception {
        // Get the ponto
        restPontoMockMvc.perform(get("/api/pontos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePonto() throws Exception {
        // Initialize the database
        pontoRepository.saveAndFlush(ponto);

        int databaseSizeBeforeUpdate = pontoRepository.findAll().size();

        // Update the ponto
        Ponto updatedPonto = pontoRepository.findById(ponto.getId()).get();
        // Disconnect from session so that the updates on updatedPonto are not directly saved in db
        em.detach(updatedPonto);
        updatedPonto
            .marcacao(UPDATED_MARCACAO)
            .motivo(UPDATED_MOTIVO);

        restPontoMockMvc.perform(put("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPonto)))
            .andExpect(status().isOk());

        // Validate the Ponto in the database
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeUpdate);
        Ponto testPonto = pontoList.get(pontoList.size() - 1);
        assertThat(testPonto.getMarcacao()).isEqualTo(UPDATED_MARCACAO);
        assertThat(testPonto.getMotivo()).isEqualTo(UPDATED_MOTIVO);

        // Validate the Ponto in Elasticsearch
        verify(mockPontoSearchRepository, times(1)).save(testPonto);
    }

    @Test
    @Transactional
    public void updateNonExistingPonto() throws Exception {
        int databaseSizeBeforeUpdate = pontoRepository.findAll().size();

        // Create the Ponto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPontoMockMvc.perform(put("/api/pontos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(ponto)))
            .andExpect(status().isBadRequest());

        // Validate the Ponto in the database
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Ponto in Elasticsearch
        verify(mockPontoSearchRepository, times(0)).save(ponto);
    }

    @Test
    @Transactional
    public void deletePonto() throws Exception {
        // Initialize the database
        pontoRepository.saveAndFlush(ponto);

        int databaseSizeBeforeDelete = pontoRepository.findAll().size();

        // Delete the ponto
        restPontoMockMvc.perform(delete("/api/pontos/{id}", ponto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Ponto> pontoList = pontoRepository.findAll();
        assertThat(pontoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Ponto in Elasticsearch
        verify(mockPontoSearchRepository, times(1)).deleteById(ponto.getId());
    }

    @Test
    @Transactional
    public void searchPonto() throws Exception {
        // Initialize the database
        pontoRepository.saveAndFlush(ponto);
        when(mockPontoSearchRepository.search(queryStringQuery("id:" + ponto.getId())))
            .thenReturn(Collections.singletonList(ponto));
        // Search the ponto
        restPontoMockMvc.perform(get("/api/_search/pontos?query=id:" + ponto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(ponto.getId().intValue())))
            .andExpect(jsonPath("$.[*].marcacao").value(hasItem(DEFAULT_MARCACAO.toString())))
            .andExpect(jsonPath("$.[*].motivo").value(hasItem(DEFAULT_MOTIVO.toString())));
    }
}
