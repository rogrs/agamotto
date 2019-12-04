package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class FeriadosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Feriados.class);
        Feriados feriados1 = new Feriados();
        feriados1.setId(1L);
        Feriados feriados2 = new Feriados();
        feriados2.setId(feriados1.getId());
        assertThat(feriados1).isEqualTo(feriados2);
        feriados2.setId(2L);
        assertThat(feriados1).isNotEqualTo(feriados2);
        feriados1.setId(null);
        assertThat(feriados1).isNotEqualTo(feriados2);
    }
}
