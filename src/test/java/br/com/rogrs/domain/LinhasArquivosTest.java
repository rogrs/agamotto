package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class LinhasArquivosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LinhasArquivos.class);
        LinhasArquivos linhasArquivos1 = new LinhasArquivos();
        linhasArquivos1.setId(1L);
        LinhasArquivos linhasArquivos2 = new LinhasArquivos();
        linhasArquivos2.setId(linhasArquivos1.getId());
        assertThat(linhasArquivos1).isEqualTo(linhasArquivos2);
        linhasArquivos2.setId(2L);
        assertThat(linhasArquivos1).isNotEqualTo(linhasArquivos2);
        linhasArquivos1.setId(null);
        assertThat(linhasArquivos1).isNotEqualTo(linhasArquivos2);
    }
}
