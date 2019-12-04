package br.com.rogrs.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ArquivosSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ArquivosSearchRepositoryMockConfiguration {

    @MockBean
    private ArquivosSearchRepository mockArquivosSearchRepository;

}
