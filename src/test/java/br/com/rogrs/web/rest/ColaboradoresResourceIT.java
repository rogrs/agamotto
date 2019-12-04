package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.Colaboradores;
import br.com.rogrs.repository.ColaboradoresRepository;
import br.com.rogrs.repository.search.ColaboradoresSearchRepository;
import br.com.rogrs.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static br.com.rogrs.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rogrs.domain.enumeration.TipoSexo;
/**
 * Integration tests for the {@link ColaboradoresResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
public class ColaboradoresResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_MATRICULA = "AAAAAAAAAA";
    private static final String UPDATED_MATRICULA = "BBBBBBBBBB";

    private static final String DEFAULT_PIS = "AAAAAAAAAA";
    private static final String UPDATED_PIS = "BBBBBBBBBB";

    private static final TipoSexo DEFAULT_SEXO = TipoSexo.FEMININO;
    private static final TipoSexo UPDATED_SEXO = TipoSexo.MASCULINO;

    private static final LocalDate DEFAULT_ADMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ADMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_CI = "AAAAAAAAAA";
    private static final String UPDATED_CI = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DEMISSAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DEMISSAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_ATUALIZADO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ATUALIZADO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ColaboradoresRepository colaboradoresRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.ColaboradoresSearchRepositoryMockConfiguration
     */
    @Autowired
    private ColaboradoresSearchRepository mockColaboradoresSearchRepository;

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

    private MockMvc restColaboradoresMockMvc;

    private Colaboradores colaboradores;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ColaboradoresResource colaboradoresResource = new ColaboradoresResource(colaboradoresRepository, mockColaboradoresSearchRepository);
        this.restColaboradoresMockMvc = MockMvcBuilders.standaloneSetup(colaboradoresResource)
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
    public static Colaboradores createEntity(EntityManager em) {
        Colaboradores colaboradores = new Colaboradores()
            .nome(DEFAULT_NOME)
            .matricula(DEFAULT_MATRICULA)
            .pis(DEFAULT_PIS)
            .sexo(DEFAULT_SEXO)
            .admissao(DEFAULT_ADMISSAO)
            .cpf(DEFAULT_CPF)
            .ci(DEFAULT_CI)
            .demissao(DEFAULT_DEMISSAO)
            .atualizado(DEFAULT_ATUALIZADO)
            .criacao(DEFAULT_CRIACAO);
        return colaboradores;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Colaboradores createUpdatedEntity(EntityManager em) {
        Colaboradores colaboradores = new Colaboradores()
            .nome(UPDATED_NOME)
            .matricula(UPDATED_MATRICULA)
            .pis(UPDATED_PIS)
            .sexo(UPDATED_SEXO)
            .admissao(UPDATED_ADMISSAO)
            .cpf(UPDATED_CPF)
            .ci(UPDATED_CI)
            .demissao(UPDATED_DEMISSAO)
            .atualizado(UPDATED_ATUALIZADO)
            .criacao(UPDATED_CRIACAO);
        return colaboradores;
    }

    @BeforeEach
    public void initTest() {
        colaboradores = createEntity(em);
    }

    @Test
    @Transactional
    public void createColaboradores() throws Exception {
        int databaseSizeBeforeCreate = colaboradoresRepository.findAll().size();

        // Create the Colaboradores
        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isCreated());

        // Validate the Colaboradores in the database
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeCreate + 1);
        Colaboradores testColaboradores = colaboradoresList.get(colaboradoresList.size() - 1);
        assertThat(testColaboradores.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testColaboradores.getMatricula()).isEqualTo(DEFAULT_MATRICULA);
        assertThat(testColaboradores.getPis()).isEqualTo(DEFAULT_PIS);
        assertThat(testColaboradores.getSexo()).isEqualTo(DEFAULT_SEXO);
        assertThat(testColaboradores.getAdmissao()).isEqualTo(DEFAULT_ADMISSAO);
        assertThat(testColaboradores.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testColaboradores.getCi()).isEqualTo(DEFAULT_CI);
        assertThat(testColaboradores.getDemissao()).isEqualTo(DEFAULT_DEMISSAO);
        assertThat(testColaboradores.getAtualizado()).isEqualTo(DEFAULT_ATUALIZADO);
        assertThat(testColaboradores.getCriacao()).isEqualTo(DEFAULT_CRIACAO);

        // Validate the Colaboradores in Elasticsearch
        verify(mockColaboradoresSearchRepository, times(1)).save(testColaboradores);
    }

    @Test
    @Transactional
    public void createColaboradoresWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = colaboradoresRepository.findAll().size();

        // Create the Colaboradores with an existing ID
        colaboradores.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        // Validate the Colaboradores in the database
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeCreate);

        // Validate the Colaboradores in Elasticsearch
        verify(mockColaboradoresSearchRepository, times(0)).save(colaboradores);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = colaboradoresRepository.findAll().size();
        // set the field null
        colaboradores.setNome(null);

        // Create the Colaboradores, which fails.

        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMatriculaIsRequired() throws Exception {
        int databaseSizeBeforeTest = colaboradoresRepository.findAll().size();
        // set the field null
        colaboradores.setMatricula(null);

        // Create the Colaboradores, which fails.

        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPisIsRequired() throws Exception {
        int databaseSizeBeforeTest = colaboradoresRepository.findAll().size();
        // set the field null
        colaboradores.setPis(null);

        // Create the Colaboradores, which fails.

        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSexoIsRequired() throws Exception {
        int databaseSizeBeforeTest = colaboradoresRepository.findAll().size();
        // set the field null
        colaboradores.setSexo(null);

        // Create the Colaboradores, which fails.

        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAdmissaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = colaboradoresRepository.findAll().size();
        // set the field null
        colaboradores.setAdmissao(null);

        // Create the Colaboradores, which fails.

        restColaboradoresMockMvc.perform(post("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllColaboradores() throws Exception {
        // Initialize the database
        colaboradoresRepository.saveAndFlush(colaboradores);

        // Get all the colaboradoresList
        restColaboradoresMockMvc.perform(get("/api/colaboradores?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colaboradores.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].pis").value(hasItem(DEFAULT_PIS)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].admissao").value(hasItem(DEFAULT_ADMISSAO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].demissao").value(hasItem(DEFAULT_DEMISSAO.toString())))
            .andExpect(jsonPath("$.[*].atualizado").value(hasItem(DEFAULT_ATUALIZADO.toString())))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(DEFAULT_CRIACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getColaboradores() throws Exception {
        // Initialize the database
        colaboradoresRepository.saveAndFlush(colaboradores);

        // Get the colaboradores
        restColaboradoresMockMvc.perform(get("/api/colaboradores/{id}", colaboradores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(colaboradores.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.matricula").value(DEFAULT_MATRICULA))
            .andExpect(jsonPath("$.pis").value(DEFAULT_PIS))
            .andExpect(jsonPath("$.sexo").value(DEFAULT_SEXO.toString()))
            .andExpect(jsonPath("$.admissao").value(DEFAULT_ADMISSAO.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF))
            .andExpect(jsonPath("$.ci").value(DEFAULT_CI))
            .andExpect(jsonPath("$.demissao").value(DEFAULT_DEMISSAO.toString()))
            .andExpect(jsonPath("$.atualizado").value(DEFAULT_ATUALIZADO.toString()))
            .andExpect(jsonPath("$.criacao").value(DEFAULT_CRIACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingColaboradores() throws Exception {
        // Get the colaboradores
        restColaboradoresMockMvc.perform(get("/api/colaboradores/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateColaboradores() throws Exception {
        // Initialize the database
        colaboradoresRepository.saveAndFlush(colaboradores);

        int databaseSizeBeforeUpdate = colaboradoresRepository.findAll().size();

        // Update the colaboradores
        Colaboradores updatedColaboradores = colaboradoresRepository.findById(colaboradores.getId()).get();
        // Disconnect from session so that the updates on updatedColaboradores are not directly saved in db
        em.detach(updatedColaboradores);
        updatedColaboradores
            .nome(UPDATED_NOME)
            .matricula(UPDATED_MATRICULA)
            .pis(UPDATED_PIS)
            .sexo(UPDATED_SEXO)
            .admissao(UPDATED_ADMISSAO)
            .cpf(UPDATED_CPF)
            .ci(UPDATED_CI)
            .demissao(UPDATED_DEMISSAO)
            .atualizado(UPDATED_ATUALIZADO)
            .criacao(UPDATED_CRIACAO);

        restColaboradoresMockMvc.perform(put("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedColaboradores)))
            .andExpect(status().isOk());

        // Validate the Colaboradores in the database
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeUpdate);
        Colaboradores testColaboradores = colaboradoresList.get(colaboradoresList.size() - 1);
        assertThat(testColaboradores.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testColaboradores.getMatricula()).isEqualTo(UPDATED_MATRICULA);
        assertThat(testColaboradores.getPis()).isEqualTo(UPDATED_PIS);
        assertThat(testColaboradores.getSexo()).isEqualTo(UPDATED_SEXO);
        assertThat(testColaboradores.getAdmissao()).isEqualTo(UPDATED_ADMISSAO);
        assertThat(testColaboradores.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testColaboradores.getCi()).isEqualTo(UPDATED_CI);
        assertThat(testColaboradores.getDemissao()).isEqualTo(UPDATED_DEMISSAO);
        assertThat(testColaboradores.getAtualizado()).isEqualTo(UPDATED_ATUALIZADO);
        assertThat(testColaboradores.getCriacao()).isEqualTo(UPDATED_CRIACAO);

        // Validate the Colaboradores in Elasticsearch
        verify(mockColaboradoresSearchRepository, times(1)).save(testColaboradores);
    }

    @Test
    @Transactional
    public void updateNonExistingColaboradores() throws Exception {
        int databaseSizeBeforeUpdate = colaboradoresRepository.findAll().size();

        // Create the Colaboradores

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restColaboradoresMockMvc.perform(put("/api/colaboradores")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(colaboradores)))
            .andExpect(status().isBadRequest());

        // Validate the Colaboradores in the database
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Colaboradores in Elasticsearch
        verify(mockColaboradoresSearchRepository, times(0)).save(colaboradores);
    }

    @Test
    @Transactional
    public void deleteColaboradores() throws Exception {
        // Initialize the database
        colaboradoresRepository.saveAndFlush(colaboradores);

        int databaseSizeBeforeDelete = colaboradoresRepository.findAll().size();

        // Delete the colaboradores
        restColaboradoresMockMvc.perform(delete("/api/colaboradores/{id}", colaboradores.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Colaboradores> colaboradoresList = colaboradoresRepository.findAll();
        assertThat(colaboradoresList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Colaboradores in Elasticsearch
        verify(mockColaboradoresSearchRepository, times(1)).deleteById(colaboradores.getId());
    }

    @Test
    @Transactional
    public void searchColaboradores() throws Exception {
        // Initialize the database
        colaboradoresRepository.saveAndFlush(colaboradores);
        when(mockColaboradoresSearchRepository.search(queryStringQuery("id:" + colaboradores.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(colaboradores), PageRequest.of(0, 1), 1));
        // Search the colaboradores
        restColaboradoresMockMvc.perform(get("/api/_search/colaboradores?query=id:" + colaboradores.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(colaboradores.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].matricula").value(hasItem(DEFAULT_MATRICULA)))
            .andExpect(jsonPath("$.[*].pis").value(hasItem(DEFAULT_PIS)))
            .andExpect(jsonPath("$.[*].sexo").value(hasItem(DEFAULT_SEXO.toString())))
            .andExpect(jsonPath("$.[*].admissao").value(hasItem(DEFAULT_ADMISSAO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF)))
            .andExpect(jsonPath("$.[*].ci").value(hasItem(DEFAULT_CI)))
            .andExpect(jsonPath("$.[*].demissao").value(hasItem(DEFAULT_DEMISSAO.toString())))
            .andExpect(jsonPath("$.[*].atualizado").value(hasItem(DEFAULT_ATUALIZADO.toString())))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(DEFAULT_CRIACAO.toString())));
    }
}
