package br.com.rogrs.repository.search;

import br.com.rogrs.domain.Feriados;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Feriados} entity.
 */
public interface FeriadosSearchRepository extends ElasticsearchRepository<Feriados, Long> {
}
