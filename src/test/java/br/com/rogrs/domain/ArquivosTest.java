package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class ArquivosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Arquivos.class);
        Arquivos arquivos1 = new Arquivos();
        arquivos1.setId(1L);
        Arquivos arquivos2 = new Arquivos();
        arquivos2.setId(arquivos1.getId());
        assertThat(arquivos1).isEqualTo(arquivos2);
        arquivos2.setId(2L);
        assertThat(arquivos1).isNotEqualTo(arquivos2);
        arquivos1.setId(null);
        assertThat(arquivos1).isNotEqualTo(arquivos2);
    }
}
