package br.com.rogrs.web.rest;

import br.com.rogrs.domain.Colaboradores;
import br.com.rogrs.repository.ColaboradoresRepository;
import br.com.rogrs.repository.search.ColaboradoresSearchRepository;
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
 * REST controller for managing {@link br.com.rogrs.domain.Colaboradores}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ColaboradoresResource {

    private final Logger log = LoggerFactory.getLogger(ColaboradoresResource.class);

    private static final String ENTITY_NAME = "colaboradores";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColaboradoresRepository colaboradoresRepository;

    private final ColaboradoresSearchRepository colaboradoresSearchRepository;

    public ColaboradoresResource(ColaboradoresRepository colaboradoresRepository, ColaboradoresSearchRepository colaboradoresSearchRepository) {
        this.colaboradoresRepository = colaboradoresRepository;
        this.colaboradoresSearchRepository = colaboradoresSearchRepository;
    }

    /**
     * {@code POST  /colaboradores} : Create a new colaboradores.
     *
     * @param colaboradores the colaboradores to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new colaboradores, or with status {@code 400 (Bad Request)} if the colaboradores has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/colaboradores")
    public ResponseEntity<Colaboradores> createColaboradores(@Valid @RequestBody Colaboradores colaboradores) throws URISyntaxException {
        log.debug("REST request to save Colaboradores : {}", colaboradores);
        if (colaboradores.getId() != null) {
            throw new BadRequestAlertException("A new colaboradores cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Colaboradores result = colaboradoresRepository.save(colaboradores);
        colaboradoresSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/colaboradores/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /colaboradores} : Updates an existing colaboradores.
     *
     * @param colaboradores the colaboradores to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated colaboradores,
     * or with status {@code 400 (Bad Request)} if the colaboradores is not valid,
     * or with status {@code 500 (Internal Server Error)} if the colaboradores couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/colaboradores")
    public ResponseEntity<Colaboradores> updateColaboradores(@Valid @RequestBody Colaboradores colaboradores) throws URISyntaxException {
        log.debug("REST request to update Colaboradores : {}", colaboradores);
        if (colaboradores.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Colaboradores result = colaboradoresRepository.save(colaboradores);
        colaboradoresSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, colaboradores.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /colaboradores} : get all the colaboradores.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of colaboradores in body.
     */
    @GetMapping("/colaboradores")
    public ResponseEntity<List<Colaboradores>> getAllColaboradores(Pageable pageable) {
        log.debug("REST request to get a page of Colaboradores");
        Page<Colaboradores> page = colaboradoresRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /colaboradores/:id} : get the "id" colaboradores.
     *
     * @param id the id of the colaboradores to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the colaboradores, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/colaboradores/{id}")
    public ResponseEntity<Colaboradores> getColaboradores(@PathVariable Long id) {
        log.debug("REST request to get Colaboradores : {}", id);
        Optional<Colaboradores> colaboradores = colaboradoresRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(colaboradores);
    }

    /**
     * {@code DELETE  /colaboradores/:id} : delete the "id" colaboradores.
     *
     * @param id the id of the colaboradores to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/colaboradores/{id}")
    public ResponseEntity<Void> deleteColaboradores(@PathVariable Long id) {
        log.debug("REST request to delete Colaboradores : {}", id);
        colaboradoresRepository.deleteById(id);
        colaboradoresSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/colaboradores?query=:query} : search for the colaboradores corresponding
     * to the query.
     *
     * @param query the query of the colaboradores search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/colaboradores")
    public ResponseEntity<List<Colaboradores>> searchColaboradores(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Colaboradores for query {}", query);
        Page<Colaboradores> page = colaboradoresSearchRepository.search(queryStringQuery(query), pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }
}
