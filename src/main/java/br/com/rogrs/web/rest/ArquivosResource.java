package br.com.rogrs.web.rest;

import br.com.rogrs.domain.Arquivos;
import br.com.rogrs.repository.ArquivosRepository;
import br.com.rogrs.repository.search.ArquivosSearchRepository;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link br.com.rogrs.domain.Arquivos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ArquivosResource {

    private final Logger log = LoggerFactory.getLogger(ArquivosResource.class);

    private static final String ENTITY_NAME = "arquivos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ArquivosRepository arquivosRepository;

    private final ArquivosSearchRepository arquivosSearchRepository;

    public ArquivosResource(ArquivosRepository arquivosRepository, ArquivosSearchRepository arquivosSearchRepository) {
        this.arquivosRepository = arquivosRepository;
        this.arquivosSearchRepository = arquivosSearchRepository;
    }

    /**
     * {@code POST  /arquivos} : Create a new arquivos.
     *
     * @param arquivos the arquivos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new arquivos, or with status {@code 400 (Bad Request)} if the arquivos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/arquivos")
    public ResponseEntity<Arquivos> createArquivos(@Valid @RequestBody Arquivos arquivos) throws URISyntaxException {
        log.debug("REST request to save Arquivos : {}", arquivos);
        if (arquivos.getId() != null) {
            throw new BadRequestAlertException("A new arquivos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Arquivos result = arquivosRepository.save(arquivos);
        arquivosSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/arquivos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /arquivos} : Updates an existing arquivos.
     *
     * @param arquivos the arquivos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated arquivos,
     * or with status {@code 400 (Bad Request)} if the arquivos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the arquivos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/arquivos")
    public ResponseEntity<Arquivos> updateArquivos(@Valid @RequestBody Arquivos arquivos) throws URISyntaxException {
        log.debug("REST request to update Arquivos : {}", arquivos);
        if (arquivos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Arquivos result = arquivosRepository.save(arquivos);
        arquivosSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, arquivos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /arquivos} : get all the arquivos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of arquivos in body.
     */
    @GetMapping("/arquivos")
    public ResponseEntity<List<Arquivos>> getAllArquivos(Pageable pageable) {
        log.debug("REST request to get a page of Arquivos");
        Page<Arquivos> page = arquivosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /arquivos/:id} : get the "id" arquivos.
     *
     * @param id the id of the arquivos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the arquivos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/arquivos/{id}")
    public ResponseEntity<Arquivos> getArquivos(@PathVariable Long id) {
        log.debug("REST request to get Arquivos : {}", id);
        Optional<Arquivos> arquivos = arquivosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(arquivos);
    }

    /**
     * {@code DELETE  /arquivos/:id} : delete the "id" arquivos.
     *
     * @param id the id of the arquivos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/arquivos/{id}")
    public ResponseEntity<Void> deleteArquivos(@PathVariable Long id) {
        log.debug("REST request to delete Arquivos : {}", id);
        arquivosRepository.deleteById(id);
        arquivosSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/arquivos?query=:query} : search for the arquivos corresponding
     * to the query.
     *
     * @param query the query of the arquivos search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/arquivos")
    public ResponseEntity<List<Arquivos>> searchArquivos(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Arquivos for query {}", query);
        Page<Arquivos> page = arquivosSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
