package br.com.rogrs.web.rest;

import br.com.rogrs.domain.Departamentos;
import br.com.rogrs.repository.DepartamentosRepository;
import br.com.rogrs.repository.search.DepartamentosSearchRepository;
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
 * REST controller for managing {@link br.com.rogrs.domain.Departamentos}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class DepartamentosResource {

    private final Logger log = LoggerFactory.getLogger(DepartamentosResource.class);

    private static final String ENTITY_NAME = "departamentos";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepartamentosRepository departamentosRepository;

    private final DepartamentosSearchRepository departamentosSearchRepository;

    public DepartamentosResource(DepartamentosRepository departamentosRepository, DepartamentosSearchRepository departamentosSearchRepository) {
        this.departamentosRepository = departamentosRepository;
        this.departamentosSearchRepository = departamentosSearchRepository;
    }

    /**
     * {@code POST  /departamentos} : Create a new departamentos.
     *
     * @param departamentos the departamentos to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new departamentos, or with status {@code 400 (Bad Request)} if the departamentos has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/departamentos")
    public ResponseEntity<Departamentos> createDepartamentos(@Valid @RequestBody Departamentos departamentos) throws URISyntaxException {
        log.debug("REST request to save Departamentos : {}", departamentos);
        if (departamentos.getId() != null) {
            throw new BadRequestAlertException("A new departamentos cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Departamentos result = departamentosRepository.save(departamentos);
        departamentosSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/departamentos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /departamentos} : Updates an existing departamentos.
     *
     * @param departamentos the departamentos to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated departamentos,
     * or with status {@code 400 (Bad Request)} if the departamentos is not valid,
     * or with status {@code 500 (Internal Server Error)} if the departamentos couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/departamentos")
    public ResponseEntity<Departamentos> updateDepartamentos(@Valid @RequestBody Departamentos departamentos) throws URISyntaxException {
        log.debug("REST request to update Departamentos : {}", departamentos);
        if (departamentos.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Departamentos result = departamentosRepository.save(departamentos);
        departamentosSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, departamentos.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /departamentos} : get all the departamentos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of departamentos in body.
     */
    @GetMapping("/departamentos")
    public List<Departamentos> getAllDepartamentos() {
        log.debug("REST request to get all Departamentos");
        return departamentosRepository.findAll();
    }

    /**
     * {@code GET  /departamentos/:id} : get the "id" departamentos.
     *
     * @param id the id of the departamentos to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the departamentos, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/departamentos/{id}")
    public ResponseEntity<Departamentos> getDepartamentos(@PathVariable Long id) {
        log.debug("REST request to get Departamentos : {}", id);
        Optional<Departamentos> departamentos = departamentosRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(departamentos);
    }

    /**
     * {@code DELETE  /departamentos/:id} : delete the "id" departamentos.
     *
     * @param id the id of the departamentos to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/departamentos/{id}")
    public ResponseEntity<Void> deleteDepartamentos(@PathVariable Long id) {
        log.debug("REST request to delete Departamentos : {}", id);
        departamentosRepository.deleteById(id);
        departamentosSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/departamentos?query=:query} : search for the departamentos corresponding
     * to the query.
     *
     * @param query the query of the departamentos search.
     * @return the result of the search.
     */
    @GetMapping("/_search/departamentos")
    public List<Departamentos> searchDepartamentos(@RequestParam String query) {
        log.debug("REST request to search Departamentos for query {}", query);
        return StreamSupport
            .stream(departamentosSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
