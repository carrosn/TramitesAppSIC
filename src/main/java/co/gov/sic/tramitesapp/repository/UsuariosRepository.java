package co.gov.sic.tramitesapp.repository;

import co.gov.sic.tramitesapp.domain.Usuarios;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Usuarios entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UsuariosRepository extends JpaRepository<Usuarios, Long>, JpaSpecificationExecutor<Usuarios> {
}
