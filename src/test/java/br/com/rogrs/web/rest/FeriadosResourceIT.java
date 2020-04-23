package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.Feriados;
import br.com.rogrs.repository.FeriadosRepository;
import br.com.rogrs.repository.search.FeriadosSearchRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

/**
 * Integration tests for the {@link FeriadosResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class FeriadosResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FIXO_OU_MUDA_TODO_ANO = false;
    private static final Boolean UPDATED_FIXO_OU_MUDA_TODO_ANO = true;

    private static final LocalDate DEFAULT_DATA_FERIADO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_FERIADO = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private FeriadosRepository feriadosRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.FeriadosSearchRepositoryMockConfiguration
     */
    @Autowired
    private FeriadosSearchRepository mockFeriadosSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFeriadosMockMvc;

    private Feriados feriados;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feriados createEntity(EntityManager em) {
        Feriados feriados = new Feriados()
            .nome(DEFAULT_NOME)
            .fixoOuMudaTodoAno(DEFAULT_FIXO_OU_MUDA_TODO_ANO)
            .dataFeriado(DEFAULT_DATA_FERIADO);
        return feriados;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Feriados createUpdatedEntity(EntityManager em) {
        Feriados feriados = new Feriados()
            .nome(UPDATED_NOME)
            .fixoOuMudaTodoAno(UPDATED_FIXO_OU_MUDA_TODO_ANO)
            .dataFeriado(UPDATED_DATA_FERIADO);
        return feriados;
    }

    @BeforeEach
    public void initTest() {
        feriados = createEntity(em);
    }

    @Test
    @Transactional
    public void createFeriados() throws Exception {
        int databaseSizeBeforeCreate = feriadosRepository.findAll().size();

        // Create the Feriados
        restFeriadosMockMvc.perform(post("/api/feriados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feriados)))
            .andExpect(status().isCreated());

        // Validate the Feriados in the database
        List<Feriados> feriadosList = feriadosRepository.findAll();
        assertThat(feriadosList).hasSize(databaseSizeBeforeCreate + 1);
        Feriados testFeriados = feriadosList.get(feriadosList.size() - 1);
        assertThat(testFeriados.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testFeriados.isFixoOuMudaTodoAno()).isEqualTo(DEFAULT_FIXO_OU_MUDA_TODO_ANO);
        assertThat(testFeriados.getDataFeriado()).isEqualTo(DEFAULT_DATA_FERIADO);

        // Validate the Feriados in Elasticsearch
        verify(mockFeriadosSearchRepository, times(1)).save(testFeriados);
    }

    @Test
    @Transactional
    public void createFeriadosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = feriadosRepository.findAll().size();

        // Create the Feriados with an existing ID
        feriados.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFeriadosMockMvc.perform(post("/api/feriados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feriados)))
            .andExpect(status().isBadRequest());

        // Validate the Feriados in the database
        List<Feriados> feriadosList = feriadosRepository.findAll();
        assertThat(feriadosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Feriados in Elasticsearch
        verify(mockFeriadosSearchRepository, times(0)).save(feriados);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = feriadosRepository.findAll().size();
        // set the field null
        feriados.setNome(null);

        // Create the Feriados, which fails.

        restFeriadosMockMvc.perform(post("/api/feriados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feriados)))
            .andExpect(status().isBadRequest());

        List<Feriados> feriadosList = feriadosRepository.findAll();
        assertThat(feriadosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFixoOuMudaTodoAnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = feriadosRepository.findAll().size();
        // set the field null
        feriados.setFixoOuMudaTodoAno(null);

        // Create the Feriados, which fails.

        restFeriadosMockMvc.perform(post("/api/feriados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feriados)))
            .andExpect(status().isBadRequest());

        List<Feriados> feriadosList = feriadosRepository.findAll();
        assertThat(feriadosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDataFeriadoIsRequired() throws Exception {
        int databaseSizeBeforeTest = feriadosRepository.findAll().size();
        // set the field null
        feriados.setDataFeriado(null);

        // Create the Feriados, which fails.

        restFeriadosMockMvc.perform(post("/api/feriados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feriados)))
            .andExpect(status().isBadRequest());

        List<Feriados> feriadosList = feriadosRepository.findAll();
        assertThat(feriadosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFeriados() throws Exception {
        // Initialize the database
        feriadosRepository.saveAndFlush(feriados);

        // Get all the feriadosList
        restFeriadosMockMvc.perform(get("/api/feriados?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feriados.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].fixoOuMudaTodoAno").value(hasItem(DEFAULT_FIXO_OU_MUDA_TODO_ANO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataFeriado").value(hasItem(DEFAULT_DATA_FERIADO.toString())));
    }
    
    @Test
    @Transactional
    public void getFeriados() throws Exception {
        // Initialize the database
        feriadosRepository.saveAndFlush(feriados);

        // Get the feriados
        restFeriadosMockMvc.perform(get("/api/feriados/{id}", feriados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(feriados.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME))
            .andExpect(jsonPath("$.fixoOuMudaTodoAno").value(DEFAULT_FIXO_OU_MUDA_TODO_ANO.booleanValue()))
            .andExpect(jsonPath("$.dataFeriado").value(DEFAULT_DATA_FERIADO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFeriados() throws Exception {
        // Get the feriados
        restFeriadosMockMvc.perform(get("/api/feriados/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFeriados() throws Exception {
        // Initialize the database
        feriadosRepository.saveAndFlush(feriados);

        int databaseSizeBeforeUpdate = feriadosRepository.findAll().size();

        // Update the feriados
        Feriados updatedFeriados = feriadosRepository.findById(feriados.getId()).get();
        // Disconnect from session so that the updates on updatedFeriados are not directly saved in db
        em.detach(updatedFeriados);
        updatedFeriados
            .nome(UPDATED_NOME)
            .fixoOuMudaTodoAno(UPDATED_FIXO_OU_MUDA_TODO_ANO)
            .dataFeriado(UPDATED_DATA_FERIADO);

        restFeriadosMockMvc.perform(put("/api/feriados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedFeriados)))
            .andExpect(status().isOk());

        // Validate the Feriados in the database
        List<Feriados> feriadosList = feriadosRepository.findAll();
        assertThat(feriadosList).hasSize(databaseSizeBeforeUpdate);
        Feriados testFeriados = feriadosList.get(feriadosList.size() - 1);
        assertThat(testFeriados.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testFeriados.isFixoOuMudaTodoAno()).isEqualTo(UPDATED_FIXO_OU_MUDA_TODO_ANO);
        assertThat(testFeriados.getDataFeriado()).isEqualTo(UPDATED_DATA_FERIADO);

        // Validate the Feriados in Elasticsearch
        verify(mockFeriadosSearchRepository, times(1)).save(testFeriados);
    }

    @Test
    @Transactional
    public void updateNonExistingFeriados() throws Exception {
        int databaseSizeBeforeUpdate = feriadosRepository.findAll().size();

        // Create the Feriados

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFeriadosMockMvc.perform(put("/api/feriados")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(feriados)))
            .andExpect(status().isBadRequest());

        // Validate the Feriados in the database
        List<Feriados> feriadosList = feriadosRepository.findAll();
        assertThat(feriadosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Feriados in Elasticsearch
        verify(mockFeriadosSearchRepository, times(0)).save(feriados);
    }

    @Test
    @Transactional
    public void deleteFeriados() throws Exception {
        // Initialize the database
        feriadosRepository.saveAndFlush(feriados);

        int databaseSizeBeforeDelete = feriadosRepository.findAll().size();

        // Delete the feriados
        restFeriadosMockMvc.perform(delete("/api/feriados/{id}", feriados.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Feriados> feriadosList = feriadosRepository.findAll();
        assertThat(feriadosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Feriados in Elasticsearch
        verify(mockFeriadosSearchRepository, times(1)).deleteById(feriados.getId());
    }

    @Test
    @Transactional
    public void searchFeriados() throws Exception {
        // Initialize the database
        feriadosRepository.saveAndFlush(feriados);
        when(mockFeriadosSearchRepository.search(queryStringQuery("id:" + feriados.getId())))
            .thenReturn(Collections.singletonList(feriados));
        // Search the feriados
        restFeriadosMockMvc.perform(get("/api/_search/feriados?query=id:" + feriados.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(feriados.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)))
            .andExpect(jsonPath("$.[*].fixoOuMudaTodoAno").value(hasItem(DEFAULT_FIXO_OU_MUDA_TODO_ANO.booleanValue())))
            .andExpect(jsonPath("$.[*].dataFeriado").value(hasItem(DEFAULT_DATA_FERIADO.toString())));
    }
}
