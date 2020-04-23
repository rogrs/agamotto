package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.Arquivos;
import br.com.rogrs.repository.ArquivosRepository;
import br.com.rogrs.repository.search.ArquivosSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rogrs.domain.enumeration.StatusArquivo;
/**
 * Integration tests for the {@link ArquivosResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ArquivosResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_CAMINHO = "AAAAAAAAAA";
    private static final String UPDATED_CAMINHO = "BBBBBBBBBB";

    private static final String DEFAULT_MD_5 = "AAAAAAAAAA";
    private static final String UPDATED_MD_5 = "BBBBBBBBBB";

    private static final StatusArquivo DEFAULT_STATUS = StatusArquivo.CRIADO;
    private static final StatusArquivo UPDATED_STATUS = StatusArquivo.ERRO;

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final Integer DEFAULT_TOTAL_LINHAS = 1;
    private static final Integer UPDATED_TOTAL_LINHAS = 2;

    private static final LocalDate DEFAULT_CRIACAO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CRIACAO = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PROCESSAMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PROCESSAMENTO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ArquivosRepository arquivosRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.ArquivosSearchRepositoryMockConfiguration
     */
    @Autowired
    private ArquivosSearchRepository mockArquivosSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restArquivosMockMvc;

    private Arquivos arquivos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arquivos createEntity(EntityManager em) {
        Arquivos arquivos = new Arquivos()
            .nome(DEFAULT_NOME)
            .caminho(DEFAULT_CAMINHO)
            .md5(DEFAULT_MD_5)
            .status(DEFAULT_STATUS)
            .tipo(DEFAULT_TIPO)
            .totalLinhas(DEFAULT_TOTAL_LINHAS)
            .criacao(DEFAULT_CRIACAO)
            .processamento(DEFAULT_PROCESSAMENTO);
        return arquivos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Arquivos createUpdatedEntity(EntityManager em) {
        Arquivos arquivos = new Arquivos()
            .nome(UPDATED_NOME)
            .caminho(UPDATED_CAMINHO)
            .md5(UPDATED_MD_5)
            .status(UPDATED_STATUS)
            .tipo(UPDATED_TIPO)
            .totalLinhas(UPDATED_TOTAL_LINHAS)
            .criacao(UPDATED_CRIACAO)
            .processamento(UPDATED_PROCESSAMENTO);
        return arquivos;
    }

    @BeforeEach
    public void initTest() {
        arquivos = createEntity(em);
    }

    @Test
    @Transactional
    public void createArquivos() throws Exception {
        int databaseSizeBeforeCreate = arquivosRepository.findAll().size();

        // Create the Arquivos
        restArquivosMockMvc.perform(post("/api/arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arquivos)))
            .andExpect(status().isCreated());

        // Validate the Arquivos in the database
        List<Arquivos> arquivosList = arquivosRepository.findAll();
        assertThat(arquivosList).hasSize(databaseSizeBeforeCreate + 1);
        Arquivos testArquivos = arquivosList.get(arquivosList.size() - 1);
        assertThat(testArquivos.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testArquivos.getCaminho()).isEqualTo(DEFAULT_CAMINHO);
        assertThat(testArquivos.getMd5()).isEqualTo(DEFAULT_MD_5);
        assertThat(testArquivos.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testArquivos.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testArquivos.getTotalLinhas()).isEqualTo(DEFAULT_TOTAL_LINHAS);
        assertThat(testArquivos.getCriacao()).isEqualTo(DEFAULT_CRIACAO);
        assertThat(testArquivos.getProcessamento()).isEqualTo(DEFAULT_PROCESSAMENTO);

        // Validate the Arquivos in Elasticsearch
        verify(mockArquivosSearchRepository, times(1)).save(testArquivos);
    }

    @Test
    @Transactional
    public void createArquivosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = arquivosRepository.findAll().size();

        // Create the Arquivos with an existing ID
        arquivos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restArquivosMockMvc.perform(post("/api/arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arquivos)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivos in the database
        List<Arquivos> arquivosList = arquivosRepository.findAll();
        assertThat(arquivosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Arquivos in Elasticsearch
        verify(mockArquivosSearchRepository, times(0)).save(arquivos);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = arquivosRepository.findAll().size();
        // set the field null
        arquivos.setNome(null);

        // Create the Arquivos, which fails.

        restArquivosMockMvc.perform(post("/api/arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arquivos)))
            .andExpect(status().isBadRequest());

        List<Arquivos> arquivosList = arquivosRepository.findAll();
        assertThat(arquivosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCaminhoIsRequired() throws Exception {
        int databaseSizeBeforeTest = arquivosRepository.findAll().size();
        // set the field null
        arquivos.setCaminho(null);

        // Create the Arquivos, which fails.

        restArquivosMockMvc.perform(post("/api/arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arquivos)))
            .andExpect(status().isBadRequest());

        List<Arquivos> arquivosList = arquivosRepository.findAll();
        assertThat(arquivosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMd5IsRequired() throws Exception {
        int databaseSizeBeforeTest = arquivosRepository.findAll().size();
        // set the field null
        arquivos.setMd5(null);

        // Create the Arquivos, which fails.

        restArquivosMockMvc.perform(post("/api/arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arquivos)))
            .andExpect(status().isBadRequest());

        List<Arquivos> arquivosList = arquivosRepository.findAll();
        assertThat(arquivosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllArquivos() throws Exception {
        // Initialize the database
        arquivosRepository.saveAndFlush(arquivos);

        // Get all the arquivosList
        restArquivosMockMvc.perform(get("/api/arquivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arquivos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].caminho").value(hasItem(DEFAULT_CAMINHO)))
            .andExpect(jsonPath("$.[*].md5").value(hasItem(DEFAULT_MD_5)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].totalLinhas").value(hasItem(DEFAULT_TOTAL_LINHAS)))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(DEFAULT_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].processamento").value(hasItem(DEFAULT_PROCESSAMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getArquivos() throws Exception {
        // Initialize the database
        arquivosRepository.saveAndFlush(arquivos);

        // Get the arquivos
        restArquivosMockMvc.perform(get("/api/arquivos/{id}", arquivos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(arquivos.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.caminho").value(DEFAULT_CAMINHO))
            .andExpect(jsonPath("$.md5").value(DEFAULT_MD_5))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.totalLinhas").value(DEFAULT_TOTAL_LINHAS))
            .andExpect(jsonPath("$.criacao").value(DEFAULT_CRIACAO.toString()))
            .andExpect(jsonPath("$.processamento").value(DEFAULT_PROCESSAMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingArquivos() throws Exception {
        // Get the arquivos
        restArquivosMockMvc.perform(get("/api/arquivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateArquivos() throws Exception {
        // Initialize the database
        arquivosRepository.saveAndFlush(arquivos);

        int databaseSizeBeforeUpdate = arquivosRepository.findAll().size();

        // Update the arquivos
        Arquivos updatedArquivos = arquivosRepository.findById(arquivos.getId()).get();
        // Disconnect from session so that the updates on updatedArquivos are not directly saved in db
        em.detach(updatedArquivos);
        updatedArquivos
            .nome(UPDATED_NOME)
            .caminho(UPDATED_CAMINHO)
            .md5(UPDATED_MD_5)
            .status(UPDATED_STATUS)
            .tipo(UPDATED_TIPO)
            .totalLinhas(UPDATED_TOTAL_LINHAS)
            .criacao(UPDATED_CRIACAO)
            .processamento(UPDATED_PROCESSAMENTO);

        restArquivosMockMvc.perform(put("/api/arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedArquivos)))
            .andExpect(status().isOk());

        // Validate the Arquivos in the database
        List<Arquivos> arquivosList = arquivosRepository.findAll();
        assertThat(arquivosList).hasSize(databaseSizeBeforeUpdate);
        Arquivos testArquivos = arquivosList.get(arquivosList.size() - 1);
        assertThat(testArquivos.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testArquivos.getCaminho()).isEqualTo(UPDATED_CAMINHO);
        assertThat(testArquivos.getMd5()).isEqualTo(UPDATED_MD_5);
        assertThat(testArquivos.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testArquivos.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testArquivos.getTotalLinhas()).isEqualTo(UPDATED_TOTAL_LINHAS);
        assertThat(testArquivos.getCriacao()).isEqualTo(UPDATED_CRIACAO);
        assertThat(testArquivos.getProcessamento()).isEqualTo(UPDATED_PROCESSAMENTO);

        // Validate the Arquivos in Elasticsearch
        verify(mockArquivosSearchRepository, times(1)).save(testArquivos);
    }

    @Test
    @Transactional
    public void updateNonExistingArquivos() throws Exception {
        int databaseSizeBeforeUpdate = arquivosRepository.findAll().size();

        // Create the Arquivos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restArquivosMockMvc.perform(put("/api/arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(arquivos)))
            .andExpect(status().isBadRequest());

        // Validate the Arquivos in the database
        List<Arquivos> arquivosList = arquivosRepository.findAll();
        assertThat(arquivosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Arquivos in Elasticsearch
        verify(mockArquivosSearchRepository, times(0)).save(arquivos);
    }

    @Test
    @Transactional
    public void deleteArquivos() throws Exception {
        // Initialize the database
        arquivosRepository.saveAndFlush(arquivos);

        int databaseSizeBeforeDelete = arquivosRepository.findAll().size();

        // Delete the arquivos
        restArquivosMockMvc.perform(delete("/api/arquivos/{id}", arquivos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Arquivos> arquivosList = arquivosRepository.findAll();
        assertThat(arquivosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Arquivos in Elasticsearch
        verify(mockArquivosSearchRepository, times(1)).deleteById(arquivos.getId());
    }

    @Test
    @Transactional
    public void searchArquivos() throws Exception {
        // Initialize the database
        arquivosRepository.saveAndFlush(arquivos);
        when(mockArquivosSearchRepository.search(queryStringQuery("id:" + arquivos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(arquivos), PageRequest.of(0, 1), 1));
        // Search the arquivos
        restArquivosMockMvc.perform(get("/api/_search/arquivos?query=id:" + arquivos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(arquivos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].caminho").value(hasItem(DEFAULT_CAMINHO)))
            .andExpect(jsonPath("$.[*].md5").value(hasItem(DEFAULT_MD_5)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].totalLinhas").value(hasItem(DEFAULT_TOTAL_LINHAS)))
            .andExpect(jsonPath("$.[*].criacao").value(hasItem(DEFAULT_CRIACAO.toString())))
            .andExpect(jsonPath("$.[*].processamento").value(hasItem(DEFAULT_PROCESSAMENTO.toString())));
    }
}
