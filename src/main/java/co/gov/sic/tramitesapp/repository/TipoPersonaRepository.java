package co.gov.sic.tramitesapp.repository;

import co.gov.sic.tramitesapp.domain.TipoPersona;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the TipoPersona entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoPersonaRepository extends JpaRepository<TipoPersona, Long>, JpaSpecificationExecutor<TipoPersona> {
}
