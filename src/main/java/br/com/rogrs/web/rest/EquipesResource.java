package br.com.rogrs.web.rest;

import br.com.rogrs.domain.Equipes;
import br.com.rogrs.repository.EquipesRepository;
import br.com.rogrs.repository.search.EquipesSearchRepository;
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
 * REST controller for managing {@link br.com.rogrs.domain.Equipes}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EquipesResource {

    private final Logger log = LoggerFactory.getLogger(EquipesResource.class);

    private static final String ENTITY_NAME = "equipes";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EquipesRepository equipesRepository;

    private final EquipesSearchRepository equipesSearchRepository;

    public EquipesResource(EquipesRepository equipesRepository, EquipesSearchRepository equipesSearchRepository) {
        this.equipesRepository = equipesRepository;
        this.equipesSearchRepository = equipesSearchRepository;
    }

    /**
     * {@code POST  /equipes} : Create a new equipes.
     *
     * @param equipes the equipes to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new equipes, or with status {@code 400 (Bad Request)} if the equipes has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/equipes")
    public ResponseEntity<Equipes> createEquipes(@Valid @RequestBody Equipes equipes) throws URISyntaxException {
        log.debug("REST request to save Equipes : {}", equipes);
        if (equipes.getId() != null) {
            throw new BadRequestAlertException("A new equipes cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Equipes result = equipesRepository.save(equipes);
        equipesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/equipes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /equipes} : Updates an existing equipes.
     *
     * @param equipes the equipes to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated equipes,
     * or with status {@code 400 (Bad Request)} if the equipes is not valid,
     * or with status {@code 500 (Internal Server Error)} if the equipes couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/equipes")
    public ResponseEntity<Equipes> updateEquipes(@Valid @RequestBody Equipes equipes) throws URISyntaxException {
        log.debug("REST request to update Equipes : {}", equipes);
        if (equipes.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Equipes result = equipesRepository.save(equipes);
        equipesSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, equipes.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /equipes} : get all the equipes.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of equipes in body.
     */
    @GetMapping("/equipes")
    public List<Equipes> getAllEquipes() {
        log.debug("REST request to get all Equipes");
        return equipesRepository.findAll();
    }

    /**
     * {@code GET  /equipes/:id} : get the "id" equipes.
     *
     * @param id the id of the equipes to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the equipes, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/equipes/{id}")
    public ResponseEntity<Equipes> getEquipes(@PathVariable Long id) {
        log.debug("REST request to get Equipes : {}", id);
        Optional<Equipes> equipes = equipesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(equipes);
    }

    /**
     * {@code DELETE  /equipes/:id} : delete the "id" equipes.
     *
     * @param id the id of the equipes to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/equipes/{id}")
    public ResponseEntity<Void> deleteEquipes(@PathVariable Long id) {
        log.debug("REST request to delete Equipes : {}", id);
        equipesRepository.deleteById(id);
        equipesSearchRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/equipes?query=:query} : search for the equipes corresponding
     * to the query.
     *
     * @param query the query of the equipes search.
     * @return the result of the search.
     */
    @GetMapping("/_search/equipes")
    public List<Equipes> searchEquipes(@RequestParam String query) {
        log.debug("REST request to search Equipes for query {}", query);
        return StreamSupport
            .stream(equipesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
