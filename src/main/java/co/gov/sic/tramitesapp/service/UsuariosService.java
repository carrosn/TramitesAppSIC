package co.gov.sic.tramitesapp.service;

import co.gov.sic.tramitesapp.domain.Usuarios;
import co.gov.sic.tramitesapp.repository.UsuariosRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Usuarios}.
 */
@Service
@Transactional
public class UsuariosService {

    private final Logger log = LoggerFactory.getLogger(UsuariosService.class);

    private final UsuariosRepository usuariosRepository;

    public UsuariosService(UsuariosRepository usuariosRepository) {
        this.usuariosRepository = usuariosRepository;
    }

    /**
     * Save a usuarios.
     *
     * @param usuarios the entity to save.
     * @return the persisted entity.
     */
    public Usuarios save(Usuarios usuarios) {
        log.debug("Request to save Usuarios : {}", usuarios);
        return usuariosRepository.save(usuarios);
    }

    /**
     * Get all the usuarios.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<Usuarios> findAll() {
        log.debug("Request to get all Usuarios");
        return usuariosRepository.findAll();
    }


    /**
     * Get one usuarios by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Usuarios> findOne(Long id) {
        log.debug("Request to get Usuarios : {}", id);
        return usuariosRepository.findById(id);
    }

    /**
     * Delete the usuarios by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Usuarios : {}", id);
        usuariosRepository.deleteById(id);
    }
}
