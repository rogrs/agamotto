package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.Cargos;
import br.com.rogrs.repository.CargosRepository;
import br.com.rogrs.repository.search.CargosSearchRepository;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CargosResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class CargosResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private CargosRepository cargosRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.CargosSearchRepositoryMockConfiguration
     */
    @Autowired
    private CargosSearchRepository mockCargosSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCargosMockMvc;

    private Cargos cargos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cargos createEntity(EntityManager em) {
        Cargos cargos = new Cargos()
            .nome(DEFAULT_NOME);
        return cargos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cargos createUpdatedEntity(EntityManager em) {
        Cargos cargos = new Cargos()
            .nome(UPDATED_NOME);
        return cargos;
    }

    @BeforeEach
    public void initTest() {
        cargos = createEntity(em);
    }

    @Test
    @Transactional
    public void createCargos() throws Exception {
        int databaseSizeBeforeCreate = cargosRepository.findAll().size();

        // Create the Cargos
        restCargosMockMvc.perform(post("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargos)))
            .andExpect(status().isCreated());

        // Validate the Cargos in the database
        List<Cargos> cargosList = cargosRepository.findAll();
        assertThat(cargosList).hasSize(databaseSizeBeforeCreate + 1);
        Cargos testCargos = cargosList.get(cargosList.size() - 1);
        assertThat(testCargos.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Cargos in Elasticsearch
        verify(mockCargosSearchRepository, times(1)).save(testCargos);
    }

    @Test
    @Transactional
    public void createCargosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cargosRepository.findAll().size();

        // Create the Cargos with an existing ID
        cargos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCargosMockMvc.perform(post("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargos)))
            .andExpect(status().isBadRequest());

        // Validate the Cargos in the database
        List<Cargos> cargosList = cargosRepository.findAll();
        assertThat(cargosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Cargos in Elasticsearch
        verify(mockCargosSearchRepository, times(0)).save(cargos);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cargosRepository.findAll().size();
        // set the field null
        cargos.setNome(null);

        // Create the Cargos, which fails.

        restCargosMockMvc.perform(post("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargos)))
            .andExpect(status().isBadRequest());

        List<Cargos> cargosList = cargosRepository.findAll();
        assertThat(cargosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCargos() throws Exception {
        // Initialize the database
        cargosRepository.saveAndFlush(cargos);

        // Get all the cargosList
        restCargosMockMvc.perform(get("/api/cargos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cargos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getCargos() throws Exception {
        // Initialize the database
        cargosRepository.saveAndFlush(cargos);

        // Get the cargos
        restCargosMockMvc.perform(get("/api/cargos/{id}", cargos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(cargos.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingCargos() throws Exception {
        // Get the cargos
        restCargosMockMvc.perform(get("/api/cargos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCargos() throws Exception {
        // Initialize the database
        cargosRepository.saveAndFlush(cargos);

        int databaseSizeBeforeUpdate = cargosRepository.findAll().size();

        // Update the cargos
        Cargos updatedCargos = cargosRepository.findById(cargos.getId()).get();
        // Disconnect from session so that the updates on updatedCargos are not directly saved in db
        em.detach(updatedCargos);
        updatedCargos
            .nome(UPDATED_NOME);

        restCargosMockMvc.perform(put("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedCargos)))
            .andExpect(status().isOk());

        // Validate the Cargos in the database
        List<Cargos> cargosList = cargosRepository.findAll();
        assertThat(cargosList).hasSize(databaseSizeBeforeUpdate);
        Cargos testCargos = cargosList.get(cargosList.size() - 1);
        assertThat(testCargos.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Cargos in Elasticsearch
        verify(mockCargosSearchRepository, times(1)).save(testCargos);
    }

    @Test
    @Transactional
    public void updateNonExistingCargos() throws Exception {
        int databaseSizeBeforeUpdate = cargosRepository.findAll().size();

        // Create the Cargos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCargosMockMvc.perform(put("/api/cargos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(cargos)))
            .andExpect(status().isBadRequest());

        // Validate the Cargos in the database
        List<Cargos> cargosList = cargosRepository.findAll();
        assertThat(cargosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Cargos in Elasticsearch
        verify(mockCargosSearchRepository, times(0)).save(cargos);
    }

    @Test
    @Transactional
    public void deleteCargos() throws Exception {
        // Initialize the database
        cargosRepository.saveAndFlush(cargos);

        int databaseSizeBeforeDelete = cargosRepository.findAll().size();

        // Delete the cargos
        restCargosMockMvc.perform(delete("/api/cargos/{id}", cargos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Cargos> cargosList = cargosRepository.findAll();
        assertThat(cargosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Cargos in Elasticsearch
        verify(mockCargosSearchRepository, times(1)).deleteById(cargos.getId());
    }

    @Test
    @Transactional
    public void searchCargos() throws Exception {
        // Initialize the database
        cargosRepository.saveAndFlush(cargos);
        when(mockCargosSearchRepository.search(queryStringQuery("id:" + cargos.getId())))
            .thenReturn(Collections.singletonList(cargos));
        // Search the cargos
        restCargosMockMvc.perform(get("/api/_search/cargos?query=id:" + cargos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cargos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
}
