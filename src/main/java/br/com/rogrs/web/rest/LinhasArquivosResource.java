package br.com.rogrs.web.rest;

import br.com.rogrs.domain.LinhasArquivos;
import br.com.rogrs.repository.LinhasArquivosRepository;
import br.com.rogrs.repository.search.LinhasArquivosSearchRepository;
import br.com.rogrs.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.rogrs.domain.LinhasArquivos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class LinhasArquivosResource {

    private final Logger log = LoggerFactory.getLogger(LinhasArquivosResource.class);

    private static final String ENTITY_NAME = "linhasArquivos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LinhasArquivosRepository linhasArquivosRepository;

    private final LinhasArquivosSearchRepository linhasArquivosSearchRepository;

    public LinhasArquivosResource(LinhasArquivosRepository linhasArquivosRepository, LinhasArquivosSearchRepository linhasArquivosSearchRepository) {
        this.linhasArquivosRepository = linhasArquivosRepository;
        this.linhasArquivosSearchRepository = linhasArquivosSearchRepository;
    }

    /**
     * {@code POST  /linhas-arquivos} : Create a new linhasArquivos.
     *
     * @param linhasArquivos the linhasArquivos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new linhasArquivos, or with status {@code 400 (Bad Request)} if the linhasArquivos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/linhas-arquivos")
    public ResponseEntity<LinhasArquivos> createLinhasArquivos(@RequestBody LinhasArquivos linhasArquivos) throws URISyntaxException {
        log.debug("REST request to save LinhasArquivos : {}", linhasArquivos);
        if (linhasArquivos.getId() != null) {
            throw new BadRequestAlertException("A new linhasArquivos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LinhasArquivos result = linhasArquivosRepository.save(linhasArquivos);
        linhasArquivosSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/linhas-arquivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /linhas-arquivos} : Updates an existing linhasArquivos.
     *
     * @param linhasArquivos the linhasArquivos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated linhasArquivos,
     * or with status {@code 400 (Bad Request)} if the linhasArquivos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the linhasArquivos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/linhas-arquivos")
    public ResponseEntity<LinhasArquivos> updateLinhasArquivos(@RequestBody LinhasArquivos linhasArquivos) throws URISyntaxException {
        log.debug("REST request to update LinhasArquivos : {}", linhasArquivos);
        if (linhasArquivos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LinhasArquivos result = linhasArquivosRepository.save(linhasArquivos);
        linhasArquivosSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, linhasArquivos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /linhas-arquivos} : get all the linhasArquivos.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of linhasArquivos in body.
     */
    @GetMapping("/linhas-arquivos")
    public ResponseEntity<List<LinhasArquivos>> getAllLinhasArquivos(Pageable pageable) {
        log.debug("REST request to get a page of LinhasArquivos");
        Page<LinhasArquivos> page = linhasArquivosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /linhas-arquivos/:id} : get the "id" linhasArquivos.
     *
     * @param id the id of the linhasArquivos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the linhasArquivos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/linhas-arquivos/{id}")
    public ResponseEntity<LinhasArquivos> getLinhasArquivos(@PathVariable Long id) {
        log.debug("REST request to get LinhasArquivos : {}", id);
        Optional<LinhasArquivos> linhasArquivos = linhasArquivosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(linhasArquivos);
    }

    /**
     * {@code DELETE  /linhas-arquivos/:id} : delete the "id" linhasArquivos.
     *
     * @param id the id of the linhasArquivos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/linhas-arquivos/{id}")
    public ResponseEntity<Void> deleteLinhasArquivos(@PathVariable Long id) {
        log.debug("REST request to delete LinhasArquivos : {}", id);
        linhasArquivosRepository.deleteById(id);
        linhasArquivosSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/linhas-arquivos?query=:query} : search for the linhasArquivos corresponding
     * to the query.
     *
     * @param query the query of the linhasArquivos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/linhas-arquivos")
    public ResponseEntity<List<LinhasArquivos>> searchLinhasArquivos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LinhasArquivos for query {}", query);
        Page<LinhasArquivos> page = linhasArquivosSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
