package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.domain.Tramites;
import co.gov.sic.tramitesapp.service.TramitesService;
import co.gov.sic.tramitesapp.web.rest.errors.BadRequestAlertException;
import co.gov.sic.tramitesapp.service.dto.TramitesCriteria;
import co.gov.sic.tramitesapp.service.TramitesQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.gov.sic.tramitesapp.domain.Tramites}.
 */
@RestController
@RequestMapping("/api")
public class TramitesResource {

    private final Logger log = LoggerFactory.getLogger(TramitesResource.class);

    private static final String ENTITY_NAME = "tramites";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TramitesService tramitesService;

    private final TramitesQueryService tramitesQueryService;

    public TramitesResource(TramitesService tramitesService, TramitesQueryService tramitesQueryService) {
        this.tramitesService = tramitesService;
        this.tramitesQueryService = tramitesQueryService;
    }

    /**
     * {@code POST  /tramites} : Create a new tramites.
     *
     * @param tramites the tramites to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tramites, or with status {@code 400 (Bad Request)} if the tramites has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tramites")
    public ResponseEntity<Tramites> createTramites(@Valid @RequestBody Tramites tramites) throws URISyntaxException {
        log.debug("REST request to save Tramites : {}", tramites);
        if (tramites.getId() != null) {
            throw new BadRequestAlertException("A new tramites cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Tramites result = tramitesService.save(tramites);
        return ResponseEntity.created(new URI("/api/tramites/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tramites} : Updates an existing tramites.
     *
     * @param tramites the tramites to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tramites,
     * or with status {@code 400 (Bad Request)} if the tramites is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tramites couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tramites")
    public ResponseEntity<Tramites> updateTramites(@Valid @RequestBody Tramites tramites) throws URISyntaxException {
        log.debug("REST request to update Tramites : {}", tramites);
        if (tramites.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Tramites result = tramitesService.save(tramites);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tramites.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tramites} : get all the tramites.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tramites in body.
     */
    @GetMapping("/tramites")
    public ResponseEntity<List<Tramites>> getAllTramites(TramitesCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Tramites by criteria: {}", criteria);
        Page<Tramites> page = tramitesQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /tramites/count} : count all the tramites.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tramites/count")
    public ResponseEntity<Long> countTramites(TramitesCriteria criteria) {
        log.debug("REST request to count Tramites by criteria: {}", criteria);
        return ResponseEntity.ok().body(tramitesQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tramites/:id} : get the "id" tramites.
     *
     * @param id the id of the tramites to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tramites, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tramites/{id}")
    public ResponseEntity<Tramites> getTramites(@PathVariable Long id) {
        log.debug("REST request to get Tramites : {}", id);
        Optional<Tramites> tramites = tramitesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tramites);
    }

    /**
     * {@code DELETE  /tramites/:id} : delete the "id" tramites.
     *
     * @param id the id of the tramites to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tramites/{id}")
    public ResponseEntity<Void> deleteTramites(@PathVariable Long id) {
        log.debug("REST request to delete Tramites : {}", id);
        tramitesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
