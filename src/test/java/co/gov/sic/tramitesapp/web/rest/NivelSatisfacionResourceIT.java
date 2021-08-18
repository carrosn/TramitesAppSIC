package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.TramitesApp;
import co.gov.sic.tramitesapp.domain.NivelSatisfacion;
import co.gov.sic.tramitesapp.repository.NivelSatisfacionRepository;
import co.gov.sic.tramitesapp.service.NivelSatisfacionService;
import co.gov.sic.tramitesapp.service.dto.NivelSatisfacionCriteria;
import co.gov.sic.tramitesapp.service.NivelSatisfacionQueryService;

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
 * Integration tests for the {@link NivelSatisfacionResource} REST controller.
 */
@SpringBootTest(classes = TramitesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class NivelSatisfacionResourceIT {

    private static final String DEFAULT_CODIGO = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private NivelSatisfacionRepository nivelSatisfacionRepository;

    @Autowired
    private NivelSatisfacionService nivelSatisfacionService;

    @Autowired
    private NivelSatisfacionQueryService nivelSatisfacionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restNivelSatisfacionMockMvc;

    private NivelSatisfacion nivelSatisfacion;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NivelSatisfacion createEntity(EntityManager em) {
        NivelSatisfacion nivelSatisfacion = new NivelSatisfacion()
            .codigo(DEFAULT_CODIGO)
            .descripcion(DEFAULT_DESCRIPCION);
        return nivelSatisfacion;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NivelSatisfacion createUpdatedEntity(EntityManager em) {
        NivelSatisfacion nivelSatisfacion = new NivelSatisfacion()
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION);
        return nivelSatisfacion;
    }

    @BeforeEach
    public void initTest() {
        nivelSatisfacion = createEntity(em);
    }

    @Test
    @Transactional
    public void createNivelSatisfacion() throws Exception {
        int databaseSizeBeforeCreate = nivelSatisfacionRepository.findAll().size();
        // Create the NivelSatisfacion
        restNivelSatisfacionMockMvc.perform(post("/api/nivel-satisfacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nivelSatisfacion)))
            .andExpect(status().isCreated());

        // Validate the NivelSatisfacion in the database
        List<NivelSatisfacion> nivelSatisfacionList = nivelSatisfacionRepository.findAll();
        assertThat(nivelSatisfacionList).hasSize(databaseSizeBeforeCreate + 1);
        NivelSatisfacion testNivelSatisfacion = nivelSatisfacionList.get(nivelSatisfacionList.size() - 1);
        assertThat(testNivelSatisfacion.getCodigo()).isEqualTo(DEFAULT_CODIGO);
        assertThat(testNivelSatisfacion.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createNivelSatisfacionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nivelSatisfacionRepository.findAll().size();

        // Create the NivelSatisfacion with an existing ID
        nivelSatisfacion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNivelSatisfacionMockMvc.perform(post("/api/nivel-satisfacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nivelSatisfacion)))
            .andExpect(status().isBadRequest());

        // Validate the NivelSatisfacion in the database
        List<NivelSatisfacion> nivelSatisfacionList = nivelSatisfacionRepository.findAll();
        assertThat(nivelSatisfacionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCodigoIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelSatisfacionRepository.findAll().size();
        // set the field null
        nivelSatisfacion.setCodigo(null);

        // Create the NivelSatisfacion, which fails.


        restNivelSatisfacionMockMvc.perform(post("/api/nivel-satisfacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nivelSatisfacion)))
            .andExpect(status().isBadRequest());

        List<NivelSatisfacion> nivelSatisfacionList = nivelSatisfacionRepository.findAll();
        assertThat(nivelSatisfacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = nivelSatisfacionRepository.findAll().size();
        // set the field null
        nivelSatisfacion.setDescripcion(null);

        // Create the NivelSatisfacion, which fails.


        restNivelSatisfacionMockMvc.perform(post("/api/nivel-satisfacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nivelSatisfacion)))
            .andExpect(status().isBadRequest());

        List<NivelSatisfacion> nivelSatisfacionList = nivelSatisfacionRepository.findAll();
        assertThat(nivelSatisfacionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllNivelSatisfacions() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList
        restNivelSatisfacionMockMvc.perform(get("/api/nivel-satisfacions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivelSatisfacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));
    }
    
    @Test
    @Transactional
    public void getNivelSatisfacion() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get the nivelSatisfacion
        restNivelSatisfacionMockMvc.perform(get("/api/nivel-satisfacions/{id}", nivelSatisfacion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(nivelSatisfacion.getId().intValue()))
            .andExpect(jsonPath("$.codigo").value(DEFAULT_CODIGO))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION));
    }


    @Test
    @Transactional
    public void getNivelSatisfacionsByIdFiltering() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        Long id = nivelSatisfacion.getId();

        defaultNivelSatisfacionShouldBeFound("id.equals=" + id);
        defaultNivelSatisfacionShouldNotBeFound("id.notEquals=" + id);

        defaultNivelSatisfacionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultNivelSatisfacionShouldNotBeFound("id.greaterThan=" + id);

        defaultNivelSatisfacionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultNivelSatisfacionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllNivelSatisfacionsByCodigoIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where codigo equals to DEFAULT_CODIGO
        defaultNivelSatisfacionShouldBeFound("codigo.equals=" + DEFAULT_CODIGO);

        // Get all the nivelSatisfacionList where codigo equals to UPDATED_CODIGO
        defaultNivelSatisfacionShouldNotBeFound("codigo.equals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllNivelSatisfacionsByCodigoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where codigo not equals to DEFAULT_CODIGO
        defaultNivelSatisfacionShouldNotBeFound("codigo.notEquals=" + DEFAULT_CODIGO);

        // Get all the nivelSatisfacionList where codigo not equals to UPDATED_CODIGO
        defaultNivelSatisfacionShouldBeFound("codigo.notEquals=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllNivelSatisfacionsByCodigoIsInShouldWork() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where codigo in DEFAULT_CODIGO or UPDATED_CODIGO
        defaultNivelSatisfacionShouldBeFound("codigo.in=" + DEFAULT_CODIGO + "," + UPDATED_CODIGO);

        // Get all the nivelSatisfacionList where codigo equals to UPDATED_CODIGO
        defaultNivelSatisfacionShouldNotBeFound("codigo.in=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllNivelSatisfacionsByCodigoIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where codigo is not null
        defaultNivelSatisfacionShouldBeFound("codigo.specified=true");

        // Get all the nivelSatisfacionList where codigo is null
        defaultNivelSatisfacionShouldNotBeFound("codigo.specified=false");
    }
                @Test
    @Transactional
    public void getAllNivelSatisfacionsByCodigoContainsSomething() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where codigo contains DEFAULT_CODIGO
        defaultNivelSatisfacionShouldBeFound("codigo.contains=" + DEFAULT_CODIGO);

        // Get all the nivelSatisfacionList where codigo contains UPDATED_CODIGO
        defaultNivelSatisfacionShouldNotBeFound("codigo.contains=" + UPDATED_CODIGO);
    }

    @Test
    @Transactional
    public void getAllNivelSatisfacionsByCodigoNotContainsSomething() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where codigo does not contain DEFAULT_CODIGO
        defaultNivelSatisfacionShouldNotBeFound("codigo.doesNotContain=" + DEFAULT_CODIGO);

        // Get all the nivelSatisfacionList where codigo does not contain UPDATED_CODIGO
        defaultNivelSatisfacionShouldBeFound("codigo.doesNotContain=" + UPDATED_CODIGO);
    }


    @Test
    @Transactional
    public void getAllNivelSatisfacionsByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where descripcion equals to DEFAULT_DESCRIPCION
        defaultNivelSatisfacionShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the nivelSatisfacionList where descripcion equals to UPDATED_DESCRIPCION
        defaultNivelSatisfacionShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllNivelSatisfacionsByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultNivelSatisfacionShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the nivelSatisfacionList where descripcion not equals to UPDATED_DESCRIPCION
        defaultNivelSatisfacionShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllNivelSatisfacionsByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultNivelSatisfacionShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the nivelSatisfacionList where descripcion equals to UPDATED_DESCRIPCION
        defaultNivelSatisfacionShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllNivelSatisfacionsByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where descripcion is not null
        defaultNivelSatisfacionShouldBeFound("descripcion.specified=true");

        // Get all the nivelSatisfacionList where descripcion is null
        defaultNivelSatisfacionShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllNivelSatisfacionsByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where descripcion contains DEFAULT_DESCRIPCION
        defaultNivelSatisfacionShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the nivelSatisfacionList where descripcion contains UPDATED_DESCRIPCION
        defaultNivelSatisfacionShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllNivelSatisfacionsByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        nivelSatisfacionRepository.saveAndFlush(nivelSatisfacion);

        // Get all the nivelSatisfacionList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultNivelSatisfacionShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the nivelSatisfacionList where descripcion does not contain UPDATED_DESCRIPCION
        defaultNivelSatisfacionShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultNivelSatisfacionShouldBeFound(String filter) throws Exception {
        restNivelSatisfacionMockMvc.perform(get("/api/nivel-satisfacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nivelSatisfacion.getId().intValue())))
            .andExpect(jsonPath("$.[*].codigo").value(hasItem(DEFAULT_CODIGO)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)));

        // Check, that the count call also returns 1
        restNivelSatisfacionMockMvc.perform(get("/api/nivel-satisfacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultNivelSatisfacionShouldNotBeFound(String filter) throws Exception {
        restNivelSatisfacionMockMvc.perform(get("/api/nivel-satisfacions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restNivelSatisfacionMockMvc.perform(get("/api/nivel-satisfacions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingNivelSatisfacion() throws Exception {
        // Get the nivelSatisfacion
        restNivelSatisfacionMockMvc.perform(get("/api/nivel-satisfacions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNivelSatisfacion() throws Exception {
        // Initialize the database
        nivelSatisfacionService.save(nivelSatisfacion);

        int databaseSizeBeforeUpdate = nivelSatisfacionRepository.findAll().size();

        // Update the nivelSatisfacion
        NivelSatisfacion updatedNivelSatisfacion = nivelSatisfacionRepository.findById(nivelSatisfacion.getId()).get();
        // Disconnect from session so that the updates on updatedNivelSatisfacion are not directly saved in db
        em.detach(updatedNivelSatisfacion);
        updatedNivelSatisfacion
            .codigo(UPDATED_CODIGO)
            .descripcion(UPDATED_DESCRIPCION);

        restNivelSatisfacionMockMvc.perform(put("/api/nivel-satisfacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedNivelSatisfacion)))
            .andExpect(status().isOk());

        // Validate the NivelSatisfacion in the database
        List<NivelSatisfacion> nivelSatisfacionList = nivelSatisfacionRepository.findAll();
        assertThat(nivelSatisfacionList).hasSize(databaseSizeBeforeUpdate);
        NivelSatisfacion testNivelSatisfacion = nivelSatisfacionList.get(nivelSatisfacionList.size() - 1);
        assertThat(testNivelSatisfacion.getCodigo()).isEqualTo(UPDATED_CODIGO);
        assertThat(testNivelSatisfacion.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingNivelSatisfacion() throws Exception {
        int databaseSizeBeforeUpdate = nivelSatisfacionRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNivelSatisfacionMockMvc.perform(put("/api/nivel-satisfacions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(nivelSatisfacion)))
            .andExpect(status().isBadRequest());

        // Validate the NivelSatisfacion in the database
        List<NivelSatisfacion> nivelSatisfacionList = nivelSatisfacionRepository.findAll();
        assertThat(nivelSatisfacionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNivelSatisfacion() throws Exception {
        // Initialize the database
        nivelSatisfacionService.save(nivelSatisfacion);

        int databaseSizeBeforeDelete = nivelSatisfacionRepository.findAll().size();

        // Delete the nivelSatisfacion
        restNivelSatisfacionMockMvc.perform(delete("/api/nivel-satisfacions/{id}", nivelSatisfacion.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NivelSatisfacion> nivelSatisfacionList = nivelSatisfacionRepository.findAll();
        assertThat(nivelSatisfacionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
