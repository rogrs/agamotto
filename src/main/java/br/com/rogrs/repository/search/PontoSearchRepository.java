package br.com.rogrs.repository.search;

import br.com.rogrs.domain.Ponto;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Ponto} entity.
 */
public interface PontoSearchRepository extends ElasticsearchRepository<Ponto, Long> {
}
