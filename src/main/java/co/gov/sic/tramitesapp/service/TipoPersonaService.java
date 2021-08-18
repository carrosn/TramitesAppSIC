package co.gov.sic.tramitesapp.service;

import co.gov.sic.tramitesapp.domain.TipoPersona;
import co.gov.sic.tramitesapp.repository.TipoPersonaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link TipoPersona}.
 */
@Service
@Transactional
public class TipoPersonaService {

    private final Logger log = LoggerFactory.getLogger(TipoPersonaService.class);

    private final TipoPersonaRepository tipoPersonaRepository;

    public TipoPersonaService(TipoPersonaRepository tipoPersonaRepository) {
        this.tipoPersonaRepository = tipoPersonaRepository;
    }

    /**
     * Save a tipoPersona.
     *
     * @param tipoPersona the entity to save.
     * @return the persisted entity.
     */
    public TipoPersona save(TipoPersona tipoPersona) {
        log.debug("Request to save TipoPersona : {}", tipoPersona);
        return tipoPersonaRepository.save(tipoPersona);
    }

    /**
     * Get all the tipoPersonas.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<TipoPersona> findAll() {
        log.debug("Request to get all TipoPersonas");
        return tipoPersonaRepository.findAll();
    }


    /**
     * Get one tipoPersona by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TipoPersona> findOne(Long id) {
        log.debug("Request to get TipoPersona : {}", id);
        return tipoPersonaRepository.findById(id);
    }

    /**
     * Delete the tipoPersona by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TipoPersona : {}", id);
        tipoPersonaRepository.deleteById(id);
    }
}
