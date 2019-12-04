package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class UnidadeNegociosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UnidadeNegocios.class);
        UnidadeNegocios unidadeNegocios1 = new UnidadeNegocios();
        unidadeNegocios1.setId(1L);
        UnidadeNegocios unidadeNegocios2 = new UnidadeNegocios();
        unidadeNegocios2.setId(unidadeNegocios1.getId());
        assertThat(unidadeNegocios1).isEqualTo(unidadeNegocios2);
        unidadeNegocios2.setId(2L);
        assertThat(unidadeNegocios1).isNotEqualTo(unidadeNegocios2);
        unidadeNegocios1.setId(null);
        assertThat(unidadeNegocios1).isNotEqualTo(unidadeNegocios2);
    }
}
