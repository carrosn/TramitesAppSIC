package co.gov.sic.tramitesapp.service;

import co.gov.sic.tramitesapp.domain.NivelSatisfacion;
import co.gov.sic.tramitesapp.repository.NivelSatisfacionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link NivelSatisfacion}.
 */
@Service
@Transactional
public class NivelSatisfacionService {

    private final Logger log = LoggerFactory.getLogger(NivelSatisfacionService.class);

    private final NivelSatisfacionRepository nivelSatisfacionRepository;

    public NivelSatisfacionService(NivelSatisfacionRepository nivelSatisfacionRepository) {
        this.nivelSatisfacionRepository = nivelSatisfacionRepository;
    }

    /**
     * Save a nivelSatisfacion.
     *
     * @param nivelSatisfacion the entity to save.
     * @return the persisted entity.
     */
    public NivelSatisfacion save(NivelSatisfacion nivelSatisfacion) {
        log.debug("Request to save NivelSatisfacion : {}", nivelSatisfacion);
        return nivelSatisfacionRepository.save(nivelSatisfacion);
    }

    /**
     * Get all the nivelSatisfacions.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<NivelSatisfacion> findAll() {
        log.debug("Request to get all NivelSatisfacions");
        return nivelSatisfacionRepository.findAll();
    }


    /**
     * Get one nivelSatisfacion by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<NivelSatisfacion> findOne(Long id) {
        log.debug("Request to get NivelSatisfacion : {}", id);
        return nivelSatisfacionRepository.findById(id);
    }

    /**
     * Delete the nivelSatisfacion by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete NivelSatisfacion : {}", id);
        nivelSatisfacionRepository.deleteById(id);
    }
}
