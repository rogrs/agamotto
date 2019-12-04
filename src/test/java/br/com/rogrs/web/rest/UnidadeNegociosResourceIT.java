package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.UnidadeNegocios;
import br.com.rogrs.repository.UnidadeNegociosRepository;
import br.com.rogrs.repository.search.UnidadeNegociosSearchRepository;
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
import java.util.Collections;
import java.util.List;

import static br.com.rogrs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rogrs.domain.enumeration.TipoBoolean;
/**
 * Integration tests for the {@link UnidadeNegociosResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
public class UnidadeNegociosResourceIT {

    private static final String DEFAULT_RAZAO_SOCIAL = "AAAAAAAAAA";
    private static final String UPDATED_RAZAO_SOCIAL = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_EMPRESA = "AAAAAAAAAA";
    private static final String UPDATED_NOME_EMPRESA = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_INSCRICAO_ESTADUAL = "AAAAAAAAAA";
    private static final String UPDATED_INSCRICAO_ESTADUAL = "BBBBBBBBBB";

    private static final TipoBoolean DEFAULT_EMPREGADORA = TipoBoolean.SIM;
    private static final TipoBoolean UPDATED_EMPREGADORA = TipoBoolean.NAO;

    @Autowired
    private UnidadeNegociosRepository unidadeNegociosRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.UnidadeNegociosSearchRepositoryMockConfiguration
     */
    @Autowired
    private UnidadeNegociosSearchRepository mockUnidadeNegociosSearchRepository;

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

    private MockMvc restUnidadeNegociosMockMvc;

    private UnidadeNegocios unidadeNegocios;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UnidadeNegociosResource unidadeNegociosResource = new UnidadeNegociosResource(unidadeNegociosRepository, mockUnidadeNegociosSearchRepository);
        this.restUnidadeNegociosMockMvc = MockMvcBuilders.standaloneSetup(unidadeNegociosResource)
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
    public static UnidadeNegocios createEntity(EntityManager em) {
        UnidadeNegocios unidadeNegocios = new UnidadeNegocios()
            .razaoSocial(DEFAULT_RAZAO_SOCIAL)
            .nomeEmpresa(DEFAULT_NOME_EMPRESA)
            .cnpj(DEFAULT_CNPJ)
            .inscricaoEstadual(DEFAULT_INSCRICAO_ESTADUAL)
            .empregadora(DEFAULT_EMPREGADORA);
        return unidadeNegocios;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UnidadeNegocios createUpdatedEntity(EntityManager em) {
        UnidadeNegocios unidadeNegocios = new UnidadeNegocios()
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .cnpj(UPDATED_CNPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .empregadora(UPDATED_EMPREGADORA);
        return unidadeNegocios;
    }

    @BeforeEach
    public void initTest() {
        unidadeNegocios = createEntity(em);
    }

    @Test
    @Transactional
    public void createUnidadeNegocios() throws Exception {
        int databaseSizeBeforeCreate = unidadeNegociosRepository.findAll().size();

        // Create the UnidadeNegocios
        restUnidadeNegociosMockMvc.perform(post("/api/unidade-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeNegocios)))
            .andExpect(status().isCreated());

        // Validate the UnidadeNegocios in the database
        List<UnidadeNegocios> unidadeNegociosList = unidadeNegociosRepository.findAll();
        assertThat(unidadeNegociosList).hasSize(databaseSizeBeforeCreate + 1);
        UnidadeNegocios testUnidadeNegocios = unidadeNegociosList.get(unidadeNegociosList.size() - 1);
        assertThat(testUnidadeNegocios.getRazaoSocial()).isEqualTo(DEFAULT_RAZAO_SOCIAL);
        assertThat(testUnidadeNegocios.getNomeEmpresa()).isEqualTo(DEFAULT_NOME_EMPRESA);
        assertThat(testUnidadeNegocios.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testUnidadeNegocios.getInscricaoEstadual()).isEqualTo(DEFAULT_INSCRICAO_ESTADUAL);
        assertThat(testUnidadeNegocios.getEmpregadora()).isEqualTo(DEFAULT_EMPREGADORA);

        // Validate the UnidadeNegocios in Elasticsearch
        verify(mockUnidadeNegociosSearchRepository, times(1)).save(testUnidadeNegocios);
    }

    @Test
    @Transactional
    public void createUnidadeNegociosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = unidadeNegociosRepository.findAll().size();

        // Create the UnidadeNegocios with an existing ID
        unidadeNegocios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUnidadeNegociosMockMvc.perform(post("/api/unidade-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeNegocios)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeNegocios in the database
        List<UnidadeNegocios> unidadeNegociosList = unidadeNegociosRepository.findAll();
        assertThat(unidadeNegociosList).hasSize(databaseSizeBeforeCreate);

        // Validate the UnidadeNegocios in Elasticsearch
        verify(mockUnidadeNegociosSearchRepository, times(0)).save(unidadeNegocios);
    }


    @Test
    @Transactional
    public void checkRazaoSocialIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeNegociosRepository.findAll().size();
        // set the field null
        unidadeNegocios.setRazaoSocial(null);

        // Create the UnidadeNegocios, which fails.

        restUnidadeNegociosMockMvc.perform(post("/api/unidade-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeNegocios)))
            .andExpect(status().isBadRequest());

        List<UnidadeNegocios> unidadeNegociosList = unidadeNegociosRepository.findAll();
        assertThat(unidadeNegociosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeEmpresaIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeNegociosRepository.findAll().size();
        // set the field null
        unidadeNegocios.setNomeEmpresa(null);

        // Create the UnidadeNegocios, which fails.

        restUnidadeNegociosMockMvc.perform(post("/api/unidade-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeNegocios)))
            .andExpect(status().isBadRequest());

        List<UnidadeNegocios> unidadeNegociosList = unidadeNegociosRepository.findAll();
        assertThat(unidadeNegociosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmpregadoraIsRequired() throws Exception {
        int databaseSizeBeforeTest = unidadeNegociosRepository.findAll().size();
        // set the field null
        unidadeNegocios.setEmpregadora(null);

        // Create the UnidadeNegocios, which fails.

        restUnidadeNegociosMockMvc.perform(post("/api/unidade-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeNegocios)))
            .andExpect(status().isBadRequest());

        List<UnidadeNegocios> unidadeNegociosList = unidadeNegociosRepository.findAll();
        assertThat(unidadeNegociosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUnidadeNegocios() throws Exception {
        // Initialize the database
        unidadeNegociosRepository.saveAndFlush(unidadeNegocios);

        // Get all the unidadeNegociosList
        restUnidadeNegociosMockMvc.perform(get("/api/unidade-negocios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeNegocios.getId().intValue())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeEmpresa").value(hasItem(DEFAULT_NOME_EMPRESA)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].inscricaoEstadual").value(hasItem(DEFAULT_INSCRICAO_ESTADUAL)))
            .andExpect(jsonPath("$.[*].empregadora").value(hasItem(DEFAULT_EMPREGADORA.toString())));
    }
    
    @Test
    @Transactional
    public void getUnidadeNegocios() throws Exception {
        // Initialize the database
        unidadeNegociosRepository.saveAndFlush(unidadeNegocios);

        // Get the unidadeNegocios
        restUnidadeNegociosMockMvc.perform(get("/api/unidade-negocios/{id}", unidadeNegocios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(unidadeNegocios.getId().intValue()))
            .andExpect(jsonPath("$.razaoSocial").value(DEFAULT_RAZAO_SOCIAL))
            .andExpect(jsonPath("$.nomeEmpresa").value(DEFAULT_NOME_EMPRESA))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ))
            .andExpect(jsonPath("$.inscricaoEstadual").value(DEFAULT_INSCRICAO_ESTADUAL))
            .andExpect(jsonPath("$.empregadora").value(DEFAULT_EMPREGADORA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUnidadeNegocios() throws Exception {
        // Get the unidadeNegocios
        restUnidadeNegociosMockMvc.perform(get("/api/unidade-negocios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUnidadeNegocios() throws Exception {
        // Initialize the database
        unidadeNegociosRepository.saveAndFlush(unidadeNegocios);

        int databaseSizeBeforeUpdate = unidadeNegociosRepository.findAll().size();

        // Update the unidadeNegocios
        UnidadeNegocios updatedUnidadeNegocios = unidadeNegociosRepository.findById(unidadeNegocios.getId()).get();
        // Disconnect from session so that the updates on updatedUnidadeNegocios are not directly saved in db
        em.detach(updatedUnidadeNegocios);
        updatedUnidadeNegocios
            .razaoSocial(UPDATED_RAZAO_SOCIAL)
            .nomeEmpresa(UPDATED_NOME_EMPRESA)
            .cnpj(UPDATED_CNPJ)
            .inscricaoEstadual(UPDATED_INSCRICAO_ESTADUAL)
            .empregadora(UPDATED_EMPREGADORA);

        restUnidadeNegociosMockMvc.perform(put("/api/unidade-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUnidadeNegocios)))
            .andExpect(status().isOk());

        // Validate the UnidadeNegocios in the database
        List<UnidadeNegocios> unidadeNegociosList = unidadeNegociosRepository.findAll();
        assertThat(unidadeNegociosList).hasSize(databaseSizeBeforeUpdate);
        UnidadeNegocios testUnidadeNegocios = unidadeNegociosList.get(unidadeNegociosList.size() - 1);
        assertThat(testUnidadeNegocios.getRazaoSocial()).isEqualTo(UPDATED_RAZAO_SOCIAL);
        assertThat(testUnidadeNegocios.getNomeEmpresa()).isEqualTo(UPDATED_NOME_EMPRESA);
        assertThat(testUnidadeNegocios.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testUnidadeNegocios.getInscricaoEstadual()).isEqualTo(UPDATED_INSCRICAO_ESTADUAL);
        assertThat(testUnidadeNegocios.getEmpregadora()).isEqualTo(UPDATED_EMPREGADORA);

        // Validate the UnidadeNegocios in Elasticsearch
        verify(mockUnidadeNegociosSearchRepository, times(1)).save(testUnidadeNegocios);
    }

    @Test
    @Transactional
    public void updateNonExistingUnidadeNegocios() throws Exception {
        int databaseSizeBeforeUpdate = unidadeNegociosRepository.findAll().size();

        // Create the UnidadeNegocios

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUnidadeNegociosMockMvc.perform(put("/api/unidade-negocios")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(unidadeNegocios)))
            .andExpect(status().isBadRequest());

        // Validate the UnidadeNegocios in the database
        List<UnidadeNegocios> unidadeNegociosList = unidadeNegociosRepository.findAll();
        assertThat(unidadeNegociosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UnidadeNegocios in Elasticsearch
        verify(mockUnidadeNegociosSearchRepository, times(0)).save(unidadeNegocios);
    }

    @Test
    @Transactional
    public void deleteUnidadeNegocios() throws Exception {
        // Initialize the database
        unidadeNegociosRepository.saveAndFlush(unidadeNegocios);

        int databaseSizeBeforeDelete = unidadeNegociosRepository.findAll().size();

        // Delete the unidadeNegocios
        restUnidadeNegociosMockMvc.perform(delete("/api/unidade-negocios/{id}", unidadeNegocios.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UnidadeNegocios> unidadeNegociosList = unidadeNegociosRepository.findAll();
        assertThat(unidadeNegociosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UnidadeNegocios in Elasticsearch
        verify(mockUnidadeNegociosSearchRepository, times(1)).deleteById(unidadeNegocios.getId());
    }

    @Test
    @Transactional
    public void searchUnidadeNegocios() throws Exception {
        // Initialize the database
        unidadeNegociosRepository.saveAndFlush(unidadeNegocios);
        when(mockUnidadeNegociosSearchRepository.search(queryStringQuery("id:" + unidadeNegocios.getId())))
            .thenReturn(Collections.singletonList(unidadeNegocios));
        // Search the unidadeNegocios
        restUnidadeNegociosMockMvc.perform(get("/api/_search/unidade-negocios?query=id:" + unidadeNegocios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(unidadeNegocios.getId().intValue())))
            .andExpect(jsonPath("$.[*].razaoSocial").value(hasItem(DEFAULT_RAZAO_SOCIAL)))
            .andExpect(jsonPath("$.[*].nomeEmpresa").value(hasItem(DEFAULT_NOME_EMPRESA)))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ)))
            .andExpect(jsonPath("$.[*].inscricaoEstadual").value(hasItem(DEFAULT_INSCRICAO_ESTADUAL)))
            .andExpect(jsonPath("$.[*].empregadora").value(hasItem(DEFAULT_EMPREGADORA.toString())));
    }
}
