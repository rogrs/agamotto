package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class ColaboradoresTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Colaboradores.class);
        Colaboradores colaboradores1 = new Colaboradores();
        colaboradores1.setId(1L);
        Colaboradores colaboradores2 = new Colaboradores();
        colaboradores2.setId(colaboradores1.getId());
        assertThat(colaboradores1).isEqualTo(colaboradores2);
        colaboradores2.setId(2L);
        assertThat(colaboradores1).isNotEqualTo(colaboradores2);
        colaboradores1.setId(null);
        assertThat(colaboradores1).isNotEqualTo(colaboradores2);
    }
}
