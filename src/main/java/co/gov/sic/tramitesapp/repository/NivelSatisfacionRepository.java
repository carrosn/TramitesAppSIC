package co.gov.sic.tramitesapp.repository;

import co.gov.sic.tramitesapp.domain.NivelSatisfacion;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the NivelSatisfacion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NivelSatisfacionRepository extends JpaRepository<NivelSatisfacion, Long>, JpaSpecificationExecutor<NivelSatisfacion> {
}
