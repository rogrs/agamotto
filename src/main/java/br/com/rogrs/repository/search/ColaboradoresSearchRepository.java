package br.com.rogrs.repository.search;
import br.com.rogrs.domain.Colaboradores;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Colaboradores} entity.
 */
public interface ColaboradoresSearchRepository extends ElasticsearchRepository<Colaboradores, Long> {
}
