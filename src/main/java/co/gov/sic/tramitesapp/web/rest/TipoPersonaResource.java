package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.domain.TipoPersona;
import co.gov.sic.tramitesapp.service.TipoPersonaService;
import co.gov.sic.tramitesapp.web.rest.errors.BadRequestAlertException;
import co.gov.sic.tramitesapp.service.dto.TipoPersonaCriteria;
import co.gov.sic.tramitesapp.service.TipoPersonaQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.gov.sic.tramitesapp.domain.TipoPersona}.
 */
@RestController
@RequestMapping("/api")
public class TipoPersonaResource {

    private final Logger log = LoggerFactory.getLogger(TipoPersonaResource.class);

    private static final String ENTITY_NAME = "tipoPersona";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoPersonaService tipoPersonaService;

    private final TipoPersonaQueryService tipoPersonaQueryService;

    public TipoPersonaResource(TipoPersonaService tipoPersonaService, TipoPersonaQueryService tipoPersonaQueryService) {
        this.tipoPersonaService = tipoPersonaService;
        this.tipoPersonaQueryService = tipoPersonaQueryService;
    }

    /**
     * {@code POST  /tipo-personas} : Create a new tipoPersona.
     *
     * @param tipoPersona the tipoPersona to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoPersona, or with status {@code 400 (Bad Request)} if the tipoPersona has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-personas")
    public ResponseEntity<TipoPersona> createTipoPersona(@Valid @RequestBody TipoPersona tipoPersona) throws URISyntaxException {
        log.debug("REST request to save TipoPersona : {}", tipoPersona);
        if (tipoPersona.getId() != null) {
            throw new BadRequestAlertException("A new tipoPersona cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoPersona result = tipoPersonaService.save(tipoPersona);
        return ResponseEntity.created(new URI("/api/tipo-personas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-personas} : Updates an existing tipoPersona.
     *
     * @param tipoPersona the tipoPersona to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoPersona,
     * or with status {@code 400 (Bad Request)} if the tipoPersona is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoPersona couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-personas")
    public ResponseEntity<TipoPersona> updateTipoPersona(@Valid @RequestBody TipoPersona tipoPersona) throws URISyntaxException {
        log.debug("REST request to update TipoPersona : {}", tipoPersona);
        if (tipoPersona.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoPersona result = tipoPersonaService.save(tipoPersona);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoPersona.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-personas} : get all the tipoPersonas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoPersonas in body.
     */
    @GetMapping("/tipo-personas")
    public ResponseEntity<List<TipoPersona>> getAllTipoPersonas(TipoPersonaCriteria criteria) {
        log.debug("REST request to get TipoPersonas by criteria: {}", criteria);
        List<TipoPersona> entityList = tipoPersonaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /tipo-personas/count} : count all the tipoPersonas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-personas/count")
    public ResponseEntity<Long> countTipoPersonas(TipoPersonaCriteria criteria) {
        log.debug("REST request to count TipoPersonas by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoPersonaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-personas/:id} : get the "id" tipoPersona.
     *
     * @param id the id of the tipoPersona to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoPersona, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-personas/{id}")
    public ResponseEntity<TipoPersona> getTipoPersona(@PathVariable Long id) {
        log.debug("REST request to get TipoPersona : {}", id);
        Optional<TipoPersona> tipoPersona = tipoPersonaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoPersona);
    }

    /**
     * {@code DELETE  /tipo-personas/:id} : delete the "id" tipoPersona.
     *
     * @param id the id of the tipoPersona to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-personas/{id}")
    public ResponseEntity<Void> deleteTipoPersona(@PathVariable Long id) {
        log.debug("REST request to delete TipoPersona : {}", id);
        tipoPersonaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
