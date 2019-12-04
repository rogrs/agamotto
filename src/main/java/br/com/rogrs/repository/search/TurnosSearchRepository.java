package br.com.rogrs.repository.search;
import br.com.rogrs.domain.Turnos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Turnos} entity.
 */
public interface TurnosSearchRepository extends ElasticsearchRepository<Turnos, Long> {
}
