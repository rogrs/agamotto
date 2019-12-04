package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class ControlePontoTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ControlePonto.class);
        ControlePonto controlePonto1 = new ControlePonto();
        controlePonto1.setId(1L);
        ControlePonto controlePonto2 = new ControlePonto();
        controlePonto2.setId(controlePonto1.getId());
        assertThat(controlePonto1).isEqualTo(controlePonto2);
        controlePonto2.setId(2L);
        assertThat(controlePonto1).isNotEqualTo(controlePonto2);
        controlePonto1.setId(null);
        assertThat(controlePonto1).isNotEqualTo(controlePonto2);
    }
}
