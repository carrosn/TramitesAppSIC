package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.domain.Encuesta;
import co.gov.sic.tramitesapp.service.EncuestaService;
import co.gov.sic.tramitesapp.web.rest.errors.BadRequestAlertException;
import co.gov.sic.tramitesapp.service.dto.EncuestaCriteria;
import co.gov.sic.tramitesapp.service.EncuestaQueryService;

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
 * REST controller for managing {@link co.gov.sic.tramitesapp.domain.Encuesta}.
 */
@RestController
@RequestMapping("/api")
public class EncuestaResource {

    private final Logger log = LoggerFactory.getLogger(EncuestaResource.class);

    private static final String ENTITY_NAME = "encuesta";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EncuestaService encuestaService;

    private final EncuestaQueryService encuestaQueryService;

    public EncuestaResource(EncuestaService encuestaService, EncuestaQueryService encuestaQueryService) {
        this.encuestaService = encuestaService;
        this.encuestaQueryService = encuestaQueryService;
    }

    /**
     * {@code POST  /encuestas} : Create a new encuesta.
     *
     * @param encuesta the encuesta to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new encuesta, or with status {@code 400 (Bad Request)} if the encuesta has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/encuestas")
    public ResponseEntity<Encuesta> createEncuesta(@Valid @RequestBody Encuesta encuesta) throws URISyntaxException {
        log.debug("REST request to save Encuesta : {}", encuesta);
        if (encuesta.getId() != null) {
            throw new BadRequestAlertException("A new encuesta cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Encuesta result = encuestaService.save(encuesta);
        return ResponseEntity.created(new URI("/api/encuestas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /encuestas} : Updates an existing encuesta.
     *
     * @param encuesta the encuesta to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated encuesta,
     * or with status {@code 400 (Bad Request)} if the encuesta is not valid,
     * or with status {@code 500 (Internal Server Error)} if the encuesta couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/encuestas")
    public ResponseEntity<Encuesta> updateEncuesta(@Valid @RequestBody Encuesta encuesta) throws URISyntaxException {
        log.debug("REST request to update Encuesta : {}", encuesta);
        if (encuesta.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Encuesta result = encuestaService.save(encuesta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, encuesta.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /encuestas} : get all the encuestas.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of encuestas in body.
     */
    @GetMapping("/encuestas")
    public ResponseEntity<List<Encuesta>> getAllEncuestas(EncuestaCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Encuestas by criteria: {}", criteria);
        Page<Encuesta> page = encuestaQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /encuestas/count} : count all the encuestas.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/encuestas/count")
    public ResponseEntity<Long> countEncuestas(EncuestaCriteria criteria) {
        log.debug("REST request to count Encuestas by criteria: {}", criteria);
        return ResponseEntity.ok().body(encuestaQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /encuestas/:id} : get the "id" encuesta.
     *
     * @param id the id of the encuesta to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the encuesta, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/encuestas/{id}")
    public ResponseEntity<Encuesta> getEncuesta(@PathVariable Long id) {
        log.debug("REST request to get Encuesta : {}", id);
        Optional<Encuesta> encuesta = encuestaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(encuesta);
    }

    /**
     * {@code DELETE  /encuestas/:id} : delete the "id" encuesta.
     *
     * @param id the id of the encuesta to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/encuestas/{id}")
    public ResponseEntity<Void> deleteEncuesta(@PathVariable Long id) {
        log.debug("REST request to delete Encuesta : {}", id);
        encuestaService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
