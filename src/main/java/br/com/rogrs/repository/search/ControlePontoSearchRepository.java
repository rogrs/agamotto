package br.com.rogrs.repository.search;

import br.com.rogrs.domain.ControlePonto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ControlePonto} entity.
 */
public interface ControlePontoSearchRepository extends ElasticsearchRepository<ControlePonto, Long> {
}
