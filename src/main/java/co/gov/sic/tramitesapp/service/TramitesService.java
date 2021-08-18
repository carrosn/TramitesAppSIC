package co.gov.sic.tramitesapp.service;

import co.gov.sic.tramitesapp.domain.Tramites;
import co.gov.sic.tramitesapp.repository.TramitesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Tramites}.
 */
@Service
@Transactional
public class TramitesService {

    private final Logger log = LoggerFactory.getLogger(TramitesService.class);

    private final TramitesRepository tramitesRepository;

    public TramitesService(TramitesRepository tramitesRepository) {
        this.tramitesRepository = tramitesRepository;
    }

    /**
     * Save a tramites.
     *
     * @param tramites the entity to save.
     * @return the persisted entity.
     */
    public Tramites save(Tramites tramites) {
        log.debug("Request to save Tramites : {}", tramites);
        return tramitesRepository.save(tramites);
    }

    /**
     * Get all the tramites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Tramites> findAll(Pageable pageable) {
        log.debug("Request to get all Tramites");
        return tramitesRepository.findAll(pageable);
    }


    /**
     * Get one tramites by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Tramites> findOne(Long id) {
        log.debug("Request to get Tramites : {}", id);
        return tramitesRepository.findById(id);
    }

    /**
     * Delete the tramites by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Tramites : {}", id);
        tramitesRepository.deleteById(id);
    }
}
