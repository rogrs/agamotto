package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class DepartamentosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Departamentos.class);
        Departamentos departamentos1 = new Departamentos();
        departamentos1.setId(1L);
        Departamentos departamentos2 = new Departamentos();
        departamentos2.setId(departamentos1.getId());
        assertThat(departamentos1).isEqualTo(departamentos2);
        departamentos2.setId(2L);
        assertThat(departamentos1).isNotEqualTo(departamentos2);
        departamentos1.setId(null);
        assertThat(departamentos1).isNotEqualTo(departamentos2);
    }
}
