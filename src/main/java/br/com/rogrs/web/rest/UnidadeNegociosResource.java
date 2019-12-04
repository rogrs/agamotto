package br.com.rogrs.web.rest;

import br.com.rogrs.domain.UnidadeNegocios;
import br.com.rogrs.repository.UnidadeNegociosRepository;
import br.com.rogrs.repository.search.UnidadeNegociosSearchRepository;
import br.com.rogrs.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
 * REST controller for managing {@link br.com.rogrs.domain.UnidadeNegocios}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class UnidadeNegociosResource {

    private final Logger log = LoggerFactory.getLogger(UnidadeNegociosResource.class);

    private static final String ENTITY_NAME = "unidadeNegocios";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final UnidadeNegociosRepository unidadeNegociosRepository;

    private final UnidadeNegociosSearchRepository unidadeNegociosSearchRepository;

    public UnidadeNegociosResource(UnidadeNegociosRepository unidadeNegociosRepository, UnidadeNegociosSearchRepository unidadeNegociosSearchRepository) {
        this.unidadeNegociosRepository = unidadeNegociosRepository;
        this.unidadeNegociosSearchRepository = unidadeNegociosSearchRepository;
    }

    /**
     * {@code POST  /unidade-negocios} : Create a new unidadeNegocios.
     *
     * @param unidadeNegocios the unidadeNegocios to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new unidadeNegocios, or with status {@code 400 (Bad Request)} if the unidadeNegocios has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/unidade-negocios")
    public ResponseEntity<UnidadeNegocios> createUnidadeNegocios(@Valid @RequestBody UnidadeNegocios unidadeNegocios) throws URISyntaxException {
        log.debug("REST request to save UnidadeNegocios : {}", unidadeNegocios);
        if (unidadeNegocios.getId() != null) {
            throw new BadRequestAlertException("A new unidadeNegocios cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UnidadeNegocios result = unidadeNegociosRepository.save(unidadeNegocios);
        unidadeNegociosSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/unidade-negocios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /unidade-negocios} : Updates an existing unidadeNegocios.
     *
     * @param unidadeNegocios the unidadeNegocios to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated unidadeNegocios,
     * or with status {@code 400 (Bad Request)} if the unidadeNegocios is not valid,
     * or with status {@code 500 (Internal Server Error)} if the unidadeNegocios couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/unidade-negocios")
    public ResponseEntity<UnidadeNegocios> updateUnidadeNegocios(@Valid @RequestBody UnidadeNegocios unidadeNegocios) throws URISyntaxException {
        log.debug("REST request to update UnidadeNegocios : {}", unidadeNegocios);
        if (unidadeNegocios.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UnidadeNegocios result = unidadeNegociosRepository.save(unidadeNegocios);
        unidadeNegociosSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, unidadeNegocios.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /unidade-negocios} : get all the unidadeNegocios.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of unidadeNegocios in body.
     */
    @GetMapping("/unidade-negocios")
    public List<UnidadeNegocios> getAllUnidadeNegocios() {
        log.debug("REST request to get all UnidadeNegocios");
        return unidadeNegociosRepository.findAll();
    }

    /**
     * {@code GET  /unidade-negocios/:id} : get the "id" unidadeNegocios.
     *
     * @param id the id of the unidadeNegocios to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the unidadeNegocios, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/unidade-negocios/{id}")
    public ResponseEntity<UnidadeNegocios> getUnidadeNegocios(@PathVariable Long id) {
        log.debug("REST request to get UnidadeNegocios : {}", id);
        Optional<UnidadeNegocios> unidadeNegocios = unidadeNegociosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(unidadeNegocios);
    }

    /**
     * {@code DELETE  /unidade-negocios/:id} : delete the "id" unidadeNegocios.
     *
     * @param id the id of the unidadeNegocios to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/unidade-negocios/{id}")
    public ResponseEntity<Void> deleteUnidadeNegocios(@PathVariable Long id) {
        log.debug("REST request to delete UnidadeNegocios : {}", id);
        unidadeNegociosRepository.deleteById(id);
        unidadeNegociosSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/unidade-negocios?query=:query} : search for the unidadeNegocios corresponding
     * to the query.
     *
     * @param query the query of the unidadeNegocios search.
     * @return the result of the search.
     */
    @GetMapping("/_search/unidade-negocios")
    public List<UnidadeNegocios> searchUnidadeNegocios(@RequestParam String query) {
        log.debug("REST request to search UnidadeNegocios for query {}", query);
        return StreamSupport
            .stream(unidadeNegociosSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
