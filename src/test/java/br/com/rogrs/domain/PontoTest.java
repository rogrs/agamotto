package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class PontoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ponto.class);
        Ponto ponto1 = new Ponto();
        ponto1.setId(1L);
        Ponto ponto2 = new Ponto();
        ponto2.setId(ponto1.getId());
        assertThat(ponto1).isEqualTo(ponto2);
        ponto2.setId(2L);
        assertThat(ponto1).isNotEqualTo(ponto2);
        ponto1.setId(null);
        assertThat(ponto1).isNotEqualTo(ponto2);
    }
}
