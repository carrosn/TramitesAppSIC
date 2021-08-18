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

import co.gov.sic.tramitesapp.domain.NivelSatisfacion;
import co.gov.sic.tramitesapp.domain.*; // for static metamodels
import co.gov.sic.tramitesapp.repository.NivelSatisfacionRepository;
import co.gov.sic.tramitesapp.service.dto.NivelSatisfacionCriteria;

/**
 * Service for executing complex queries for {@link NivelSatisfacion} entities in the database.
 * The main input is a {@link NivelSatisfacionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link NivelSatisfacion} or a {@link Page} of {@link NivelSatisfacion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class NivelSatisfacionQueryService extends QueryService<NivelSatisfacion> {

    private final Logger log = LoggerFactory.getLogger(NivelSatisfacionQueryService.class);

    private final NivelSatisfacionRepository nivelSatisfacionRepository;

    public NivelSatisfacionQueryService(NivelSatisfacionRepository nivelSatisfacionRepository) {
        this.nivelSatisfacionRepository = nivelSatisfacionRepository;
    }

    /**
     * Return a {@link List} of {@link NivelSatisfacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<NivelSatisfacion> findByCriteria(NivelSatisfacionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<NivelSatisfacion> specification = createSpecification(criteria);
        return nivelSatisfacionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link NivelSatisfacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<NivelSatisfacion> findByCriteria(NivelSatisfacionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<NivelSatisfacion> specification = createSpecification(criteria);
        return nivelSatisfacionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(NivelSatisfacionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<NivelSatisfacion> specification = createSpecification(criteria);
        return nivelSatisfacionRepository.count(specification);
    }

    /**
     * Function to convert {@link NivelSatisfacionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<NivelSatisfacion> createSpecification(NivelSatisfacionCriteria criteria) {
        Specification<NivelSatisfacion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), NivelSatisfacion_.id));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), NivelSatisfacion_.codigo));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), NivelSatisfacion_.descripcion));
            }
        }
        return specification;
    }
}
