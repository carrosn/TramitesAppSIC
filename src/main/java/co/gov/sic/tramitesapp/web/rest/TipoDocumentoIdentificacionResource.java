package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.domain.TipoDocumentoIdentificacion;
import co.gov.sic.tramitesapp.service.TipoDocumentoIdentificacionService;
import co.gov.sic.tramitesapp.web.rest.errors.BadRequestAlertException;
import co.gov.sic.tramitesapp.service.dto.TipoDocumentoIdentificacionCriteria;
import co.gov.sic.tramitesapp.service.TipoDocumentoIdentificacionQueryService;

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
 * REST controller for managing {@link co.gov.sic.tramitesapp.domain.TipoDocumentoIdentificacion}.
 */
@RestController
@RequestMapping("/api")
public class TipoDocumentoIdentificacionResource {

    private final Logger log = LoggerFactory.getLogger(TipoDocumentoIdentificacionResource.class);

    private static final String ENTITY_NAME = "tipoDocumentoIdentificacion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoDocumentoIdentificacionService tipoDocumentoIdentificacionService;

    private final TipoDocumentoIdentificacionQueryService tipoDocumentoIdentificacionQueryService;

    public TipoDocumentoIdentificacionResource(TipoDocumentoIdentificacionService tipoDocumentoIdentificacionService, TipoDocumentoIdentificacionQueryService tipoDocumentoIdentificacionQueryService) {
        this.tipoDocumentoIdentificacionService = tipoDocumentoIdentificacionService;
        this.tipoDocumentoIdentificacionQueryService = tipoDocumentoIdentificacionQueryService;
    }

    /**
     * {@code POST  /tipo-documento-identificacions} : Create a new tipoDocumentoIdentificacion.
     *
     * @param tipoDocumentoIdentificacion the tipoDocumentoIdentificacion to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoDocumentoIdentificacion, or with status {@code 400 (Bad Request)} if the tipoDocumentoIdentificacion has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-documento-identificacions")
    public ResponseEntity<TipoDocumentoIdentificacion> createTipoDocumentoIdentificacion(@Valid @RequestBody TipoDocumentoIdentificacion tipoDocumentoIdentificacion) throws URISyntaxException {
        log.debug("REST request to save TipoDocumentoIdentificacion : {}", tipoDocumentoIdentificacion);
        if (tipoDocumentoIdentificacion.getId() != null) {
            throw new BadRequestAlertException("A new tipoDocumentoIdentificacion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoDocumentoIdentificacion result = tipoDocumentoIdentificacionService.save(tipoDocumentoIdentificacion);
        return ResponseEntity.created(new URI("/api/tipo-documento-identificacions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-documento-identificacions} : Updates an existing tipoDocumentoIdentificacion.
     *
     * @param tipoDocumentoIdentificacion the tipoDocumentoIdentificacion to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoDocumentoIdentificacion,
     * or with status {@code 400 (Bad Request)} if the tipoDocumentoIdentificacion is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoDocumentoIdentificacion couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-documento-identificacions")
    public ResponseEntity<TipoDocumentoIdentificacion> updateTipoDocumentoIdentificacion(@Valid @RequestBody TipoDocumentoIdentificacion tipoDocumentoIdentificacion) throws URISyntaxException {
        log.debug("REST request to update TipoDocumentoIdentificacion : {}", tipoDocumentoIdentificacion);
        if (tipoDocumentoIdentificacion.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoDocumentoIdentificacion result = tipoDocumentoIdentificacionService.save(tipoDocumentoIdentificacion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoDocumentoIdentificacion.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-documento-identificacions} : get all the tipoDocumentoIdentificacions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoDocumentoIdentificacions in body.
     */
    @GetMapping("/tipo-documento-identificacions")
    public ResponseEntity<List<TipoDocumentoIdentificacion>> getAllTipoDocumentoIdentificacions(TipoDocumentoIdentificacionCriteria criteria) {
        log.debug("REST request to get TipoDocumentoIdentificacions by criteria: {}", criteria);
        List<TipoDocumentoIdentificacion> entityList = tipoDocumentoIdentificacionQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /tipo-documento-identificacions/count} : count all the tipoDocumentoIdentificacions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/tipo-documento-identificacions/count")
    public ResponseEntity<Long> countTipoDocumentoIdentificacions(TipoDocumentoIdentificacionCriteria criteria) {
        log.debug("REST request to count TipoDocumentoIdentificacions by criteria: {}", criteria);
        return ResponseEntity.ok().body(tipoDocumentoIdentificacionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /tipo-documento-identificacions/:id} : get the "id" tipoDocumentoIdentificacion.
     *
     * @param id the id of the tipoDocumentoIdentificacion to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoDocumentoIdentificacion, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-documento-identificacions/{id}")
    public ResponseEntity<TipoDocumentoIdentificacion> getTipoDocumentoIdentificacion(@PathVariable Long id) {
        log.debug("REST request to get TipoDocumentoIdentificacion : {}", id);
        Optional<TipoDocumentoIdentificacion> tipoDocumentoIdentificacion = tipoDocumentoIdentificacionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(tipoDocumentoIdentificacion);
    }

    /**
     * {@code DELETE  /tipo-documento-identificacions/:id} : delete the "id" tipoDocumentoIdentificacion.
     *
     * @param id the id of the tipoDocumentoIdentificacion to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-documento-identificacions/{id}")
    public ResponseEntity<Void> deleteTipoDocumentoIdentificacion(@PathVariable Long id) {
        log.debug("REST request to delete TipoDocumentoIdentificacion : {}", id);
        tipoDocumentoIdentificacionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
