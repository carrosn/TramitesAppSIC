package co.gov.sic.tramitesapp.repository;

import co.gov.sic.tramitesapp.domain.TipoDocumentoIdentificacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoDocumentoIdentificacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoDocumentoIdentificacionRepository extends JpaRepository<TipoDocumentoIdentificacion, Long>, JpaSpecificationExecutor<TipoDocumentoIdentificacion> {
}
