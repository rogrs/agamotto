package br.com.rogrs.web.rest;

import br.com.rogrs.AgamottoApp;
import br.com.rogrs.domain.LinhasArquivos;
import br.com.rogrs.repository.LinhasArquivosRepository;
import br.com.rogrs.repository.search.LinhasArquivosSearchRepository;

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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.rogrs.domain.enumeration.TipoRegistro;
import br.com.rogrs.domain.enumeration.TipoOperacao;
/**
 * Integration tests for the {@link LinhasArquivosResource} REST controller.
 */
@SpringBootTest(classes = AgamottoApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class LinhasArquivosResourceIT {

    private static final String DEFAULT_NSR = "AAAAAAAAAA";
    private static final String UPDATED_NSR = "BBBBBBBBBB";

    private static final String DEFAULT_TIPO = "AAAAAAAAAA";
    private static final String UPDATED_TIPO = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_PONTO = "AAAAAAAAAA";
    private static final String UPDATED_DATA_PONTO = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_PONTO = "AAAAAAAAAA";
    private static final String UPDATED_HORA_PONTO = "BBBBBBBBBB";

    private static final String DEFAULT_DATA_AJUSTADA = "AAAAAAAAAA";
    private static final String UPDATED_DATA_AJUSTADA = "BBBBBBBBBB";

    private static final String DEFAULT_HORA_AJUSTADA = "AAAAAAAAAA";
    private static final String UPDATED_HORA_AJUSTADA = "BBBBBBBBBB";

    private static final String DEFAULT_PIS = "AAAAAAAAAA";
    private static final String UPDATED_PIS = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_EMPREGADO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_EMPREGADO = "BBBBBBBBBB";

    private static final String DEFAULT_LINHA = "AAAAAAAAAA";
    private static final String UPDATED_LINHA = "BBBBBBBBBB";

    private static final TipoRegistro DEFAULT_TIPO_REGISTRO = TipoRegistro.CABECALHO;
    private static final TipoRegistro UPDATED_TIPO_REGISTRO = TipoRegistro.INCLUSAO_ALTERACAO;

    private static final TipoOperacao DEFAULT_TIPO_OPERACAO = TipoOperacao.INCLUSAO;
    private static final TipoOperacao UPDATED_TIPO_OPERACAO = TipoOperacao.ALTERACAO;

    @Autowired
    private LinhasArquivosRepository linhasArquivosRepository;

    /**
     * This repository is mocked in the br.com.rogrs.repository.search test package.
     *
     * @see br.com.rogrs.repository.search.LinhasArquivosSearchRepositoryMockConfiguration
     */
    @Autowired
    private LinhasArquivosSearchRepository mockLinhasArquivosSearchRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLinhasArquivosMockMvc;

    private LinhasArquivos linhasArquivos;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinhasArquivos createEntity(EntityManager em) {
        LinhasArquivos linhasArquivos = new LinhasArquivos()
            .nsr(DEFAULT_NSR)
            .tipo(DEFAULT_TIPO)
            .dataPonto(DEFAULT_DATA_PONTO)
            .horaPonto(DEFAULT_HORA_PONTO)
            .dataAjustada(DEFAULT_DATA_AJUSTADA)
            .horaAjustada(DEFAULT_HORA_AJUSTADA)
            .pis(DEFAULT_PIS)
            .nomeEmpregado(DEFAULT_NOME_EMPREGADO)
            .linha(DEFAULT_LINHA)
            .tipoRegistro(DEFAULT_TIPO_REGISTRO)
            .tipoOperacao(DEFAULT_TIPO_OPERACAO);
        return linhasArquivos;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LinhasArquivos createUpdatedEntity(EntityManager em) {
        LinhasArquivos linhasArquivos = new LinhasArquivos()
            .nsr(UPDATED_NSR)
            .tipo(UPDATED_TIPO)
            .dataPonto(UPDATED_DATA_PONTO)
            .horaPonto(UPDATED_HORA_PONTO)
            .dataAjustada(UPDATED_DATA_AJUSTADA)
            .horaAjustada(UPDATED_HORA_AJUSTADA)
            .pis(UPDATED_PIS)
            .nomeEmpregado(UPDATED_NOME_EMPREGADO)
            .linha(UPDATED_LINHA)
            .tipoRegistro(UPDATED_TIPO_REGISTRO)
            .tipoOperacao(UPDATED_TIPO_OPERACAO);
        return linhasArquivos;
    }

    @BeforeEach
    public void initTest() {
        linhasArquivos = createEntity(em);
    }

    @Test
    @Transactional
    public void createLinhasArquivos() throws Exception {
        int databaseSizeBeforeCreate = linhasArquivosRepository.findAll().size();

        // Create the LinhasArquivos
        restLinhasArquivosMockMvc.perform(post("/api/linhas-arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linhasArquivos)))
            .andExpect(status().isCreated());

        // Validate the LinhasArquivos in the database
        List<LinhasArquivos> linhasArquivosList = linhasArquivosRepository.findAll();
        assertThat(linhasArquivosList).hasSize(databaseSizeBeforeCreate + 1);
        LinhasArquivos testLinhasArquivos = linhasArquivosList.get(linhasArquivosList.size() - 1);
        assertThat(testLinhasArquivos.getNsr()).isEqualTo(DEFAULT_NSR);
        assertThat(testLinhasArquivos.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testLinhasArquivos.getDataPonto()).isEqualTo(DEFAULT_DATA_PONTO);
        assertThat(testLinhasArquivos.getHoraPonto()).isEqualTo(DEFAULT_HORA_PONTO);
        assertThat(testLinhasArquivos.getDataAjustada()).isEqualTo(DEFAULT_DATA_AJUSTADA);
        assertThat(testLinhasArquivos.getHoraAjustada()).isEqualTo(DEFAULT_HORA_AJUSTADA);
        assertThat(testLinhasArquivos.getPis()).isEqualTo(DEFAULT_PIS);
        assertThat(testLinhasArquivos.getNomeEmpregado()).isEqualTo(DEFAULT_NOME_EMPREGADO);
        assertThat(testLinhasArquivos.getLinha()).isEqualTo(DEFAULT_LINHA);
        assertThat(testLinhasArquivos.getTipoRegistro()).isEqualTo(DEFAULT_TIPO_REGISTRO);
        assertThat(testLinhasArquivos.getTipoOperacao()).isEqualTo(DEFAULT_TIPO_OPERACAO);

        // Validate the LinhasArquivos in Elasticsearch
        verify(mockLinhasArquivosSearchRepository, times(1)).save(testLinhasArquivos);
    }

    @Test
    @Transactional
    public void createLinhasArquivosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = linhasArquivosRepository.findAll().size();

        // Create the LinhasArquivos with an existing ID
        linhasArquivos.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLinhasArquivosMockMvc.perform(post("/api/linhas-arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linhasArquivos)))
            .andExpect(status().isBadRequest());

        // Validate the LinhasArquivos in the database
        List<LinhasArquivos> linhasArquivosList = linhasArquivosRepository.findAll();
        assertThat(linhasArquivosList).hasSize(databaseSizeBeforeCreate);

        // Validate the LinhasArquivos in Elasticsearch
        verify(mockLinhasArquivosSearchRepository, times(0)).save(linhasArquivos);
    }


    @Test
    @Transactional
    public void getAllLinhasArquivos() throws Exception {
        // Initialize the database
        linhasArquivosRepository.saveAndFlush(linhasArquivos);

        // Get all the linhasArquivosList
        restLinhasArquivosMockMvc.perform(get("/api/linhas-arquivos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linhasArquivos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nsr").value(hasItem(DEFAULT_NSR)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].dataPonto").value(hasItem(DEFAULT_DATA_PONTO)))
            .andExpect(jsonPath("$.[*].horaPonto").value(hasItem(DEFAULT_HORA_PONTO)))
            .andExpect(jsonPath("$.[*].dataAjustada").value(hasItem(DEFAULT_DATA_AJUSTADA)))
            .andExpect(jsonPath("$.[*].horaAjustada").value(hasItem(DEFAULT_HORA_AJUSTADA)))
            .andExpect(jsonPath("$.[*].pis").value(hasItem(DEFAULT_PIS)))
            .andExpect(jsonPath("$.[*].nomeEmpregado").value(hasItem(DEFAULT_NOME_EMPREGADO)))
            .andExpect(jsonPath("$.[*].linha").value(hasItem(DEFAULT_LINHA)))
            .andExpect(jsonPath("$.[*].tipoRegistro").value(hasItem(DEFAULT_TIPO_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].tipoOperacao").value(hasItem(DEFAULT_TIPO_OPERACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getLinhasArquivos() throws Exception {
        // Initialize the database
        linhasArquivosRepository.saveAndFlush(linhasArquivos);

        // Get the linhasArquivos
        restLinhasArquivosMockMvc.perform(get("/api/linhas-arquivos/{id}", linhasArquivos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(linhasArquivos.getId().intValue()))
            .andExpect(jsonPath("$.nsr").value(DEFAULT_NSR))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO))
            .andExpect(jsonPath("$.dataPonto").value(DEFAULT_DATA_PONTO))
            .andExpect(jsonPath("$.horaPonto").value(DEFAULT_HORA_PONTO))
            .andExpect(jsonPath("$.dataAjustada").value(DEFAULT_DATA_AJUSTADA))
            .andExpect(jsonPath("$.horaAjustada").value(DEFAULT_HORA_AJUSTADA))
            .andExpect(jsonPath("$.pis").value(DEFAULT_PIS))
            .andExpect(jsonPath("$.nomeEmpregado").value(DEFAULT_NOME_EMPREGADO))
            .andExpect(jsonPath("$.linha").value(DEFAULT_LINHA))
            .andExpect(jsonPath("$.tipoRegistro").value(DEFAULT_TIPO_REGISTRO.toString()))
            .andExpect(jsonPath("$.tipoOperacao").value(DEFAULT_TIPO_OPERACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLinhasArquivos() throws Exception {
        // Get the linhasArquivos
        restLinhasArquivosMockMvc.perform(get("/api/linhas-arquivos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLinhasArquivos() throws Exception {
        // Initialize the database
        linhasArquivosRepository.saveAndFlush(linhasArquivos);

        int databaseSizeBeforeUpdate = linhasArquivosRepository.findAll().size();

        // Update the linhasArquivos
        LinhasArquivos updatedLinhasArquivos = linhasArquivosRepository.findById(linhasArquivos.getId()).get();
        // Disconnect from session so that the updates on updatedLinhasArquivos are not directly saved in db
        em.detach(updatedLinhasArquivos);
        updatedLinhasArquivos
            .nsr(UPDATED_NSR)
            .tipo(UPDATED_TIPO)
            .dataPonto(UPDATED_DATA_PONTO)
            .horaPonto(UPDATED_HORA_PONTO)
            .dataAjustada(UPDATED_DATA_AJUSTADA)
            .horaAjustada(UPDATED_HORA_AJUSTADA)
            .pis(UPDATED_PIS)
            .nomeEmpregado(UPDATED_NOME_EMPREGADO)
            .linha(UPDATED_LINHA)
            .tipoRegistro(UPDATED_TIPO_REGISTRO)
            .tipoOperacao(UPDATED_TIPO_OPERACAO);

        restLinhasArquivosMockMvc.perform(put("/api/linhas-arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLinhasArquivos)))
            .andExpect(status().isOk());

        // Validate the LinhasArquivos in the database
        List<LinhasArquivos> linhasArquivosList = linhasArquivosRepository.findAll();
        assertThat(linhasArquivosList).hasSize(databaseSizeBeforeUpdate);
        LinhasArquivos testLinhasArquivos = linhasArquivosList.get(linhasArquivosList.size() - 1);
        assertThat(testLinhasArquivos.getNsr()).isEqualTo(UPDATED_NSR);
        assertThat(testLinhasArquivos.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testLinhasArquivos.getDataPonto()).isEqualTo(UPDATED_DATA_PONTO);
        assertThat(testLinhasArquivos.getHoraPonto()).isEqualTo(UPDATED_HORA_PONTO);
        assertThat(testLinhasArquivos.getDataAjustada()).isEqualTo(UPDATED_DATA_AJUSTADA);
        assertThat(testLinhasArquivos.getHoraAjustada()).isEqualTo(UPDATED_HORA_AJUSTADA);
        assertThat(testLinhasArquivos.getPis()).isEqualTo(UPDATED_PIS);
        assertThat(testLinhasArquivos.getNomeEmpregado()).isEqualTo(UPDATED_NOME_EMPREGADO);
        assertThat(testLinhasArquivos.getLinha()).isEqualTo(UPDATED_LINHA);
        assertThat(testLinhasArquivos.getTipoRegistro()).isEqualTo(UPDATED_TIPO_REGISTRO);
        assertThat(testLinhasArquivos.getTipoOperacao()).isEqualTo(UPDATED_TIPO_OPERACAO);

        // Validate the LinhasArquivos in Elasticsearch
        verify(mockLinhasArquivosSearchRepository, times(1)).save(testLinhasArquivos);
    }

    @Test
    @Transactional
    public void updateNonExistingLinhasArquivos() throws Exception {
        int databaseSizeBeforeUpdate = linhasArquivosRepository.findAll().size();

        // Create the LinhasArquivos

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLinhasArquivosMockMvc.perform(put("/api/linhas-arquivos")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(linhasArquivos)))
            .andExpect(status().isBadRequest());

        // Validate the LinhasArquivos in the database
        List<LinhasArquivos> linhasArquivosList = linhasArquivosRepository.findAll();
        assertThat(linhasArquivosList).hasSize(databaseSizeBeforeUpdate);

        // Validate the LinhasArquivos in Elasticsearch
        verify(mockLinhasArquivosSearchRepository, times(0)).save(linhasArquivos);
    }

    @Test
    @Transactional
    public void deleteLinhasArquivos() throws Exception {
        // Initialize the database
        linhasArquivosRepository.saveAndFlush(linhasArquivos);

        int databaseSizeBeforeDelete = linhasArquivosRepository.findAll().size();

        // Delete the linhasArquivos
        restLinhasArquivosMockMvc.perform(delete("/api/linhas-arquivos/{id}", linhasArquivos.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LinhasArquivos> linhasArquivosList = linhasArquivosRepository.findAll();
        assertThat(linhasArquivosList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the LinhasArquivos in Elasticsearch
        verify(mockLinhasArquivosSearchRepository, times(1)).deleteById(linhasArquivos.getId());
    }

    @Test
    @Transactional
    public void searchLinhasArquivos() throws Exception {
        // Initialize the database
        linhasArquivosRepository.saveAndFlush(linhasArquivos);
        when(mockLinhasArquivosSearchRepository.search(queryStringQuery("id:" + linhasArquivos.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(linhasArquivos), PageRequest.of(0, 1), 1));
        // Search the linhasArquivos
        restLinhasArquivosMockMvc.perform(get("/api/_search/linhas-arquivos?query=id:" + linhasArquivos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(linhasArquivos.getId().intValue())))
            .andExpect(jsonPath("$.[*].nsr").value(hasItem(DEFAULT_NSR)))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO)))
            .andExpect(jsonPath("$.[*].dataPonto").value(hasItem(DEFAULT_DATA_PONTO)))
            .andExpect(jsonPath("$.[*].horaPonto").value(hasItem(DEFAULT_HORA_PONTO)))
            .andExpect(jsonPath("$.[*].dataAjustada").value(hasItem(DEFAULT_DATA_AJUSTADA)))
            .andExpect(jsonPath("$.[*].horaAjustada").value(hasItem(DEFAULT_HORA_AJUSTADA)))
            .andExpect(jsonPath("$.[*].pis").value(hasItem(DEFAULT_PIS)))
            .andExpect(jsonPath("$.[*].nomeEmpregado").value(hasItem(DEFAULT_NOME_EMPREGADO)))
            .andExpect(jsonPath("$.[*].linha").value(hasItem(DEFAULT_LINHA)))
            .andExpect(jsonPath("$.[*].tipoRegistro").value(hasItem(DEFAULT_TIPO_REGISTRO.toString())))
            .andExpect(jsonPath("$.[*].tipoOperacao").value(hasItem(DEFAULT_TIPO_OPERACAO.toString())));
    }
}
