package br.com.rogrs.web.rest;

import br.com.rogrs.domain.Turnos;
import br.com.rogrs.repository.TurnosRepository;
import br.com.rogrs.repository.search.TurnosSearchRepository;
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
 * REST controller for managing {@link br.com.rogrs.domain.Turnos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class TurnosResource {

    private final Logger log = LoggerFactory.getLogger(TurnosResource.class);

    private static final String ENTITY_NAME = "turnos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TurnosRepository turnosRepository;

    private final TurnosSearchRepository turnosSearchRepository;

    public TurnosResource(TurnosRepository turnosRepository, TurnosSearchRepository turnosSearchRepository) {
        this.turnosRepository = turnosRepository;
        this.turnosSearchRepository = turnosSearchRepository;
    }

    /**
     * {@code POST  /turnos} : Create a new turnos.
     *
     * @param turnos the turnos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new turnos, or with status {@code 400 (Bad Request)} if the turnos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/turnos")
    public ResponseEntity<Turnos> createTurnos(@Valid @RequestBody Turnos turnos) throws URISyntaxException {
        log.debug("REST request to save Turnos : {}", turnos);
        if (turnos.getId() != null) {
            throw new BadRequestAlertException("A new turnos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Turnos result = turnosRepository.save(turnos);
        turnosSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/turnos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /turnos} : Updates an existing turnos.
     *
     * @param turnos the turnos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated turnos,
     * or with status {@code 400 (Bad Request)} if the turnos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the turnos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/turnos")
    public ResponseEntity<Turnos> updateTurnos(@Valid @RequestBody Turnos turnos) throws URISyntaxException {
        log.debug("REST request to update Turnos : {}", turnos);
        if (turnos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Turnos result = turnosRepository.save(turnos);
        turnosSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, turnos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /turnos} : get all the turnos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of turnos in body.
     */
    @GetMapping("/turnos")
    public List<Turnos> getAllTurnos() {
        log.debug("REST request to get all Turnos");
        return turnosRepository.findAll();
    }

    /**
     * {@code GET  /turnos/:id} : get the "id" turnos.
     *
     * @param id the id of the turnos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the turnos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/turnos/{id}")
    public ResponseEntity<Turnos> getTurnos(@PathVariable Long id) {
        log.debug("REST request to get Turnos : {}", id);
        Optional<Turnos> turnos = turnosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(turnos);
    }

    /**
     * {@code DELETE  /turnos/:id} : delete the "id" turnos.
     *
     * @param id the id of the turnos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/turnos/{id}")
    public ResponseEntity<Void> deleteTurnos(@PathVariable Long id) {
        log.debug("REST request to delete Turnos : {}", id);
        turnosRepository.deleteById(id);
        turnosSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/turnos?query=:query} : search for the turnos corresponding
     * to the query.
     *
     * @param query the query of the turnos search.
     * @return the result of the search.
     */
    @GetMapping("/_search/turnos")
    public List<Turnos> searchTurnos(@RequestParam String query) {
        log.debug("REST request to search Turnos for query {}", query);
        return StreamSupport
            .stream(turnosSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
