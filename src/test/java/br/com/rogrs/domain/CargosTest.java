package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class CargosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cargos.class);
        Cargos cargos1 = new Cargos();
        cargos1.setId(1L);
        Cargos cargos2 = new Cargos();
        cargos2.setId(cargos1.getId());
        assertThat(cargos1).isEqualTo(cargos2);
        cargos2.setId(2L);
        assertThat(cargos1).isNotEqualTo(cargos2);
        cargos1.setId(null);
        assertThat(cargos1).isNotEqualTo(cargos2);
    }
}
