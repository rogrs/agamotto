package br.com.rogrs.agamotto.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import br.com.rogrs.agamotto.domain.Colaboradores;
import br.com.rogrs.agamotto.exception.BadRequestAlertException;
import br.com.rogrs.agamotto.exception.NotFoundException;
import br.com.rogrs.agamotto.repository.ColaboradoresRepository;
import br.com.rogrs.agamotto.web.rest.utils.HeaderUtil;
import br.com.rogrs.agamotto.web.rest.utils.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
@Slf4j
public class ColaboradoresController {

	    private static final String ENTITY_NAME = "colaborador";

	    private final ColaboradoresRepository repository;

	    public ColaboradoresController(ColaboradoresRepository repository) {
	        this.repository = repository;
	    }

	    @PostMapping("/colaboradores")
	    public ResponseEntity<Colaboradores> create(@RequestBody Colaboradores obj) throws URISyntaxException {
	        log.debug("REST request to save {}: {}",ENTITY_NAME, obj);
	        if (obj.getId() != null) {
	            throw new BadRequestAlertException("A new  cannot already have an ID", ENTITY_NAME, "idexists");
	        }
	        Colaboradores result = repository.save(obj);
	        return ResponseEntity.created(new URI("/api/colaboradores/" + result.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
	            .body(result);
	    }

	    @PutMapping("/colaboradores")
	    public ResponseEntity<Colaboradores> update(@RequestBody Colaboradores obj) throws URISyntaxException {
	        log.debug("REST request to update {}: {}",ENTITY_NAME, obj);
	        if (obj.getId() == null) {
	            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
	        }
	        Colaboradores result = repository.save(obj);
	        return ResponseEntity.ok()
	            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, obj.getId().toString()))
	            .body(result);
	    }
	 
	    @GetMapping("/colaboradores")
	    public ResponseEntity<List<Colaboradores>> getAll(Pageable pageable) {
	        log.debug("REST request to get a page of {}",ENTITY_NAME);
	        Page<Colaboradores> page = repository.findAll(pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/colaboradores");
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }

	    @GetMapping("/colaboradores/{id}")	    
	    public ResponseEntity<Colaboradores> get(@PathVariable Long id) {
	        log.debug("REST request to get {}: {}",ENTITY_NAME, id);
	        Optional<Colaboradores> obj = repository.findById(id);
	        return ResponseEntity.ok().body(obj.get());
	    }

	  
	    @DeleteMapping("/colaboradores/{id}")	    
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        log.debug("REST request to delete {}: {}",ENTITY_NAME, id);

	        repository.deleteById(id);
	        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	    }


	    @GetMapping("/colaboradores/pis/{pis}")	    
	    public ResponseEntity<Colaboradores> getByPis(@PathVariable String pis) {
	        log.debug("REST request to get {}: {}",ENTITY_NAME, pis);
	        Colaboradores obj = repository.findByPis(pis);	
			
			if( obj == null){
				throw new NotFoundException(
				  HttpStatus.NOT_FOUND, ENTITY_NAME+" Not Found");

			}
	        return  ResponseEntity.ok().body(obj);
		
	    }

}
