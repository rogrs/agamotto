package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class TurnosTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Turnos.class);
        Turnos turnos1 = new Turnos();
        turnos1.setId(1L);
        Turnos turnos2 = new Turnos();
        turnos2.setId(turnos1.getId());
        assertThat(turnos1).isEqualTo(turnos2);
        turnos2.setId(2L);
        assertThat(turnos1).isNotEqualTo(turnos2);
        turnos1.setId(null);
        assertThat(turnos1).isNotEqualTo(turnos2);
    }
}
