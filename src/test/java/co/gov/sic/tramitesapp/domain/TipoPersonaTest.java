package co.gov.sic.tramitesapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.gov.sic.tramitesapp.web.rest.TestUtil;

public class TipoPersonaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoPersona.class);
        TipoPersona tipoPersona1 = new TipoPersona();
        tipoPersona1.setId(1L);
        TipoPersona tipoPersona2 = new TipoPersona();
        tipoPersona2.setId(tipoPersona1.getId());
        assertThat(tipoPersona1).isEqualTo(tipoPersona2);
        tipoPersona2.setId(2L);
        assertThat(tipoPersona1).isNotEqualTo(tipoPersona2);
        tipoPersona1.setId(null);
        assertThat(tipoPersona1).isNotEqualTo(tipoPersona2);
    }
}
