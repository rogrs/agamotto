package br.com.rogrs.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link ControlePontoSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class ControlePontoSearchRepositoryMockConfiguration {

    @MockBean
    private ControlePontoSearchRepository mockControlePontoSearchRepository;

}
