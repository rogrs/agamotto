package br.com.rogrs.repository.search;
import br.com.rogrs.domain.Departamentos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Departamentos} entity.
 */
public interface DepartamentosSearchRepository extends ElasticsearchRepository<Departamentos, Long> {
}
