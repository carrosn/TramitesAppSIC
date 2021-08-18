package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.domain.NivelSatisfacion;
import co.gov.sic.tramitesapp.service.NivelSatisfacionService;
import co.gov.sic.tramitesapp.web.rest.errors.BadRequestAlertException;
import co.gov.sic.tramitesapp.service.dto.NivelSatisfacionCriteria;
import co.gov.sic.tramitesapp.service.NivelSatisfacionQueryService;

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
 * REST controller for managing {@link co.gov.sic.tramitesapp.domain.NivelSatisfacion}.
 */
@RestController
@RequestMapping("/api")
public class NivelSatisfacionResource {

    private final Logger log = LoggerFactory.getLogger(NivelSatisfacionResource.class);

    private static final String ENTITY_NAME = "nivelSatisfacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NivelSatisfacionService nivelSatisfacionService;

    private final NivelSatisfacionQueryService nivelSatisfacionQueryService;

    public NivelSatisfacionResource(NivelSatisfacionService nivelSatisfacionService, NivelSatisfacionQueryService nivelSatisfacionQueryService) {
        this.nivelSatisfacionService = nivelSatisfacionService;
        this.nivelSatisfacionQueryService = nivelSatisfacionQueryService;
    }

    /**
     * {@code POST  /nivel-satisfacions} : Create a new nivelSatisfacion.
     *
     * @param nivelSatisfacion the nivelSatisfacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nivelSatisfacion, or with status {@code 400 (Bad Request)} if the nivelSatisfacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nivel-satisfacions")
    public ResponseEntity<NivelSatisfacion> createNivelSatisfacion(@Valid @RequestBody NivelSatisfacion nivelSatisfacion) throws URISyntaxException {
        log.debug("REST request to save NivelSatisfacion : {}", nivelSatisfacion);
        if (nivelSatisfacion.getId() != null) {
            throw new BadRequestAlertException("A new nivelSatisfacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NivelSatisfacion result = nivelSatisfacionService.save(nivelSatisfacion);
        return ResponseEntity.created(new URI("/api/nivel-satisfacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nivel-satisfacions} : Updates an existing nivelSatisfacion.
     *
     * @param nivelSatisfacion the nivelSatisfacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nivelSatisfacion,
     * or with status {@code 400 (Bad Request)} if the nivelSatisfacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nivelSatisfacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nivel-satisfacions")
    public ResponseEntity<NivelSatisfacion> updateNivelSatisfacion(@Valid @RequestBody NivelSatisfacion nivelSatisfacion) throws URISyntaxException {
        log.debug("REST request to update NivelSatisfacion : {}", nivelSatisfacion);
        if (nivelSatisfacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NivelSatisfacion result = nivelSatisfacionService.save(nivelSatisfacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nivelSatisfacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nivel-satisfacions} : get all the nivelSatisfacions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nivelSatisfacions in body.
     */
    @GetMapping("/nivel-satisfacions")
    public ResponseEntity<List<NivelSatisfacion>> getAllNivelSatisfacions(NivelSatisfacionCriteria criteria) {
        log.debug("REST request to get NivelSatisfacions by criteria: {}", criteria);
        List<NivelSatisfacion> entityList = nivelSatisfacionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /nivel-satisfacions/count} : count all the nivelSatisfacions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/nivel-satisfacions/count")
    public ResponseEntity<Long> countNivelSatisfacions(NivelSatisfacionCriteria criteria) {
        log.debug("REST request to count NivelSatisfacions by criteria: {}", criteria);
        return ResponseEntity.ok().body(nivelSatisfacionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /nivel-satisfacions/:id} : get the "id" nivelSatisfacion.
     *
     * @param id the id of the nivelSatisfacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nivelSatisfacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nivel-satisfacions/{id}")
    public ResponseEntity<NivelSatisfacion> getNivelSatisfacion(@PathVariable Long id) {
        log.debug("REST request to get NivelSatisfacion : {}", id);
        Optional<NivelSatisfacion> nivelSatisfacion = nivelSatisfacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(nivelSatisfacion);
    }

    /**
     * {@code DELETE  /nivel-satisfacions/:id} : delete the "id" nivelSatisfacion.
     *
     * @param id the id of the nivelSatisfacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nivel-satisfacions/{id}")
    public ResponseEntity<Void> deleteNivelSatisfacion(@PathVariable Long id) {
        log.debug("REST request to delete NivelSatisfacion : {}", id);
        nivelSatisfacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
