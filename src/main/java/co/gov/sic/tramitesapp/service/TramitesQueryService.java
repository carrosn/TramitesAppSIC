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

import co.gov.sic.tramitesapp.domain.Tramites;
import co.gov.sic.tramitesapp.domain.*; // for static metamodels
import co.gov.sic.tramitesapp.repository.TramitesRepository;
import co.gov.sic.tramitesapp.service.dto.TramitesCriteria;

/**
 * Service for executing complex queries for {@link Tramites} entities in the database.
 * The main input is a {@link TramitesCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Tramites} or a {@link Page} of {@link Tramites} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TramitesQueryService extends QueryService<Tramites> {

    private final Logger log = LoggerFactory.getLogger(TramitesQueryService.class);

    private final TramitesRepository tramitesRepository;

    public TramitesQueryService(TramitesRepository tramitesRepository) {
        this.tramitesRepository = tramitesRepository;
    }

    /**
     * Return a {@link List} of {@link Tramites} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Tramites> findByCriteria(TramitesCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Tramites> specification = createSpecification(criteria);
        return tramitesRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Tramites} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Tramites> findByCriteria(TramitesCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Tramites> specification = createSpecification(criteria);
        return tramitesRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TramitesCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Tramites> specification = createSpecification(criteria);
        return tramitesRepository.count(specification);
    }

    /**
     * Function to convert {@link TramitesCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Tramites> createSpecification(TramitesCriteria criteria) {
        Specification<Tramites> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Tramites_.id));
            }
            if (criteria.getNumeroTramite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNumeroTramite(), Tramites_.numeroTramite));
            }
            if (criteria.getAnno() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnno(), Tramites_.anno));
            }
            if (criteria.getNombreTramite() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombreTramite(), Tramites_.nombreTramite));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Tramites_.descripcion));
            }
            if (criteria.getPersonaRadica() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPersonaRadica(), Tramites_.personaRadica));
            }
            if (criteria.getFuncionario() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFuncionario(), Tramites_.funcionario));
            }
            if (criteria.getRadicaPersonaId() != null) {
                specification = specification.and(buildSpecification(criteria.getRadicaPersonaId(),
                    root -> root.join(Tramites_.radicaPersona, JoinType.LEFT).get(Persona_.id)));
            }
            if (criteria.getFuncionarioPersonaId() != null) {
                specification = specification.and(buildSpecification(criteria.getFuncionarioPersonaId(),
                    root -> root.join(Tramites_.funcionarioPersona, JoinType.LEFT).get(Persona_.id)));
            }
        }
        return specification;
    }
}
