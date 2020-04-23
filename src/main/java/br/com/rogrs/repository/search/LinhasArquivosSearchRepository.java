package br.com.rogrs.repository.search;

import br.com.rogrs.domain.LinhasArquivos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link LinhasArquivos} entity.
 */
public interface LinhasArquivosSearchRepository extends ElasticsearchRepository<LinhasArquivos, Long> {
}
