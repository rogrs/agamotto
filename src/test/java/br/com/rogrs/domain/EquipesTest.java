package br.com.rogrs.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import br.com.rogrs.web.rest.TestUtil;

public class EquipesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Equipes.class);
        Equipes equipes1 = new Equipes();
        equipes1.setId(1L);
        Equipes equipes2 = new Equipes();
        equipes2.setId(equipes1.getId());
        assertThat(equipes1).isEqualTo(equipes2);
        equipes2.setId(2L);
        assertThat(equipes1).isNotEqualTo(equipes2);
        equipes1.setId(null);
        assertThat(equipes1).isNotEqualTo(equipes2);
    }
}
