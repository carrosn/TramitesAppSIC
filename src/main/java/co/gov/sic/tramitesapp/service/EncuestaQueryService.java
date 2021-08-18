package co.gov.sic.tramitesapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import co.gov.sic.tramitesapp.domain.Encuesta;
import co.gov.sic.tramitesapp.domain.*; // for static metamodels
import co.gov.sic.tramitesapp.repository.EncuestaRepository;
import co.gov.sic.tramitesapp.service.dto.EncuestaCriteria;

/**
 * Service for executing complex queries for {@link Encuesta} entities in the database.
 * The main input is a {@link EncuestaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Encuesta} or a {@link Page} of {@link Encuesta} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EncuestaQueryService extends QueryService<Encuesta> {

    private final Logger log = LoggerFactory.getLogger(EncuestaQueryService.class);

    private final EncuestaRepository encuestaRepository;

    public EncuestaQueryService(EncuestaRepository encuestaRepository) {
        this.encuestaRepository = encuestaRepository;
    }

    /**
     * Return a {@link List} of {@link Encuesta} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Encuesta> findByCriteria(EncuestaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Encuesta> specification = createSpecification(criteria);
        return encuestaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Encuesta} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Encuesta> findByCriteria(EncuestaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Encuesta> specification = createSpecification(criteria);
        return encuestaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EncuestaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Encuesta> specification = createSpecification(criteria);
        return encuestaRepository.count(specification);
    }

    /**
     * Function to convert {@link EncuestaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Encuesta> createSpecification(EncuestaCriteria criteria) {
        Specification<Encuesta> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Encuesta_.id));
            }
            if (criteria.getPreguntaOne() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPreguntaOne(), Encuesta_.preguntaOne));
            }
            if (criteria.getNiveSatisfacion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNiveSatisfacion(), Encuesta_.niveSatisfacion));
            }
            if (criteria.getNivelSatisfacionId() != null) {
                specification = specification.and(buildSpecification(criteria.getNivelSatisfacionId(),
                    root -> root.join(Encuesta_.nivelSatisfacion, JoinType.LEFT).get(NivelSatisfacion_.id)));
            }
        }
        return specification;
    }
}
