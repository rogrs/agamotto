package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.ControlePonto;
import br.com.rogrs.repository.ControlePontoRepository;
import br.com.rogrs.repository.search.ControlePontoSearchRepository;

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
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ControlePontoResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class ControlePontoResourceIT {

    private static final Duration DEFAULT_DATA = Duration.ofHours(6);
    private static final Duration UPDATED_DATA = Duration.ofHours(12);

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private ControlePontoRepository controlePontoRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.ControlePontoSearchRepositoryMockConfiguration
     */
    @Autowired
    private ControlePontoSearchRepository mockControlePontoSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restControlePontoMockMvc;

    private ControlePonto controlePonto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControlePonto createEntity(EntityManager em) {
        ControlePonto controlePonto = new ControlePonto()
            .data(DEFAULT_DATA)
            .status(DEFAULT_STATUS);
        return controlePonto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ControlePonto createUpdatedEntity(EntityManager em) {
        ControlePonto controlePonto = new ControlePonto()
            .data(UPDATED_DATA)
            .status(UPDATED_STATUS);
        return controlePonto;
    }

    @BeforeEach
    public void initTest() {
        controlePonto = createEntity(em);
    }

    @Test
    @Transactional
    public void createControlePonto() throws Exception {
        int databaseSizeBeforeCreate = controlePontoRepository.findAll().size();

        // Create the ControlePonto
        restControlePontoMockMvc.perform(post("/api/controle-pontos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlePonto)))
            .andExpect(status().isCreated());

        // Validate the ControlePonto in the database
        List<ControlePonto> controlePontoList = controlePontoRepository.findAll();
        assertThat(controlePontoList).hasSize(databaseSizeBeforeCreate + 1);
        ControlePonto testControlePonto = controlePontoList.get(controlePontoList.size() - 1);
        assertThat(testControlePonto.getData()).isEqualTo(DEFAULT_DATA);
        assertThat(testControlePonto.getStatus()).isEqualTo(DEFAULT_STATUS);

        // Validate the ControlePonto in Elasticsearch
        verify(mockControlePontoSearchRepository, times(1)).save(testControlePonto);
    }

    @Test
    @Transactional
    public void createControlePontoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = controlePontoRepository.findAll().size();

        // Create the ControlePonto with an existing ID
        controlePonto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restControlePontoMockMvc.perform(post("/api/controle-pontos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlePonto)))
            .andExpect(status().isBadRequest());

        // Validate the ControlePonto in the database
        List<ControlePonto> controlePontoList = controlePontoRepository.findAll();
        assertThat(controlePontoList).hasSize(databaseSizeBeforeCreate);

        // Validate the ControlePonto in Elasticsearch
        verify(mockControlePontoSearchRepository, times(0)).save(controlePonto);
    }


    @Test
    @Transactional
    public void checkDataIsRequired() throws Exception {
        int databaseSizeBeforeTest = controlePontoRepository.findAll().size();
        // set the field null
        controlePonto.setData(null);

        // Create the ControlePonto, which fails.

        restControlePontoMockMvc.perform(post("/api/controle-pontos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlePonto)))
            .andExpect(status().isBadRequest());

        List<ControlePonto> controlePontoList = controlePontoRepository.findAll();
        assertThat(controlePontoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllControlePontos() throws Exception {
        // Initialize the database
        controlePontoRepository.saveAndFlush(controlePonto);

        // Get all the controlePontoList
        restControlePontoMockMvc.perform(get("/api/controle-pontos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controlePonto.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getControlePonto() throws Exception {
        // Initialize the database
        controlePontoRepository.saveAndFlush(controlePonto);

        // Get the controlePonto
        restControlePontoMockMvc.perform(get("/api/controle-pontos/{id}", controlePonto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(controlePonto.getId().intValue()))
            .andExpect(jsonPath("$.data").value(DEFAULT_DATA.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingControlePonto() throws Exception {
        // Get the controlePonto
        restControlePontoMockMvc.perform(get("/api/controle-pontos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateControlePonto() throws Exception {
        // Initialize the database
        controlePontoRepository.saveAndFlush(controlePonto);

        int databaseSizeBeforeUpdate = controlePontoRepository.findAll().size();

        // Update the controlePonto
        ControlePonto updatedControlePonto = controlePontoRepository.findById(controlePonto.getId()).get();
        // Disconnect from session so that the updates on updatedControlePonto are not directly saved in db
        em.detach(updatedControlePonto);
        updatedControlePonto
            .data(UPDATED_DATA)
            .status(UPDATED_STATUS);

        restControlePontoMockMvc.perform(put("/api/controle-pontos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedControlePonto)))
            .andExpect(status().isOk());

        // Validate the ControlePonto in the database
        List<ControlePonto> controlePontoList = controlePontoRepository.findAll();
        assertThat(controlePontoList).hasSize(databaseSizeBeforeUpdate);
        ControlePonto testControlePonto = controlePontoList.get(controlePontoList.size() - 1);
        assertThat(testControlePonto.getData()).isEqualTo(UPDATED_DATA);
        assertThat(testControlePonto.getStatus()).isEqualTo(UPDATED_STATUS);

        // Validate the ControlePonto in Elasticsearch
        verify(mockControlePontoSearchRepository, times(1)).save(testControlePonto);
    }

    @Test
    @Transactional
    public void updateNonExistingControlePonto() throws Exception {
        int databaseSizeBeforeUpdate = controlePontoRepository.findAll().size();

        // Create the ControlePonto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restControlePontoMockMvc.perform(put("/api/controle-pontos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(controlePonto)))
            .andExpect(status().isBadRequest());

        // Validate the ControlePonto in the database
        List<ControlePonto> controlePontoList = controlePontoRepository.findAll();
        assertThat(controlePontoList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ControlePonto in Elasticsearch
        verify(mockControlePontoSearchRepository, times(0)).save(controlePonto);
    }

    @Test
    @Transactional
    public void deleteControlePonto() throws Exception {
        // Initialize the database
        controlePontoRepository.saveAndFlush(controlePonto);

        int databaseSizeBeforeDelete = controlePontoRepository.findAll().size();

        // Delete the controlePonto
        restControlePontoMockMvc.perform(delete("/api/controle-pontos/{id}", controlePonto.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ControlePonto> controlePontoList = controlePontoRepository.findAll();
        assertThat(controlePontoList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ControlePonto in Elasticsearch
        verify(mockControlePontoSearchRepository, times(1)).deleteById(controlePonto.getId());
    }

    @Test
    @Transactional
    public void searchControlePonto() throws Exception {
        // Initialize the database
        controlePontoRepository.saveAndFlush(controlePonto);
        when(mockControlePontoSearchRepository.search(queryStringQuery("id:" + controlePonto.getId())))
            .thenReturn(Collections.singletonList(controlePonto));
        // Search the controlePonto
        restControlePontoMockMvc.perform(get("/api/_search/controle-pontos?query=id:" + controlePonto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(controlePonto.getId().intValue())))
            .andExpect(jsonPath("$.[*].data").value(hasItem(DEFAULT_DATA.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
}
