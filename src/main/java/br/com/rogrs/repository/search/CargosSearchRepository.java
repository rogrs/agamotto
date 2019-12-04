package br.com.rogrs.repository.search;
import br.com.rogrs.domain.Cargos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Cargos} entity.
 */
public interface CargosSearchRepository extends ElasticsearchRepository<Cargos, Long> {
}
