package br.com.rogrs.repository.search;

import br.com.rogrs.domain.UnidadeNegocios;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link UnidadeNegocios} entity.
 */
public interface UnidadeNegociosSearchRepository extends ElasticsearchRepository<UnidadeNegocios, Long> {
}
