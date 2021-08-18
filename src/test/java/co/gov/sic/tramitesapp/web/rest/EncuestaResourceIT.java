package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.TramitesApp;
import co.gov.sic.tramitesapp.domain.Encuesta;
import co.gov.sic.tramitesapp.domain.NivelSatisfacion;
import co.gov.sic.tramitesapp.repository.EncuestaRepository;
import co.gov.sic.tramitesapp.service.EncuestaService;
import co.gov.sic.tramitesapp.service.dto.EncuestaCriteria;
import co.gov.sic.tramitesapp.service.EncuestaQueryService;

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
 * Integration tests for the {@link EncuestaResource} REST controller.
 */
@SpringBootTest(classes = TramitesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EncuestaResourceIT {

    private static final String DEFAULT_PREGUNTA_ONE = "AAAAAAAAAA";
    private static final String UPDATED_PREGUNTA_ONE = "BBBBBBBBBB";

    private static final String DEFAULT_NIVE_SATISFACION = "AAAAAAAAAA";
    private static final String UPDATED_NIVE_SATISFACION = "BBBBBBBBBB";

    @Autowired
    private EncuestaRepository encuestaRepository;

    @Autowired
    private EncuestaService encuestaService;

    @Autowired
    private EncuestaQueryService encuestaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEncuestaMockMvc;

    private Encuesta encuesta;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encuesta createEntity(EntityManager em) {
        Encuesta encuesta = new Encuesta()
            .preguntaOne(DEFAULT_PREGUNTA_ONE)
            .niveSatisfacion(DEFAULT_NIVE_SATISFACION);
        // Add required entity
        NivelSatisfacion nivelSatisfacion;
        if (TestUtil.findAll(em, NivelSatisfacion.class).isEmpty()) {
            nivelSatisfacion = NivelSatisfacionResourceIT.createEntity(em);
            em.persist(nivelSatisfacion);
            em.flush();
        } else {
            nivelSatisfacion = TestUtil.findAll(em, NivelSatisfacion.class).get(0);
        }
        encuesta.setNivelSatisfacion(nivelSatisfacion);
        return encuesta;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Encuesta createUpdatedEntity(EntityManager em) {
        Encuesta encuesta = new Encuesta()
            .preguntaOne(UPDATED_PREGUNTA_ONE)
            .niveSatisfacion(UPDATED_NIVE_SATISFACION);
        // Add required entity
        NivelSatisfacion nivelSatisfacion;
        if (TestUtil.findAll(em, NivelSatisfacion.class).isEmpty()) {
            nivelSatisfacion = NivelSatisfacionResourceIT.createUpdatedEntity(em);
            em.persist(nivelSatisfacion);
            em.flush();
        } else {
            nivelSatisfacion = TestUtil.findAll(em, NivelSatisfacion.class).get(0);
        }
        encuesta.setNivelSatisfacion(nivelSatisfacion);
        return encuesta;
    }

    @BeforeEach
    public void initTest() {
        encuesta = createEntity(em);
    }

    @Test
    @Transactional
    public void createEncuesta() throws Exception {
        int databaseSizeBeforeCreate = encuestaRepository.findAll().size();
        // Create the Encuesta
        restEncuestaMockMvc.perform(post("/api/encuestas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuesta)))
            .andExpect(status().isCreated());

        // Validate the Encuesta in the database
        List<Encuesta> encuestaList = encuestaRepository.findAll();
        assertThat(encuestaList).hasSize(databaseSizeBeforeCreate + 1);
        Encuesta testEncuesta = encuestaList.get(encuestaList.size() - 1);
        assertThat(testEncuesta.getPreguntaOne()).isEqualTo(DEFAULT_PREGUNTA_ONE);
        assertThat(testEncuesta.getNiveSatisfacion()).isEqualTo(DEFAULT_NIVE_SATISFACION);
    }

    @Test
    @Transactional
    public void createEncuestaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = encuestaRepository.findAll().size();

        // Create the Encuesta with an existing ID
        encuesta.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEncuestaMockMvc.perform(post("/api/encuestas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuesta)))
            .andExpect(status().isBadRequest());

        // Validate the Encuesta in the database
        List<Encuesta> encuestaList = encuestaRepository.findAll();
        assertThat(encuestaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPreguntaOneIsRequired() throws Exception {
        int databaseSizeBeforeTest = encuestaRepository.findAll().size();
        // set the field null
        encuesta.setPreguntaOne(null);

        // Create the Encuesta, which fails.


        restEncuestaMockMvc.perform(post("/api/encuestas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuesta)))
            .andExpect(status().isBadRequest());

        List<Encuesta> encuestaList = encuestaRepository.findAll();
        assertThat(encuestaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNiveSatisfacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = encuestaRepository.findAll().size();
        // set the field null
        encuesta.setNiveSatisfacion(null);

        // Create the Encuesta, which fails.


        restEncuestaMockMvc.perform(post("/api/encuestas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuesta)))
            .andExpect(status().isBadRequest());

        List<Encuesta> encuestaList = encuestaRepository.findAll();
        assertThat(encuestaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEncuestas() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList
        restEncuestaMockMvc.perform(get("/api/encuestas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encuesta.getId().intValue())))
            .andExpect(jsonPath("$.[*].preguntaOne").value(hasItem(DEFAULT_PREGUNTA_ONE)))
            .andExpect(jsonPath("$.[*].niveSatisfacion").value(hasItem(DEFAULT_NIVE_SATISFACION)));
    }
    
    @Test
    @Transactional
    public void getEncuesta() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get the encuesta
        restEncuestaMockMvc.perform(get("/api/encuestas/{id}", encuesta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(encuesta.getId().intValue()))
            .andExpect(jsonPath("$.preguntaOne").value(DEFAULT_PREGUNTA_ONE))
            .andExpect(jsonPath("$.niveSatisfacion").value(DEFAULT_NIVE_SATISFACION));
    }


    @Test
    @Transactional
    public void getEncuestasByIdFiltering() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        Long id = encuesta.getId();

        defaultEncuestaShouldBeFound("id.equals=" + id);
        defaultEncuestaShouldNotBeFound("id.notEquals=" + id);

        defaultEncuestaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultEncuestaShouldNotBeFound("id.greaterThan=" + id);

        defaultEncuestaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultEncuestaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllEncuestasByPreguntaOneIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where preguntaOne equals to DEFAULT_PREGUNTA_ONE
        defaultEncuestaShouldBeFound("preguntaOne.equals=" + DEFAULT_PREGUNTA_ONE);

        // Get all the encuestaList where preguntaOne equals to UPDATED_PREGUNTA_ONE
        defaultEncuestaShouldNotBeFound("preguntaOne.equals=" + UPDATED_PREGUNTA_ONE);
    }

    @Test
    @Transactional
    public void getAllEncuestasByPreguntaOneIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where preguntaOne not equals to DEFAULT_PREGUNTA_ONE
        defaultEncuestaShouldNotBeFound("preguntaOne.notEquals=" + DEFAULT_PREGUNTA_ONE);

        // Get all the encuestaList where preguntaOne not equals to UPDATED_PREGUNTA_ONE
        defaultEncuestaShouldBeFound("preguntaOne.notEquals=" + UPDATED_PREGUNTA_ONE);
    }

    @Test
    @Transactional
    public void getAllEncuestasByPreguntaOneIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where preguntaOne in DEFAULT_PREGUNTA_ONE or UPDATED_PREGUNTA_ONE
        defaultEncuestaShouldBeFound("preguntaOne.in=" + DEFAULT_PREGUNTA_ONE + "," + UPDATED_PREGUNTA_ONE);

        // Get all the encuestaList where preguntaOne equals to UPDATED_PREGUNTA_ONE
        defaultEncuestaShouldNotBeFound("preguntaOne.in=" + UPDATED_PREGUNTA_ONE);
    }

    @Test
    @Transactional
    public void getAllEncuestasByPreguntaOneIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where preguntaOne is not null
        defaultEncuestaShouldBeFound("preguntaOne.specified=true");

        // Get all the encuestaList where preguntaOne is null
        defaultEncuestaShouldNotBeFound("preguntaOne.specified=false");
    }
                @Test
    @Transactional
    public void getAllEncuestasByPreguntaOneContainsSomething() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where preguntaOne contains DEFAULT_PREGUNTA_ONE
        defaultEncuestaShouldBeFound("preguntaOne.contains=" + DEFAULT_PREGUNTA_ONE);

        // Get all the encuestaList where preguntaOne contains UPDATED_PREGUNTA_ONE
        defaultEncuestaShouldNotBeFound("preguntaOne.contains=" + UPDATED_PREGUNTA_ONE);
    }

    @Test
    @Transactional
    public void getAllEncuestasByPreguntaOneNotContainsSomething() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where preguntaOne does not contain DEFAULT_PREGUNTA_ONE
        defaultEncuestaShouldNotBeFound("preguntaOne.doesNotContain=" + DEFAULT_PREGUNTA_ONE);

        // Get all the encuestaList where preguntaOne does not contain UPDATED_PREGUNTA_ONE
        defaultEncuestaShouldBeFound("preguntaOne.doesNotContain=" + UPDATED_PREGUNTA_ONE);
    }


    @Test
    @Transactional
    public void getAllEncuestasByNiveSatisfacionIsEqualToSomething() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where niveSatisfacion equals to DEFAULT_NIVE_SATISFACION
        defaultEncuestaShouldBeFound("niveSatisfacion.equals=" + DEFAULT_NIVE_SATISFACION);

        // Get all the encuestaList where niveSatisfacion equals to UPDATED_NIVE_SATISFACION
        defaultEncuestaShouldNotBeFound("niveSatisfacion.equals=" + UPDATED_NIVE_SATISFACION);
    }

    @Test
    @Transactional
    public void getAllEncuestasByNiveSatisfacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where niveSatisfacion not equals to DEFAULT_NIVE_SATISFACION
        defaultEncuestaShouldNotBeFound("niveSatisfacion.notEquals=" + DEFAULT_NIVE_SATISFACION);

        // Get all the encuestaList where niveSatisfacion not equals to UPDATED_NIVE_SATISFACION
        defaultEncuestaShouldBeFound("niveSatisfacion.notEquals=" + UPDATED_NIVE_SATISFACION);
    }

    @Test
    @Transactional
    public void getAllEncuestasByNiveSatisfacionIsInShouldWork() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where niveSatisfacion in DEFAULT_NIVE_SATISFACION or UPDATED_NIVE_SATISFACION
        defaultEncuestaShouldBeFound("niveSatisfacion.in=" + DEFAULT_NIVE_SATISFACION + "," + UPDATED_NIVE_SATISFACION);

        // Get all the encuestaList where niveSatisfacion equals to UPDATED_NIVE_SATISFACION
        defaultEncuestaShouldNotBeFound("niveSatisfacion.in=" + UPDATED_NIVE_SATISFACION);
    }

    @Test
    @Transactional
    public void getAllEncuestasByNiveSatisfacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where niveSatisfacion is not null
        defaultEncuestaShouldBeFound("niveSatisfacion.specified=true");

        // Get all the encuestaList where niveSatisfacion is null
        defaultEncuestaShouldNotBeFound("niveSatisfacion.specified=false");
    }
                @Test
    @Transactional
    public void getAllEncuestasByNiveSatisfacionContainsSomething() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where niveSatisfacion contains DEFAULT_NIVE_SATISFACION
        defaultEncuestaShouldBeFound("niveSatisfacion.contains=" + DEFAULT_NIVE_SATISFACION);

        // Get all the encuestaList where niveSatisfacion contains UPDATED_NIVE_SATISFACION
        defaultEncuestaShouldNotBeFound("niveSatisfacion.contains=" + UPDATED_NIVE_SATISFACION);
    }

    @Test
    @Transactional
    public void getAllEncuestasByNiveSatisfacionNotContainsSomething() throws Exception {
        // Initialize the database
        encuestaRepository.saveAndFlush(encuesta);

        // Get all the encuestaList where niveSatisfacion does not contain DEFAULT_NIVE_SATISFACION
        defaultEncuestaShouldNotBeFound("niveSatisfacion.doesNotContain=" + DEFAULT_NIVE_SATISFACION);

        // Get all the encuestaList where niveSatisfacion does not contain UPDATED_NIVE_SATISFACION
        defaultEncuestaShouldBeFound("niveSatisfacion.doesNotContain=" + UPDATED_NIVE_SATISFACION);
    }


    @Test
    @Transactional
    public void getAllEncuestasByNivelSatisfacionIsEqualToSomething() throws Exception {
        // Get already existing entity
        NivelSatisfacion nivelSatisfacion = encuesta.getNivelSatisfacion();
        encuestaRepository.saveAndFlush(encuesta);
        Long nivelSatisfacionId = nivelSatisfacion.getId();

        // Get all the encuestaList where nivelSatisfacion equals to nivelSatisfacionId
        defaultEncuestaShouldBeFound("nivelSatisfacionId.equals=" + nivelSatisfacionId);

        // Get all the encuestaList where nivelSatisfacion equals to nivelSatisfacionId + 1
        defaultEncuestaShouldNotBeFound("nivelSatisfacionId.equals=" + (nivelSatisfacionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEncuestaShouldBeFound(String filter) throws Exception {
        restEncuestaMockMvc.perform(get("/api/encuestas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(encuesta.getId().intValue())))
            .andExpect(jsonPath("$.[*].preguntaOne").value(hasItem(DEFAULT_PREGUNTA_ONE)))
            .andExpect(jsonPath("$.[*].niveSatisfacion").value(hasItem(DEFAULT_NIVE_SATISFACION)));

        // Check, that the count call also returns 1
        restEncuestaMockMvc.perform(get("/api/encuestas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEncuestaShouldNotBeFound(String filter) throws Exception {
        restEncuestaMockMvc.perform(get("/api/encuestas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEncuestaMockMvc.perform(get("/api/encuestas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingEncuesta() throws Exception {
        // Get the encuesta
        restEncuestaMockMvc.perform(get("/api/encuestas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEncuesta() throws Exception {
        // Initialize the database
        encuestaService.save(encuesta);

        int databaseSizeBeforeUpdate = encuestaRepository.findAll().size();

        // Update the encuesta
        Encuesta updatedEncuesta = encuestaRepository.findById(encuesta.getId()).get();
        // Disconnect from session so that the updates on updatedEncuesta are not directly saved in db
        em.detach(updatedEncuesta);
        updatedEncuesta
            .preguntaOne(UPDATED_PREGUNTA_ONE)
            .niveSatisfacion(UPDATED_NIVE_SATISFACION);

        restEncuestaMockMvc.perform(put("/api/encuestas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedEncuesta)))
            .andExpect(status().isOk());

        // Validate the Encuesta in the database
        List<Encuesta> encuestaList = encuestaRepository.findAll();
        assertThat(encuestaList).hasSize(databaseSizeBeforeUpdate);
        Encuesta testEncuesta = encuestaList.get(encuestaList.size() - 1);
        assertThat(testEncuesta.getPreguntaOne()).isEqualTo(UPDATED_PREGUNTA_ONE);
        assertThat(testEncuesta.getNiveSatisfacion()).isEqualTo(UPDATED_NIVE_SATISFACION);
    }

    @Test
    @Transactional
    public void updateNonExistingEncuesta() throws Exception {
        int databaseSizeBeforeUpdate = encuestaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEncuestaMockMvc.perform(put("/api/encuestas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(encuesta)))
            .andExpect(status().isBadRequest());

        // Validate the Encuesta in the database
        List<Encuesta> encuestaList = encuestaRepository.findAll();
        assertThat(encuestaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEncuesta() throws Exception {
        // Initialize the database
        encuestaService.save(encuesta);

        int databaseSizeBeforeDelete = encuestaRepository.findAll().size();

        // Delete the encuesta
        restEncuestaMockMvc.perform(delete("/api/encuestas/{id}", encuesta.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Encuesta> encuestaList = encuestaRepository.findAll();
        assertThat(encuestaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
