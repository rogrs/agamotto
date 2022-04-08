package br.com.rogrs.agamotto.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import br.com.rogrs.agamotto.domain.LinhasArquivos;
import br.com.rogrs.agamotto.exception.BadRequestAlertException;
import br.com.rogrs.agamotto.exception.NotFoundException;
import br.com.rogrs.agamotto.repository.LinhasArquivosRepository;
import br.com.rogrs.agamotto.web.rest.utils.HeaderUtil;
import br.com.rogrs.agamotto.web.rest.utils.PaginationUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class LinhasArquivosController {

	    private static final String ENTITY_NAME = "linhas";

	    private final LinhasArquivosRepository repository;

	    public LinhasArquivosController(LinhasArquivosRepository repository) {
	        this.repository = repository;
	    }

	    @PostMapping("/linhas")
	    public ResponseEntity<LinhasArquivos> create(@RequestBody LinhasArquivos obj) throws URISyntaxException {
	        log.debug("REST request to save {}: {}",ENTITY_NAME, obj);
	        if (obj.getId() != null) {
	            throw new BadRequestAlertException("A new  cannot already have an ID", ENTITY_NAME, "idexists");
	        }
	        LinhasArquivos result = repository.save(obj);
	        return ResponseEntity.created(new URI("/api/cargos/" + result.getId()))
	            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
	            .body(result);
	    }

	    @PutMapping("/linhas")
	    public ResponseEntity<LinhasArquivos> update(@RequestBody LinhasArquivos obj) throws URISyntaxException {
	        log.debug("REST request to update {}: {}",ENTITY_NAME, obj);
	        if (obj.getId() == null) {
	            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
	        }
	        LinhasArquivos result = repository.save(obj);
	        return ResponseEntity.ok()
	            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, obj.getId().toString()))
	            .body(result);
	    }
	 
	    @GetMapping("/linhas")
	    public ResponseEntity<List<LinhasArquivos>> getAll(Pageable pageable) {
	        log.debug("REST request to get a page of {}",ENTITY_NAME);
	        Page<LinhasArquivos> page = repository.findAll(pageable);
	        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cargos");
	        return ResponseEntity.ok().headers(headers).body(page.getContent());
	    }

	    @GetMapping("/linhas/{id}")	    
	    public ResponseEntity<LinhasArquivos> get(@PathVariable Long id) {
	        log.debug("REST request to get {}: {}",ENTITY_NAME, id);
	        Optional<LinhasArquivos> obj = repository.findById(id);
	        return ResponseEntity.ok().body(obj.get());
	    }

	  
	    @DeleteMapping("/linhas/{id}")	    
	    public ResponseEntity<Void> delete(@PathVariable Long id) {
	        log.debug("REST request to delete {}: {}",ENTITY_NAME, id);

	        repository.deleteById(id);
	        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
	    }

		@GetMapping("/linhas/pis/{pis}")
	    public ResponseEntity<List<LinhasArquivos>> getLinhasByPIS(@PathVariable String pis) {
	        log.debug("REST request to get {}: {}",ENTITY_NAME, pis);
	        List<LinhasArquivos> linhas = repository.findByPis(pis);

			if( linhas.isEmpty()){
				throw new NotFoundException(
				  HttpStatus.NOT_FOUND, ENTITY_NAME+" Not Found");

			}
	        return  ResponseEntity.ok().body(linhas);
		
	    }
}
