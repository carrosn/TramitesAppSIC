package co.gov.sic.tramitesapp.repository;

import co.gov.sic.tramitesapp.domain.Tramites;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Tramites entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TramitesRepository extends JpaRepository<Tramites, Long>, JpaSpecificationExecutor<Tramites> {
}
