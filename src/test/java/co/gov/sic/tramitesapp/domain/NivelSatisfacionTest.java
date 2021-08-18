package co.gov.sic.tramitesapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.gov.sic.tramitesapp.web.rest.TestUtil;

public class NivelSatisfacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NivelSatisfacion.class);
        NivelSatisfacion nivelSatisfacion1 = new NivelSatisfacion();
        nivelSatisfacion1.setId(1L);
        NivelSatisfacion nivelSatisfacion2 = new NivelSatisfacion();
        nivelSatisfacion2.setId(nivelSatisfacion1.getId());
        assertThat(nivelSatisfacion1).isEqualTo(nivelSatisfacion2);
        nivelSatisfacion2.setId(2L);
        assertThat(nivelSatisfacion1).isNotEqualTo(nivelSatisfacion2);
        nivelSatisfacion1.setId(null);
        assertThat(nivelSatisfacion1).isNotEqualTo(nivelSatisfacion2);
    }
}
