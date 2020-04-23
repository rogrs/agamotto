package br.com.rogrs.web.rest;

import br.com.rogrs.domain.ControlePonto;
import br.com.rogrs.repository.ControlePontoRepository;
import br.com.rogrs.repository.search.ControlePontoSearchRepository;
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
 * REST controller for managing {@link br.com.rogrs.domain.ControlePonto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ControlePontoResource {

    private final Logger log = LoggerFactory.getLogger(ControlePontoResource.class);

    private static final String ENTITY_NAME = "controlePonto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ControlePontoRepository controlePontoRepository;

    private final ControlePontoSearchRepository controlePontoSearchRepository;

    public ControlePontoResource(ControlePontoRepository controlePontoRepository, ControlePontoSearchRepository controlePontoSearchRepository) {
        this.controlePontoRepository = controlePontoRepository;
        this.controlePontoSearchRepository = controlePontoSearchRepository;
    }

    /**
     * {@code POST  /controle-pontos} : Create a new controlePonto.
     *
     * @param controlePonto the controlePonto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new controlePonto, or with status {@code 400 (Bad Request)} if the controlePonto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/controle-pontos")
    public ResponseEntity<ControlePonto> createControlePonto(@Valid @RequestBody ControlePonto controlePonto) throws URISyntaxException {
        log.debug("REST request to save ControlePonto : {}", controlePonto);
        if (controlePonto.getId() != null) {
            throw new BadRequestAlertException("A new controlePonto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ControlePonto result = controlePontoRepository.save(controlePonto);
        controlePontoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/controle-pontos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /controle-pontos} : Updates an existing controlePonto.
     *
     * @param controlePonto the controlePonto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated controlePonto,
     * or with status {@code 400 (Bad Request)} if the controlePonto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the controlePonto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/controle-pontos")
    public ResponseEntity<ControlePonto> updateControlePonto(@Valid @RequestBody ControlePonto controlePonto) throws URISyntaxException {
        log.debug("REST request to update ControlePonto : {}", controlePonto);
        if (controlePonto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ControlePonto result = controlePontoRepository.save(controlePonto);
        controlePontoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, controlePonto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /controle-pontos} : get all the controlePontos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of controlePontos in body.
     */
    @GetMapping("/controle-pontos")
    public List<ControlePonto> getAllControlePontos() {
        log.debug("REST request to get all ControlePontos");
        return controlePontoRepository.findAll();
    }

    /**
     * {@code GET  /controle-pontos/:id} : get the "id" controlePonto.
     *
     * @param id the id of the controlePonto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the controlePonto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/controle-pontos/{id}")
    public ResponseEntity<ControlePonto> getControlePonto(@PathVariable Long id) {
        log.debug("REST request to get ControlePonto : {}", id);
        Optional<ControlePonto> controlePonto = controlePontoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(controlePonto);
    }

    /**
     * {@code DELETE  /controle-pontos/:id} : delete the "id" controlePonto.
     *
     * @param id the id of the controlePonto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/controle-pontos/{id}")
    public ResponseEntity<Void> deleteControlePonto(@PathVariable Long id) {
        log.debug("REST request to delete ControlePonto : {}", id);
        controlePontoRepository.deleteById(id);
        controlePontoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/controle-pontos?query=:query} : search for the controlePonto corresponding
     * to the query.
     *
     * @param query the query of the controlePonto search.
     * @return the result of the search.
     */
    @GetMapping("/_search/controle-pontos")
    public List<ControlePonto> searchControlePontos(@RequestParam String query) {
        log.debug("REST request to search ControlePontos for query {}", query);
        return StreamSupport
            .stream(controlePontoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
