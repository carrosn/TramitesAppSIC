package co.gov.sic.tramitesapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.gov.sic.tramitesapp.web.rest.TestUtil;

public class TramitesTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tramites.class);
        Tramites tramites1 = new Tramites();
        tramites1.setId(1L);
        Tramites tramites2 = new Tramites();
        tramites2.setId(tramites1.getId());
        assertThat(tramites1).isEqualTo(tramites2);
        tramites2.setId(2L);
        assertThat(tramites1).isNotEqualTo(tramites2);
        tramites1.setId(null);
        assertThat(tramites1).isNotEqualTo(tramites2);
    }
}
