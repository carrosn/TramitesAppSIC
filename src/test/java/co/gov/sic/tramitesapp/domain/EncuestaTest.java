package co.gov.sic.tramitesapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.gov.sic.tramitesapp.web.rest.TestUtil;

public class EncuestaTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Encuesta.class);
        Encuesta encuesta1 = new Encuesta();
        encuesta1.setId(1L);
        Encuesta encuesta2 = new Encuesta();
        encuesta2.setId(encuesta1.getId());
        assertThat(encuesta1).isEqualTo(encuesta2);
        encuesta2.setId(2L);
        assertThat(encuesta1).isNotEqualTo(encuesta2);
        encuesta1.setId(null);
        assertThat(encuesta1).isNotEqualTo(encuesta2);
    }
}
