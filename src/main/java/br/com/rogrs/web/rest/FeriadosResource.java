package br.com.rogrs.web.rest;

import br.com.rogrs.domain.Feriados;
import br.com.rogrs.repository.FeriadosRepository;
import br.com.rogrs.repository.search.FeriadosSearchRepository;
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
 * REST controller for managing {@link br.com.rogrs.domain.Feriados}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class FeriadosResource {

    private final Logger log = LoggerFactory.getLogger(FeriadosResource.class);

    private static final String ENTITY_NAME = "feriados";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FeriadosRepository feriadosRepository;

    private final FeriadosSearchRepository feriadosSearchRepository;

    public FeriadosResource(FeriadosRepository feriadosRepository, FeriadosSearchRepository feriadosSearchRepository) {
        this.feriadosRepository = feriadosRepository;
        this.feriadosSearchRepository = feriadosSearchRepository;
    }

    /**
     * {@code POST  /feriados} : Create a new feriados.
     *
     * @param feriados the feriados to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feriados, or with status {@code 400 (Bad Request)} if the feriados has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/feriados")
    public ResponseEntity<Feriados> createFeriados(@Valid @RequestBody Feriados feriados) throws URISyntaxException {
        log.debug("REST request to save Feriados : {}", feriados);
        if (feriados.getId() != null) {
            throw new BadRequestAlertException("A new feriados cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Feriados result = feriadosRepository.save(feriados);
        feriadosSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/feriados/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /feriados} : Updates an existing feriados.
     *
     * @param feriados the feriados to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feriados,
     * or with status {@code 400 (Bad Request)} if the feriados is not valid,
     * or with status {@code 500 (Internal Server Error)} if the feriados couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/feriados")
    public ResponseEntity<Feriados> updateFeriados(@Valid @RequestBody Feriados feriados) throws URISyntaxException {
        log.debug("REST request to update Feriados : {}", feriados);
        if (feriados.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Feriados result = feriadosRepository.save(feriados);
        feriadosSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, feriados.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /feriados} : get all the feriados.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feriados in body.
     */
    @GetMapping("/feriados")
    public List<Feriados> getAllFeriados() {
        log.debug("REST request to get all Feriados");
        return feriadosRepository.findAll();
    }

    /**
     * {@code GET  /feriados/:id} : get the "id" feriados.
     *
     * @param id the id of the feriados to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feriados, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/feriados/{id}")
    public ResponseEntity<Feriados> getFeriados(@PathVariable Long id) {
        log.debug("REST request to get Feriados : {}", id);
        Optional<Feriados> feriados = feriadosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(feriados);
    }

    /**
     * {@code DELETE  /feriados/:id} : delete the "id" feriados.
     *
     * @param id the id of the feriados to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/feriados/{id}")
    public ResponseEntity<Void> deleteFeriados(@PathVariable Long id) {
        log.debug("REST request to delete Feriados : {}", id);
        feriadosRepository.deleteById(id);
        feriadosSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/feriados?query=:query} : search for the feriados corresponding
     * to the query.
     *
     * @param query the query of the feriados search.
     * @return the result of the search.
     */
    @GetMapping("/_search/feriados")
    public List<Feriados> searchFeriados(@RequestParam String query) {
        log.debug("REST request to search Feriados for query {}", query);
        return StreamSupport
            .stream(feriadosSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
