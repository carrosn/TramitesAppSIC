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

import co.gov.sic.tramitesapp.domain.TipoDocumentoIdentificacion;
import co.gov.sic.tramitesapp.domain.*; // for static metamodels
import co.gov.sic.tramitesapp.repository.TipoDocumentoIdentificacionRepository;
import co.gov.sic.tramitesapp.service.dto.TipoDocumentoIdentificacionCriteria;

/**
 * Service for executing complex queries for {@link TipoDocumentoIdentificacion} entities in the database.
 * The main input is a {@link TipoDocumentoIdentificacionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoDocumentoIdentificacion} or a {@link Page} of {@link TipoDocumentoIdentificacion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoDocumentoIdentificacionQueryService extends QueryService<TipoDocumentoIdentificacion> {

    private final Logger log = LoggerFactory.getLogger(TipoDocumentoIdentificacionQueryService.class);

    private final TipoDocumentoIdentificacionRepository tipoDocumentoIdentificacionRepository;

    public TipoDocumentoIdentificacionQueryService(TipoDocumentoIdentificacionRepository tipoDocumentoIdentificacionRepository) {
        this.tipoDocumentoIdentificacionRepository = tipoDocumentoIdentificacionRepository;
    }

    /**
     * Return a {@link List} of {@link TipoDocumentoIdentificacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoDocumentoIdentificacion> findByCriteria(TipoDocumentoIdentificacionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TipoDocumentoIdentificacion> specification = createSpecification(criteria);
        return tipoDocumentoIdentificacionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipoDocumentoIdentificacion} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoDocumentoIdentificacion> findByCriteria(TipoDocumentoIdentificacionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TipoDocumentoIdentificacion> specification = createSpecification(criteria);
        return tipoDocumentoIdentificacionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TipoDocumentoIdentificacionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TipoDocumentoIdentificacion> specification = createSpecification(criteria);
        return tipoDocumentoIdentificacionRepository.count(specification);
    }

    /**
     * Function to convert {@link TipoDocumentoIdentificacionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<TipoDocumentoIdentificacion> createSpecification(TipoDocumentoIdentificacionCriteria criteria) {
        Specification<TipoDocumentoIdentificacion> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), TipoDocumentoIdentificacion_.id));
            }
            if (criteria.getNombre() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNombre(), TipoDocumentoIdentificacion_.nombre));
            }
            if (criteria.getAbreviatura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAbreviatura(), TipoDocumentoIdentificacion_.abreviatura));
            }
        }
        return specification;
    }
}
