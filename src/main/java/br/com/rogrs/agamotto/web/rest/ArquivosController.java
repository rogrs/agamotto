package br.com.rogrs.agamotto.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import br.com.rogrs.agamotto.domain.Arquivos;
import br.com.rogrs.agamotto.exception.BadRequestAlertException;
import br.com.rogrs.agamotto.repository.ArquivosRepository;
import br.com.rogrs.agamotto.web.rest.utils.HeaderUtil;
import br.com.rogrs.agamotto.web.rest.utils.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
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
public class ArquivosController {

	    private static final String ENTITY_NAME = "arquivo";

	    private final ArquivosRepository repository;

	    public ArquivosController(ArquivosRepository repository) {
	        this.repository = repository;
	    }

	    @PostMapping("/arquivos")
	    public ResponseEntity<Arquivos> create(@RequestBody Arquivos obj) throws URISyntaxException {
	        log.debug("REST request to save {}: {}",ENTITY_NAME, obj);
	        if (obj.getId() != null) {
	            throw new BadRequestAlertException("A new  cannot already have an ID", ENTITY_NAME, "idexists");
	        }
	        Arquivos result = repository.save(obj);
	        return ResponseEntity.created(new URI("/api/arquivos/" + result.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
	            .body(result);
	    }

	    @PutMapping("/arquivos")
	    public ResponseEntity<Arquivos> update(@RequestBody Arquivos obj) throws URISyntaxException {
	        log.debug("REST request to update {}: {}",ENTITY_NAME, obj);
	        if (obj.getId() == null) {
	            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
	        }
	        Arquivos result = repository.save(obj);
	        return ResponseEntity.ok()
	            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, obj.getId().toString()))
	            .body(result);
	    }
	 
	    @GetMapping("/arquivos")
	    public ResponseEntity<List<Arquivos>> getAll(Pageable pageable) {
	        log.debug("REST request to get a page of {}",ENTITY_NAME);
	        Page<Arquivos> page = repository.findAll(pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/arquivos");
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }

	    @GetMapping("/arquivos/{id}")	    
	    public ResponseEntity<Arquivos> get(@PathVariable Long id) {
	        log.debug("REST request to get {}: {}",ENTITY_NAME, id);
	        Optional<Arquivos> obj = repository.findById(id);
	        return ResponseEntity.ok().body(obj.get());
	    }

	  
	    @DeleteMapping("/arquivos/{id}")	    
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        log.debug("REST request to delete {}: {}",ENTITY_NAME, id);

	        repository.deleteById(id);
	        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	    }

}
