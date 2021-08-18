package co.gov.sic.tramitesapp.repository;

import co.gov.sic.tramitesapp.domain.Encuesta;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Encuesta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EncuestaRepository extends JpaRepository<Encuesta, Long>, JpaSpecificationExecutor<Encuesta> {
}
