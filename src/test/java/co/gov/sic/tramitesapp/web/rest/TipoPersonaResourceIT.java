package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.TramitesApp;
import co.gov.sic.tramitesapp.domain.TipoPersona;
import co.gov.sic.tramitesapp.repository.TipoPersonaRepository;
import co.gov.sic.tramitesapp.service.TipoPersonaService;
import co.gov.sic.tramitesapp.service.dto.TipoPersonaCriteria;
import co.gov.sic.tramitesapp.service.TipoPersonaQueryService;

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
 * Integration tests for the {@link TipoPersonaResource} REST controller.
 */
@SpringBootTest(classes = TramitesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TipoPersonaResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoPersonaRepository tipoPersonaRepository;

    @Autowired
    private TipoPersonaService tipoPersonaService;

    @Autowired
    private TipoPersonaQueryService tipoPersonaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTipoPersonaMockMvc;

    private TipoPersona tipoPersona;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPersona createEntity(EntityManager em) {
        TipoPersona tipoPersona = new TipoPersona()
            .codigo(DEFAULT_CODIGO)
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoPersona;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoPersona createUpdatedEntity(EntityManager em) {
        TipoPersona tipoPersona = new TipoPersona()
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION);
        return tipoPersona;
    }

    @BeforeEach
    public void initTest() {
        tipoPersona = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoPersona() throws Exception {
        int databaseSizeBeforeCreate = tipoPersonaRepository.findAll().size();
        // Create the TipoPersona
        restTipoPersonaMockMvc.perform(post("/api/tipo-personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoPersona)))
            .andExpect(status().isCreated());

        // Validate the TipoPersona in the database
        List<TipoPersona> tipoPersonaList = tipoPersonaRepository.findAll();
        assertThat(tipoPersonaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoPersona testTipoPersona = tipoPersonaList.get(tipoPersonaList.size() - 1);
        assertThat(testTipoPersona.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testTipoPersona.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoPersonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoPersonaRepository.findAll().size();

        // Create the TipoPersona with an existing ID
        tipoPersona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoPersonaMockMvc.perform(post("/api/tipo-personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoPersona)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPersona in the database
        List<TipoPersona> tipoPersonaList = tipoPersonaRepository.findAll();
        assertThat(tipoPersonaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPersonaRepository.findAll().size();
        // set the field null
        tipoPersona.setCodigo(null);

        // Create the TipoPersona, which fails.


        restTipoPersonaMockMvc.perform(post("/api/tipo-personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoPersona)))
            .andExpect(status().isBadRequest());

        List<TipoPersona> tipoPersonaList = tipoPersonaRepository.findAll();
        assertThat(tipoPersonaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tipoPersonaRepository.findAll().size();
        // set the field null
        tipoPersona.setDescripcion(null);

        // Create the TipoPersona, which fails.


        restTipoPersonaMockMvc.perform(post("/api/tipo-personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoPersona)))
            .andExpect(status().isBadRequest());

        List<TipoPersona> tipoPersonaList = tipoPersonaRepository.findAll();
        assertThat(tipoPersonaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTipoPersonas() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList
        restTipoPersonaMockMvc.perform(get("/api/tipo-personas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPersona.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getTipoPersona() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get the tipoPersona
        restTipoPersonaMockMvc.perform(get("/api/tipo-personas/{id}", tipoPersona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tipoPersona.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }


    @Test
    @Transactional
    public void getTipoPersonasByIdFiltering() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        Long id = tipoPersona.getId();

        defaultTipoPersonaShouldBeFound("id.equals=" + id);
        defaultTipoPersonaShouldNotBeFound("id.notEquals=" + id);

        defaultTipoPersonaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTipoPersonaShouldNotBeFound("id.greaterThan=" + id);

        defaultTipoPersonaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTipoPersonaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTipoPersonasByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where codigo equals to DEFAULT_CODIGO
        defaultTipoPersonaShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the tipoPersonaList where codigo equals to UPDATED_CODIGO
        defaultTipoPersonaShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllTipoPersonasByCodigoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where codigo not equals to DEFAULT_CODIGO
        defaultTipoPersonaShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

        // Get all the tipoPersonaList where codigo not equals to UPDATED_CODIGO
        defaultTipoPersonaShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllTipoPersonasByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultTipoPersonaShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the tipoPersonaList where codigo equals to UPDATED_CODIGO
        defaultTipoPersonaShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllTipoPersonasByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where codigo is not null
        defaultTipoPersonaShouldBeFound("codigo.specified=true");

        // Get all the tipoPersonaList where codigo is null
        defaultTipoPersonaShouldNotBeFound("codigo.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoPersonasByCodigoContainsSomething() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where codigo contains DEFAULT_CODIGO
        defaultTipoPersonaShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the tipoPersonaList where codigo contains UPDATED_CODIGO
        defaultTipoPersonaShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllTipoPersonasByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where codigo does not contain DEFAULT_CODIGO
        defaultTipoPersonaShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the tipoPersonaList where codigo does not contain UPDATED_CODIGO
        defaultTipoPersonaShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }


    @Test
    @Transactional
    public void getAllTipoPersonasByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where descripcion equals to DEFAULT_DESCRIPCION
        defaultTipoPersonaShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the tipoPersonaList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoPersonaShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoPersonasByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultTipoPersonaShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the tipoPersonaList where descripcion not equals to UPDATED_DESCRIPCION
        defaultTipoPersonaShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoPersonasByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultTipoPersonaShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the tipoPersonaList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoPersonaShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoPersonasByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where descripcion is not null
        defaultTipoPersonaShouldBeFound("descripcion.specified=true");

        // Get all the tipoPersonaList where descripcion is null
        defaultTipoPersonaShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllTipoPersonasByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where descripcion contains DEFAULT_DESCRIPCION
        defaultTipoPersonaShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the tipoPersonaList where descripcion contains UPDATED_DESCRIPCION
        defaultTipoPersonaShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoPersonasByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        tipoPersonaRepository.saveAndFlush(tipoPersona);

        // Get all the tipoPersonaList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultTipoPersonaShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the tipoPersonaList where descripcion does not contain UPDATED_DESCRIPCION
        defaultTipoPersonaShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTipoPersonaShouldBeFound(String filter) throws Exception {
        restTipoPersonaMockMvc.perform(get("/api/tipo-personas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoPersona.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restTipoPersonaMockMvc.perform(get("/api/tipo-personas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTipoPersonaShouldNotBeFound(String filter) throws Exception {
        restTipoPersonaMockMvc.perform(get("/api/tipo-personas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTipoPersonaMockMvc.perform(get("/api/tipo-personas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTipoPersona() throws Exception {
        // Get the tipoPersona
        restTipoPersonaMockMvc.perform(get("/api/tipo-personas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoPersona() throws Exception {
        // Initialize the database
        tipoPersonaService.save(tipoPersona);

        int databaseSizeBeforeUpdate = tipoPersonaRepository.findAll().size();

        // Update the tipoPersona
        TipoPersona updatedTipoPersona = tipoPersonaRepository.findById(tipoPersona.getId()).get();
        // Disconnect from session so that the updates on updatedTipoPersona are not directly saved in db
        em.detach(updatedTipoPersona);
        updatedTipoPersona
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION);

        restTipoPersonaMockMvc.perform(put("/api/tipo-personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoPersona)))
            .andExpect(status().isOk());

        // Validate the TipoPersona in the database
        List<TipoPersona> tipoPersonaList = tipoPersonaRepository.findAll();
        assertThat(tipoPersonaList).hasSize(databaseSizeBeforeUpdate);
        TipoPersona testTipoPersona = tipoPersonaList.get(tipoPersonaList.size() - 1);
        assertThat(testTipoPersona.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testTipoPersona.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoPersona() throws Exception {
        int databaseSizeBeforeUpdate = tipoPersonaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoPersonaMockMvc.perform(put("/api/tipo-personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tipoPersona)))
            .andExpect(status().isBadRequest());

        // Validate the TipoPersona in the database
        List<TipoPersona> tipoPersonaList = tipoPersonaRepository.findAll();
        assertThat(tipoPersonaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoPersona() throws Exception {
        // Initialize the database
        tipoPersonaService.save(tipoPersona);

        int databaseSizeBeforeDelete = tipoPersonaRepository.findAll().size();

        // Delete the tipoPersona
        restTipoPersonaMockMvc.perform(delete("/api/tipo-personas/{id}", tipoPersona.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoPersona> tipoPersonaList = tipoPersonaRepository.findAll();
        assertThat(tipoPersonaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
