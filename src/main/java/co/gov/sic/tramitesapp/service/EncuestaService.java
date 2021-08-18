package co.gov.sic.tramitesapp.service;

import co.gov.sic.tramitesapp.domain.Encuesta;
import co.gov.sic.tramitesapp.repository.EncuestaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Encuesta}.
 */
@Service
@Transactional
public class EncuestaService {

    private final Logger log = LoggerFactory.getLogger(EncuestaService.class);

    private final EncuestaRepository encuestaRepository;

    public EncuestaService(EncuestaRepository encuestaRepository) {
        this.encuestaRepository = encuestaRepository;
    }

    /**
     * Save a encuesta.
     *
     * @param encuesta the entity to save.
     * @return the persisted entity.
     */
    public Encuesta save(Encuesta encuesta) {
        log.debug("Request to save Encuesta : {}", encuesta);
        return encuestaRepository.save(encuesta);
    }

    /**
     * Get all the encuestas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Encuesta> findAll(Pageable pageable) {
        log.debug("Request to get all Encuestas");
        return encuestaRepository.findAll(pageable);
    }


    /**
     * Get one encuesta by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Encuesta> findOne(Long id) {
        log.debug("Request to get Encuesta : {}", id);
        return encuestaRepository.findById(id);
    }

    /**
     * Delete the encuesta by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Encuesta : {}", id);
        encuestaRepository.deleteById(id);
    }
}
