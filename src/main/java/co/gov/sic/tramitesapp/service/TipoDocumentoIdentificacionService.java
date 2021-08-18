package co.gov.sic.tramitesapp.service;

import co.gov.sic.tramitesapp.domain.TipoDocumentoIdentificacion;
import co.gov.sic.tramitesapp.repository.TipoDocumentoIdentificacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoDocumentoIdentificacion}.
 */
@Service
@Transactional
public class TipoDocumentoIdentificacionService {

    private final Logger log = LoggerFactory.getLogger(TipoDocumentoIdentificacionService.class);

    private final TipoDocumentoIdentificacionRepository tipoDocumentoIdentificacionRepository;

    public TipoDocumentoIdentificacionService(TipoDocumentoIdentificacionRepository tipoDocumentoIdentificacionRepository) {
        this.tipoDocumentoIdentificacionRepository = tipoDocumentoIdentificacionRepository;
    }

    /**
     * Save a tipoDocumentoIdentificacion.
     *
     * @param tipoDocumentoIdentificacion the entity to save.
     * @return the persisted entity.
     */
    public TipoDocumentoIdentificacion save(TipoDocumentoIdentificacion tipoDocumentoIdentificacion) {
        log.debug("Request to save TipoDocumentoIdentificacion : {}", tipoDocumentoIdentificacion);
        return tipoDocumentoIdentificacionRepository.save(tipoDocumentoIdentificacion);
    }

    /**
     * Get all the tipoDocumentoIdentificacions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TipoDocumentoIdentificacion> findAll() {
        log.debug("Request to get all TipoDocumentoIdentificacions");
        return tipoDocumentoIdentificacionRepository.findAll();
    }


    /**
     * Get one tipoDocumentoIdentificacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoDocumentoIdentificacion> findOne(Long id) {
        log.debug("Request to get TipoDocumentoIdentificacion : {}", id);
        return tipoDocumentoIdentificacionRepository.findById(id);
    }

    /**
     * Delete the tipoDocumentoIdentificacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoDocumentoIdentificacion : {}", id);
        tipoDocumentoIdentificacionRepository.deleteById(id);
    }
}
