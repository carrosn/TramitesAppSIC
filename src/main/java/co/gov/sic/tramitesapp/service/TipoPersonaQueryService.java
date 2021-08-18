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

import co.gov.sic.tramitesapp.domain.TipoPersona;
import co.gov.sic.tramitesapp.domain.*; // for static metamodels
import co.gov.sic.tramitesapp.repository.TipoPersonaRepository;
import co.gov.sic.tramitesapp.service.dto.TipoPersonaCriteria;

/**
 * Service for executing complex queries for {@link TipoPersona} entities in the database.
 * The main input is a {@link TipoPersonaCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoPersona} or a {@link Page} of {@link TipoPersona} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoPersonaQueryService extends QueryService<TipoPersona> {

    private final Logger log = LoggerFactory.getLogger(TipoPersonaQueryService.class);

    private final TipoPersonaRepository tipoPersonaRepository;

    public TipoPersonaQueryService(TipoPersonaRepository tipoPersonaRepository) {
        this.tipoPersonaRepository = tipoPersonaRepository;
    }

    /**
     * Return a {@link List} of {@link TipoPersona} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoPersona> findByCriteria(TipoPersonaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoPersona> specification = createSpecification(criteria);
        return tipoPersonaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipoPersona} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoPersona> findByCriteria(TipoPersonaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoPersona> specification = createSpecification(criteria);
        return tipoPersonaRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoPersonaCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoPersona> specification = createSpecification(criteria);
        return tipoPersonaRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoPersonaCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoPersona> createSpecification(TipoPersonaCriteria criteria) {
        Specification<TipoPersona> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoPersona_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), TipoPersona_.codigo));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), TipoPersona_.descripcion));
            }
        }
        return specification;
    }
}
