package br.com.rogrs.repository.search;

import br.com.rogrs.domain.Equipes;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Equipes} entity.
 */
public interface EquipesSearchRepository extends ElasticsearchRepository<Equipes, Long> {
}
