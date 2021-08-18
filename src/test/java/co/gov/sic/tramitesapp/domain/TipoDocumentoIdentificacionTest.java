package co.gov.sic.tramitesapp.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import co.gov.sic.tramitesapp.web.rest.TestUtil;

public class TipoDocumentoIdentificacionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoDocumentoIdentificacion.class);
        TipoDocumentoIdentificacion tipoDocumentoIdentificacion1 = new TipoDocumentoIdentificacion();
        tipoDocumentoIdentificacion1.setId(1L);
        TipoDocumentoIdentificacion tipoDocumentoIdentificacion2 = new TipoDocumentoIdentificacion();
        tipoDocumentoIdentificacion2.setId(tipoDocumentoIdentificacion1.getId());
        assertThat(tipoDocumentoIdentificacion1).isEqualTo(tipoDocumentoIdentificacion2);
        tipoDocumentoIdentificacion2.setId(2L);
        assertThat(tipoDocumentoIdentificacion1).isNotEqualTo(tipoDocumentoIdentificacion2);
        tipoDocumentoIdentificacion1.setId(null);
        assertThat(tipoDocumentoIdentificacion1).isNotEqualTo(tipoDocumentoIdentificacion2);
    }
}
