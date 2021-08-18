package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.TramitesApp;
import co.gov.sic.tramitesapp.domain.TipoDocumentoIdentificacion;
import co.gov.sic.tramitesapp.repository.TipoDocumentoIdentificacionRepository;
import co.gov.sic.tramitesapp.service.TipoDocumentoIdentificacionService;
import co.gov.sic.tramitesapp.service.dto.TipoDocumentoIdentificacionCriteria;
import co.gov.sic.tramitesapp.service.TipoDocumentoIdentificacionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link TipoDocumentoIdentificacionResource} REST controller.
 */
@SpringBootTest(classes = TramitesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoDocumentoIdentificacionResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_ABREVIATURA = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIATURA = "BBBBBBBBBB";

    @Autowired
    private TipoDocumentoIdentificacionRepository tipoDocumentoIdentificacionRepository;

    @Autowired
    private TipoDocumentoIdentificacionService tipoDocumentoIdentificacionService;

    @Autowired
    private TipoDocumentoIdentificacionQueryService tipoDocumentoIdentificacionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoDocumentoIdentificacionMockMvc;

    private TipoDocumentoIdentificacion tipoDocumentoIdentificacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDocumentoIdentificacion createEntity(EntityManager em) {
        TipoDocumentoIdentificacion tipoDocumentoIdentificacion = new TipoDocumentoIdentificacion()
            .nombre(DEFAULT_NOMBRE)
            .abreviatura(DEFAULT_ABREVIATURA);
        return tipoDocumentoIdentificacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoDocumentoIdentificacion createUpdatedEntity(EntityManager em) {
        TipoDocumentoIdentificacion tipoDocumentoIdentificacion = new TipoDocumentoIdentificacion()
            .nombre(UPDATED_NOMBRE)
            .abreviatura(UPDATED_ABREVIATURA);
        return tipoDocumentoIdentificacion;
    }

    @BeforeEach
    public void initTest() {
        tipoDocumentoIdentificacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoDocumentoIdentificacion() throws Exception {
        int databaseSizeBeforeCreate = tipoDocumentoIdentificacionRepository.findAll().size();
        // Create the TipoDocumentoIdentificacion
        restTipoDocumentoIdentificacionMockMvc.perform(post("/api/tipo-documento-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumentoIdentificacion)))
            .andExpect(status().isCreated());

        // Validate the TipoDocumentoIdentificacion in the database
        List<TipoDocumentoIdentificacion> tipoDocumentoIdentificacionList = tipoDocumentoIdentificacionRepository.findAll();
        assertThat(tipoDocumentoIdentificacionList).hasSize(databaseSizeBeforeCreate + 1);
        TipoDocumentoIdentificacion testTipoDocumentoIdentificacion = tipoDocumentoIdentificacionList.get(tipoDocumentoIdentificacionList.size() - 1);
        assertThat(testTipoDocumentoIdentificacion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testTipoDocumentoIdentificacion.getAbreviatura()).isEqualTo(DEFAULT_ABREVIATURA);
    }

    @Test
    @Transactional
    public void createTipoDocumentoIdentificacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoDocumentoIdentificacionRepository.findAll().size();

        // Create the TipoDocumentoIdentificacion with an existing ID
        tipoDocumentoIdentificacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoDocumentoIdentificacionMockMvc.perform(post("/api/tipo-documento-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumentoIdentificacion)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDocumentoIdentificacion in the database
        List<TipoDocumentoIdentificacion> tipoDocumentoIdentificacionList = tipoDocumentoIdentificacionRepository.findAll();
        assertThat(tipoDocumentoIdentificacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNombreIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDocumentoIdentificacionRepository.findAll().size();
        // set the field null
        tipoDocumentoIdentificacion.setNombre(null);

        // Create the TipoDocumentoIdentificacion, which fails.


        restTipoDocumentoIdentificacionMockMvc.perform(post("/api/tipo-documento-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumentoIdentificacion)))
            .andExpect(status().isBadRequest());

        List<TipoDocumentoIdentificacion> tipoDocumentoIdentificacionList = tipoDocumentoIdentificacionRepository.findAll();
        assertThat(tipoDocumentoIdentificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAbreviaturaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoDocumentoIdentificacionRepository.findAll().size();
        // set the field null
        tipoDocumentoIdentificacion.setAbreviatura(null);

        // Create the TipoDocumentoIdentificacion, which fails.


        restTipoDocumentoIdentificacionMockMvc.perform(post("/api/tipo-documento-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumentoIdentificacion)))
            .andExpect(status().isBadRequest());

        List<TipoDocumentoIdentificacion> tipoDocumentoIdentificacionList = tipoDocumentoIdentificacionRepository.findAll();
        assertThat(tipoDocumentoIdentificacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacions() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList
        restTipoDocumentoIdentificacionMockMvc.perform(get("/api/tipo-documento-identificacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDocumentoIdentificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].abreviatura").value(hasItem(DEFAULT_ABREVIATURA)));
    }
    
    @Test
    @Transactional
    public void getTipoDocumentoIdentificacion() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get the tipoDocumentoIdentificacion
        restTipoDocumentoIdentificacionMockMvc.perform(get("/api/tipo-documento-identificacions/{id}", tipoDocumentoIdentificacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoDocumentoIdentificacion.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE))
            .andExpect(jsonPath("$.abreviatura").value(DEFAULT_ABREVIATURA));
    }


    @Test
    @Transactional
    public void getTipoDocumentoIdentificacionsByIdFiltering() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        Long id = tipoDocumentoIdentificacion.getId();

        defaultTipoDocumentoIdentificacionShouldBeFound("id.equals=" + id);
        defaultTipoDocumentoIdentificacionShouldNotBeFound("id.notEquals=" + id);

        defaultTipoDocumentoIdentificacionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoDocumentoIdentificacionShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoDocumentoIdentificacionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoDocumentoIdentificacionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where nombre equals to DEFAULT_NOMBRE
        defaultTipoDocumentoIdentificacionShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the tipoDocumentoIdentificacionList where nombre equals to UPDATED_NOMBRE
        defaultTipoDocumentoIdentificacionShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByNombreIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where nombre not equals to DEFAULT_NOMBRE
        defaultTipoDocumentoIdentificacionShouldNotBeFound("nombre.notEquals=" + DEFAULT_NOMBRE);

        // Get all the tipoDocumentoIdentificacionList where nombre not equals to UPDATED_NOMBRE
        defaultTipoDocumentoIdentificacionShouldBeFound("nombre.notEquals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultTipoDocumentoIdentificacionShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the tipoDocumentoIdentificacionList where nombre equals to UPDATED_NOMBRE
        defaultTipoDocumentoIdentificacionShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where nombre is not null
        defaultTipoDocumentoIdentificacionShouldBeFound("nombre.specified=true");

        // Get all the tipoDocumentoIdentificacionList where nombre is null
        defaultTipoDocumentoIdentificacionShouldNotBeFound("nombre.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByNombreContainsSomething() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where nombre contains DEFAULT_NOMBRE
        defaultTipoDocumentoIdentificacionShouldBeFound("nombre.contains=" + DEFAULT_NOMBRE);

        // Get all the tipoDocumentoIdentificacionList where nombre contains UPDATED_NOMBRE
        defaultTipoDocumentoIdentificacionShouldNotBeFound("nombre.contains=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByNombreNotContainsSomething() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where nombre does not contain DEFAULT_NOMBRE
        defaultTipoDocumentoIdentificacionShouldNotBeFound("nombre.doesNotContain=" + DEFAULT_NOMBRE);

        // Get all the tipoDocumentoIdentificacionList where nombre does not contain UPDATED_NOMBRE
        defaultTipoDocumentoIdentificacionShouldBeFound("nombre.doesNotContain=" + UPDATED_NOMBRE);
    }


    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByAbreviaturaIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where abreviatura equals to DEFAULT_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldBeFound("abreviatura.equals=" + DEFAULT_ABREVIATURA);

        // Get all the tipoDocumentoIdentificacionList where abreviatura equals to UPDATED_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldNotBeFound("abreviatura.equals=" + UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByAbreviaturaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where abreviatura not equals to DEFAULT_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldNotBeFound("abreviatura.notEquals=" + DEFAULT_ABREVIATURA);

        // Get all the tipoDocumentoIdentificacionList where abreviatura not equals to UPDATED_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldBeFound("abreviatura.notEquals=" + UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByAbreviaturaIsInShouldWork() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where abreviatura in DEFAULT_ABREVIATURA or UPDATED_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldBeFound("abreviatura.in=" + DEFAULT_ABREVIATURA + "," + UPDATED_ABREVIATURA);

        // Get all the tipoDocumentoIdentificacionList where abreviatura equals to UPDATED_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldNotBeFound("abreviatura.in=" + UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByAbreviaturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where abreviatura is not null
        defaultTipoDocumentoIdentificacionShouldBeFound("abreviatura.specified=true");

        // Get all the tipoDocumentoIdentificacionList where abreviatura is null
        defaultTipoDocumentoIdentificacionShouldNotBeFound("abreviatura.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByAbreviaturaContainsSomething() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where abreviatura contains DEFAULT_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldBeFound("abreviatura.contains=" + DEFAULT_ABREVIATURA);

        // Get all the tipoDocumentoIdentificacionList where abreviatura contains UPDATED_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldNotBeFound("abreviatura.contains=" + UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    public void getAllTipoDocumentoIdentificacionsByAbreviaturaNotContainsSomething() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionRepository.saveAndFlush(tipoDocumentoIdentificacion);

        // Get all the tipoDocumentoIdentificacionList where abreviatura does not contain DEFAULT_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldNotBeFound("abreviatura.doesNotContain=" + DEFAULT_ABREVIATURA);

        // Get all the tipoDocumentoIdentificacionList where abreviatura does not contain UPDATED_ABREVIATURA
        defaultTipoDocumentoIdentificacionShouldBeFound("abreviatura.doesNotContain=" + UPDATED_ABREVIATURA);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoDocumentoIdentificacionShouldBeFound(String filter) throws Exception {
        restTipoDocumentoIdentificacionMockMvc.perform(get("/api/tipo-documento-identificacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoDocumentoIdentificacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE)))
            .andExpect(jsonPath("$.[*].abreviatura").value(hasItem(DEFAULT_ABREVIATURA)));

        // Check, that the count call also returns 1
        restTipoDocumentoIdentificacionMockMvc.perform(get("/api/tipo-documento-identificacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoDocumentoIdentificacionShouldNotBeFound(String filter) throws Exception {
        restTipoDocumentoIdentificacionMockMvc.perform(get("/api/tipo-documento-identificacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoDocumentoIdentificacionMockMvc.perform(get("/api/tipo-documento-identificacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipoDocumentoIdentificacion() throws Exception {
        // Get the tipoDocumentoIdentificacion
        restTipoDocumentoIdentificacionMockMvc.perform(get("/api/tipo-documento-identificacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoDocumentoIdentificacion() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionService.save(tipoDocumentoIdentificacion);

        int databaseSizeBeforeUpdate = tipoDocumentoIdentificacionRepository.findAll().size();

        // Update the tipoDocumentoIdentificacion
        TipoDocumentoIdentificacion updatedTipoDocumentoIdentificacion = tipoDocumentoIdentificacionRepository.findById(tipoDocumentoIdentificacion.getId()).get();
        // Disconnect from session so that the updates on updatedTipoDocumentoIdentificacion are not directly saved in db
        em.detach(updatedTipoDocumentoIdentificacion);
        updatedTipoDocumentoIdentificacion
            .nombre(UPDATED_NOMBRE)
            .abreviatura(UPDATED_ABREVIATURA);

        restTipoDocumentoIdentificacionMockMvc.perform(put("/api/tipo-documento-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoDocumentoIdentificacion)))
            .andExpect(status().isOk());

        // Validate the TipoDocumentoIdentificacion in the database
        List<TipoDocumentoIdentificacion> tipoDocumentoIdentificacionList = tipoDocumentoIdentificacionRepository.findAll();
        assertThat(tipoDocumentoIdentificacionList).hasSize(databaseSizeBeforeUpdate);
        TipoDocumentoIdentificacion testTipoDocumentoIdentificacion = tipoDocumentoIdentificacionList.get(tipoDocumentoIdentificacionList.size() - 1);
        assertThat(testTipoDocumentoIdentificacion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testTipoDocumentoIdentificacion.getAbreviatura()).isEqualTo(UPDATED_ABREVIATURA);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoDocumentoIdentificacion() throws Exception {
        int databaseSizeBeforeUpdate = tipoDocumentoIdentificacionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoDocumentoIdentificacionMockMvc.perform(put("/api/tipo-documento-identificacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoDocumentoIdentificacion)))
            .andExpect(status().isBadRequest());

        // Validate the TipoDocumentoIdentificacion in the database
        List<TipoDocumentoIdentificacion> tipoDocumentoIdentificacionList = tipoDocumentoIdentificacionRepository.findAll();
        assertThat(tipoDocumentoIdentificacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoDocumentoIdentificacion() throws Exception {
        // Initialize the database
        tipoDocumentoIdentificacionService.save(tipoDocumentoIdentificacion);

        int databaseSizeBeforeDelete = tipoDocumentoIdentificacionRepository.findAll().size();

        // Delete the tipoDocumentoIdentificacion
        restTipoDocumentoIdentificacionMockMvc.perform(delete("/api/tipo-documento-identificacions/{id}", tipoDocumentoIdentificacion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoDocumentoIdentificacion> tipoDocumentoIdentificacionList = tipoDocumentoIdentificacionRepository.findAll();
        assertThat(tipoDocumentoIdentificacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
