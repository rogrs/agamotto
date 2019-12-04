package br.com.rogrs.repository.search;
import br.com.rogrs.domain.Arquivos;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link Arquivos} entity.
 */
public interface ArquivosSearchRepository extends ElasticsearchRepository<Arquivos, Long> {
}
