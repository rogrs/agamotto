package br.com.rogrs.web.rest;

import br.com.rogrs.domain.Ponto;
import br.com.rogrs.repository.PontoRepository;
import br.com.rogrs.repository.search.PontoSearchRepository;
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
 * REST controller for managing {@link br.com.rogrs.domain.Ponto}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class PontoResource {

    private final Logger log = LoggerFactory.getLogger(PontoResource.class);

    private static final String ENTITY_NAME = "ponto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PontoRepository pontoRepository;

    private final PontoSearchRepository pontoSearchRepository;

    public PontoResource(PontoRepository pontoRepository, PontoSearchRepository pontoSearchRepository) {
        this.pontoRepository = pontoRepository;
        this.pontoSearchRepository = pontoSearchRepository;
    }

    /**
     * {@code POST  /pontos} : Create a new ponto.
     *
     * @param ponto the ponto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new ponto, or with status {@code 400 (Bad Request)} if the ponto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/pontos")
    public ResponseEntity<Ponto> createPonto(@Valid @RequestBody Ponto ponto) throws URISyntaxException {
        log.debug("REST request to save Ponto : {}", ponto);
        if (ponto.getId() != null) {
            throw new BadRequestAlertException("A new ponto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Ponto result = pontoRepository.save(ponto);
        pontoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pontos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /pontos} : Updates an existing ponto.
     *
     * @param ponto the ponto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated ponto,
     * or with status {@code 400 (Bad Request)} if the ponto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the ponto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/pontos")
    public ResponseEntity<Ponto> updatePonto(@Valid @RequestBody Ponto ponto) throws URISyntaxException {
        log.debug("REST request to update Ponto : {}", ponto);
        if (ponto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Ponto result = pontoRepository.save(ponto);
        pontoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, ponto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /pontos} : get all the pontos.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of pontos in body.
     */
    @GetMapping("/pontos")
    public List<Ponto> getAllPontos() {
        log.debug("REST request to get all Pontos");
        return pontoRepository.findAll();
    }

    /**
     * {@code GET  /pontos/:id} : get the "id" ponto.
     *
     * @param id the id of the ponto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the ponto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/pontos/{id}")
    public ResponseEntity<Ponto> getPonto(@PathVariable Long id) {
        log.debug("REST request to get Ponto : {}", id);
        Optional<Ponto> ponto = pontoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(ponto);
    }

    /**
     * {@code DELETE  /pontos/:id} : delete the "id" ponto.
     *
     * @param id the id of the ponto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/pontos/{id}")
    public ResponseEntity<Void> deletePonto(@PathVariable Long id) {
        log.debug("REST request to delete Ponto : {}", id);
        pontoRepository.deleteById(id);
        pontoSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/pontos?query=:query} : search for the ponto corresponding
     * to the query.
     *
     * @param query the query of the ponto search.
     * @return the result of the search.
     */
    @GetMapping("/_search/pontos")
    public List<Ponto> searchPontos(@RequestParam String query) {
        log.debug("REST request to search Pontos for query {}", query);
        return StreamSupport
            .stream(pontoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
