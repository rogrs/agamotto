package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.Departamentos;
import br.com.rogrs.repository.DepartamentosRepository;
import br.com.rogrs.repository.search.DepartamentosSearchRepository;

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
 * Integration tests for the {@link DepartamentosResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class DepartamentosResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private DepartamentosRepository departamentosRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.DepartamentosSearchRepositoryMockConfiguration
     */
    @Autowired
    private DepartamentosSearchRepository mockDepartamentosSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDepartamentosMockMvc;

    private Departamentos departamentos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamentos createEntity(EntityManager em) {
        Departamentos departamentos = new Departamentos()
            .nome(DEFAULT_NOME);
        return departamentos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Departamentos createUpdatedEntity(EntityManager em) {
        Departamentos departamentos = new Departamentos()
            .nome(UPDATED_NOME);
        return departamentos;
    }

    @BeforeEach
    public void initTest() {
        departamentos = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepartamentos() throws Exception {
        int databaseSizeBeforeCreate = departamentosRepository.findAll().size();

        // Create the Departamentos
        restDepartamentosMockMvc.perform(post("/api/departamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isCreated());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeCreate + 1);
        Departamentos testDepartamentos = departamentosList.get(departamentosList.size() - 1);
        assertThat(testDepartamentos.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Departamentos in Elasticsearch
        verify(mockDepartamentosSearchRepository, times(1)).save(testDepartamentos);
    }

    @Test
    @Transactional
    public void createDepartamentosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = departamentosRepository.findAll().size();

        // Create the Departamentos with an existing ID
        departamentos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepartamentosMockMvc.perform(post("/api/departamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isBadRequest());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeCreate);

        // Validate the Departamentos in Elasticsearch
        verify(mockDepartamentosSearchRepository, times(0)).save(departamentos);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = departamentosRepository.findAll().size();
        // set the field null
        departamentos.setNome(null);

        // Create the Departamentos, which fails.

        restDepartamentosMockMvc.perform(post("/api/departamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isBadRequest());

        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        // Get all the departamentosList
        restDepartamentosMockMvc.perform(get("/api/departamentos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        // Get the departamentos
        restDepartamentosMockMvc.perform(get("/api/departamentos/{id}", departamentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(departamentos.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingDepartamentos() throws Exception {
        // Get the departamentos
        restDepartamentosMockMvc.perform(get("/api/departamentos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();

        // Update the departamentos
        Departamentos updatedDepartamentos = departamentosRepository.findById(departamentos.getId()).get();
        // Disconnect from session so that the updates on updatedDepartamentos are not directly saved in db
        em.detach(updatedDepartamentos);
        updatedDepartamentos
            .nome(UPDATED_NOME);

        restDepartamentosMockMvc.perform(put("/api/departamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDepartamentos)))
            .andExpect(status().isOk());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);
        Departamentos testDepartamentos = departamentosList.get(departamentosList.size() - 1);
        assertThat(testDepartamentos.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Departamentos in Elasticsearch
        verify(mockDepartamentosSearchRepository, times(1)).save(testDepartamentos);
    }

    @Test
    @Transactional
    public void updateNonExistingDepartamentos() throws Exception {
        int databaseSizeBeforeUpdate = departamentosRepository.findAll().size();

        // Create the Departamentos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepartamentosMockMvc.perform(put("/api/departamentos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(departamentos)))
            .andExpect(status().isBadRequest());

        // Validate the Departamentos in the database
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Departamentos in Elasticsearch
        verify(mockDepartamentosSearchRepository, times(0)).save(departamentos);
    }

    @Test
    @Transactional
    public void deleteDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);

        int databaseSizeBeforeDelete = departamentosRepository.findAll().size();

        // Delete the departamentos
        restDepartamentosMockMvc.perform(delete("/api/departamentos/{id}", departamentos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Departamentos> departamentosList = departamentosRepository.findAll();
        assertThat(departamentosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Departamentos in Elasticsearch
        verify(mockDepartamentosSearchRepository, times(1)).deleteById(departamentos.getId());
    }

    @Test
    @Transactional
    public void searchDepartamentos() throws Exception {
        // Initialize the database
        departamentosRepository.saveAndFlush(departamentos);
        when(mockDepartamentosSearchRepository.search(queryStringQuery("id:" + departamentos.getId())))
            .thenReturn(Collections.singletonList(departamentos));
        // Search the departamentos
        restDepartamentosMockMvc.perform(get("/api/_search/departamentos?query=id:" + departamentos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(departamentos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
}
