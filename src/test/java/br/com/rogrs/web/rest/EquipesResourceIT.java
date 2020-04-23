package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.Equipes;
import br.com.rogrs.repository.EquipesRepository;
import br.com.rogrs.repository.search.EquipesSearchRepository;

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
 * Integration tests for the {@link EquipesResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class EquipesResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    @Autowired
    private EquipesRepository equipesRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.EquipesSearchRepositoryMockConfiguration
     */
    @Autowired
    private EquipesSearchRepository mockEquipesSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEquipesMockMvc;

    private Equipes equipes;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipes createEntity(EntityManager em) {
        Equipes equipes = new Equipes()
            .nome(DEFAULT_NOME);
        return equipes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Equipes createUpdatedEntity(EntityManager em) {
        Equipes equipes = new Equipes()
            .nome(UPDATED_NOME);
        return equipes;
    }

    @BeforeEach
    public void initTest() {
        equipes = createEntity(em);
    }

    @Test
    @Transactional
    public void createEquipes() throws Exception {
        int databaseSizeBeforeCreate = equipesRepository.findAll().size();

        // Create the Equipes
        restEquipesMockMvc.perform(post("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipes)))
            .andExpect(status().isCreated());

        // Validate the Equipes in the database
        List<Equipes> equipesList = equipesRepository.findAll();
        assertThat(equipesList).hasSize(databaseSizeBeforeCreate + 1);
        Equipes testEquipes = equipesList.get(equipesList.size() - 1);
        assertThat(testEquipes.getNome()).isEqualTo(DEFAULT_NOME);

        // Validate the Equipes in Elasticsearch
        verify(mockEquipesSearchRepository, times(1)).save(testEquipes);
    }

    @Test
    @Transactional
    public void createEquipesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = equipesRepository.findAll().size();

        // Create the Equipes with an existing ID
        equipes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEquipesMockMvc.perform(post("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipes)))
            .andExpect(status().isBadRequest());

        // Validate the Equipes in the database
        List<Equipes> equipesList = equipesRepository.findAll();
        assertThat(equipesList).hasSize(databaseSizeBeforeCreate);

        // Validate the Equipes in Elasticsearch
        verify(mockEquipesSearchRepository, times(0)).save(equipes);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = equipesRepository.findAll().size();
        // set the field null
        equipes.setNome(null);

        // Create the Equipes, which fails.

        restEquipesMockMvc.perform(post("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipes)))
            .andExpect(status().isBadRequest());

        List<Equipes> equipesList = equipesRepository.findAll();
        assertThat(equipesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEquipes() throws Exception {
        // Initialize the database
        equipesRepository.saveAndFlush(equipes);

        // Get all the equipesList
        restEquipesMockMvc.perform(get("/api/equipes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
    
    @Test
    @Transactional
    public void getEquipes() throws Exception {
        // Initialize the database
        equipesRepository.saveAndFlush(equipes);

        // Get the equipes
        restEquipesMockMvc.perform(get("/api/equipes/{id}", equipes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(equipes.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    public void getNonExistingEquipes() throws Exception {
        // Get the equipes
        restEquipesMockMvc.perform(get("/api/equipes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEquipes() throws Exception {
        // Initialize the database
        equipesRepository.saveAndFlush(equipes);

        int databaseSizeBeforeUpdate = equipesRepository.findAll().size();

        // Update the equipes
        Equipes updatedEquipes = equipesRepository.findById(equipes.getId()).get();
        // Disconnect from session so that the updates on updatedEquipes are not directly saved in db
        em.detach(updatedEquipes);
        updatedEquipes
            .nome(UPDATED_NOME);

        restEquipesMockMvc.perform(put("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEquipes)))
            .andExpect(status().isOk());

        // Validate the Equipes in the database
        List<Equipes> equipesList = equipesRepository.findAll();
        assertThat(equipesList).hasSize(databaseSizeBeforeUpdate);
        Equipes testEquipes = equipesList.get(equipesList.size() - 1);
        assertThat(testEquipes.getNome()).isEqualTo(UPDATED_NOME);

        // Validate the Equipes in Elasticsearch
        verify(mockEquipesSearchRepository, times(1)).save(testEquipes);
    }

    @Test
    @Transactional
    public void updateNonExistingEquipes() throws Exception {
        int databaseSizeBeforeUpdate = equipesRepository.findAll().size();

        // Create the Equipes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEquipesMockMvc.perform(put("/api/equipes")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(equipes)))
            .andExpect(status().isBadRequest());

        // Validate the Equipes in the database
        List<Equipes> equipesList = equipesRepository.findAll();
        assertThat(equipesList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Equipes in Elasticsearch
        verify(mockEquipesSearchRepository, times(0)).save(equipes);
    }

    @Test
    @Transactional
    public void deleteEquipes() throws Exception {
        // Initialize the database
        equipesRepository.saveAndFlush(equipes);

        int databaseSizeBeforeDelete = equipesRepository.findAll().size();

        // Delete the equipes
        restEquipesMockMvc.perform(delete("/api/equipes/{id}", equipes.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Equipes> equipesList = equipesRepository.findAll();
        assertThat(equipesList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Equipes in Elasticsearch
        verify(mockEquipesSearchRepository, times(1)).deleteById(equipes.getId());
    }

    @Test
    @Transactional
    public void searchEquipes() throws Exception {
        // Initialize the database
        equipesRepository.saveAndFlush(equipes);
        when(mockEquipesSearchRepository.search(queryStringQuery("id:" + equipes.getId())))
            .thenReturn(Collections.singletonList(equipes));
        // Search the equipes
        restEquipesMockMvc.perform(get("/api/_search/equipes?query=id:" + equipes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(equipes.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }
}
