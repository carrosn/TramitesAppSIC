package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.TramitesApp;
import co.gov.sic.tramitesapp.domain.Tramites;
import co.gov.sic.tramitesapp.domain.Persona;
import co.gov.sic.tramitesapp.repository.TramitesRepository;
import co.gov.sic.tramitesapp.service.TramitesService;
import co.gov.sic.tramitesapp.service.dto.TramitesCriteria;
import co.gov.sic.tramitesapp.service.TramitesQueryService;

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
 * Integration tests for the {@link TramitesResource} REST controller.
 */
@SpringBootTest(classes = TramitesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class TramitesResourceIT {

    private static final String DEFAULT_NUMERO_TRAMITE = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_TRAMITE = "BBBBBBBBBB";

    private static final String DEFAULT_ANNO = "AAAAAAAAAA";
    private static final String UPDATED_ANNO = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_TRAMITE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_TRAMITE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_PERSONA_RADICA = "AAAAAAAAAA";
    private static final String UPDATED_PERSONA_RADICA = "BBBBBBBBBB";

    private static final String DEFAULT_FUNCIONARIO = "AAAAAAAAAA";
    private static final String UPDATED_FUNCIONARIO = "BBBBBBBBBB";

    @Autowired
    private TramitesRepository tramitesRepository;

    @Autowired
    private TramitesService tramitesService;

    @Autowired
    private TramitesQueryService tramitesQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTramitesMockMvc;

    private Tramites tramites;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tramites createEntity(EntityManager em) {
        Tramites tramites = new Tramites()
            .numeroTramite(DEFAULT_NUMERO_TRAMITE)
            .anno(DEFAULT_ANNO)
            .nombreTramite(DEFAULT_NOMBRE_TRAMITE)
            .descripcion(DEFAULT_DESCRIPCION)
            .personaRadica(DEFAULT_PERSONA_RADICA)
            .funcionario(DEFAULT_FUNCIONARIO);
        return tramites;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tramites createUpdatedEntity(EntityManager em) {
        Tramites tramites = new Tramites()
            .numeroTramite(UPDATED_NUMERO_TRAMITE)
            .anno(UPDATED_ANNO)
            .nombreTramite(UPDATED_NOMBRE_TRAMITE)
            .descripcion(UPDATED_DESCRIPCION)
            .personaRadica(UPDATED_PERSONA_RADICA)
            .funcionario(UPDATED_FUNCIONARIO);
        return tramites;
    }

    @BeforeEach
    public void initTest() {
        tramites = createEntity(em);
    }

    @Test
    @Transactional
    public void createTramites() throws Exception {
        int databaseSizeBeforeCreate = tramitesRepository.findAll().size();
        // Create the Tramites
        restTramitesMockMvc.perform(post("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tramites)))
            .andExpect(status().isCreated());

        // Validate the Tramites in the database
        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeCreate + 1);
        Tramites testTramites = tramitesList.get(tramitesList.size() - 1);
        assertThat(testTramites.getNumeroTramite()).isEqualTo(DEFAULT_NUMERO_TRAMITE);
        assertThat(testTramites.getAnno()).isEqualTo(DEFAULT_ANNO);
        assertThat(testTramites.getNombreTramite()).isEqualTo(DEFAULT_NOMBRE_TRAMITE);
        assertThat(testTramites.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTramites.getPersonaRadica()).isEqualTo(DEFAULT_PERSONA_RADICA);
        assertThat(testTramites.getFuncionario()).isEqualTo(DEFAULT_FUNCIONARIO);
    }

    @Test
    @Transactional
    public void createTramitesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tramitesRepository.findAll().size();

        // Create the Tramites with an existing ID
        tramites.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTramitesMockMvc.perform(post("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tramites)))
            .andExpect(status().isBadRequest());

        // Validate the Tramites in the database
        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroTramiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = tramitesRepository.findAll().size();
        // set the field null
        tramites.setNumeroTramite(null);

        // Create the Tramites, which fails.


        restTramitesMockMvc.perform(post("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tramites)))
            .andExpect(status().isBadRequest());

        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAnnoIsRequired() throws Exception {
        int databaseSizeBeforeTest = tramitesRepository.findAll().size();
        // set the field null
        tramites.setAnno(null);

        // Create the Tramites, which fails.


        restTramitesMockMvc.perform(post("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tramites)))
            .andExpect(status().isBadRequest());

        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombreTramiteIsRequired() throws Exception {
        int databaseSizeBeforeTest = tramitesRepository.findAll().size();
        // set the field null
        tramites.setNombreTramite(null);

        // Create the Tramites, which fails.


        restTramitesMockMvc.perform(post("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tramites)))
            .andExpect(status().isBadRequest());

        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescripcionIsRequired() throws Exception {
        int databaseSizeBeforeTest = tramitesRepository.findAll().size();
        // set the field null
        tramites.setDescripcion(null);

        // Create the Tramites, which fails.


        restTramitesMockMvc.perform(post("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tramites)))
            .andExpect(status().isBadRequest());

        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPersonaRadicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = tramitesRepository.findAll().size();
        // set the field null
        tramites.setPersonaRadica(null);

        // Create the Tramites, which fails.


        restTramitesMockMvc.perform(post("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tramites)))
            .andExpect(status().isBadRequest());

        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFuncionarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = tramitesRepository.findAll().size();
        // set the field null
        tramites.setFuncionario(null);

        // Create the Tramites, which fails.


        restTramitesMockMvc.perform(post("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tramites)))
            .andExpect(status().isBadRequest());

        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTramites() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList
        restTramitesMockMvc.perform(get("/api/tramites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tramites.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroTramite").value(hasItem(DEFAULT_NUMERO_TRAMITE)))
            .andExpect(jsonPath("$.[*].anno").value(hasItem(DEFAULT_ANNO)))
            .andExpect(jsonPath("$.[*].nombreTramite").value(hasItem(DEFAULT_NOMBRE_TRAMITE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].personaRadica").value(hasItem(DEFAULT_PERSONA_RADICA)))
            .andExpect(jsonPath("$.[*].funcionario").value(hasItem(DEFAULT_FUNCIONARIO)));
    }
    
    @Test
    @Transactional
    public void getTramites() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get the tramites
        restTramitesMockMvc.perform(get("/api/tramites/{id}", tramites.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tramites.getId().intValue()))
            .andExpect(jsonPath("$.numeroTramite").value(DEFAULT_NUMERO_TRAMITE))
            .andExpect(jsonPath("$.anno").value(DEFAULT_ANNO))
            .andExpect(jsonPath("$.nombreTramite").value(DEFAULT_NOMBRE_TRAMITE))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION))
            .andExpect(jsonPath("$.personaRadica").value(DEFAULT_PERSONA_RADICA))
            .andExpect(jsonPath("$.funcionario").value(DEFAULT_FUNCIONARIO));
    }


    @Test
    @Transactional
    public void getTramitesByIdFiltering() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        Long id = tramites.getId();

        defaultTramitesShouldBeFound("id.equals=" + id);
        defaultTramitesShouldNotBeFound("id.notEquals=" + id);

        defaultTramitesShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultTramitesShouldNotBeFound("id.greaterThan=" + id);

        defaultTramitesShouldBeFound("id.lessThanOrEqual=" + id);
        defaultTramitesShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllTramitesByNumeroTramiteIsEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where numeroTramite equals to DEFAULT_NUMERO_TRAMITE
        defaultTramitesShouldBeFound("numeroTramite.equals=" + DEFAULT_NUMERO_TRAMITE);

        // Get all the tramitesList where numeroTramite equals to UPDATED_NUMERO_TRAMITE
        defaultTramitesShouldNotBeFound("numeroTramite.equals=" + UPDATED_NUMERO_TRAMITE);
    }

    @Test
    @Transactional
    public void getAllTramitesByNumeroTramiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where numeroTramite not equals to DEFAULT_NUMERO_TRAMITE
        defaultTramitesShouldNotBeFound("numeroTramite.notEquals=" + DEFAULT_NUMERO_TRAMITE);

        // Get all the tramitesList where numeroTramite not equals to UPDATED_NUMERO_TRAMITE
        defaultTramitesShouldBeFound("numeroTramite.notEquals=" + UPDATED_NUMERO_TRAMITE);
    }

    @Test
    @Transactional
    public void getAllTramitesByNumeroTramiteIsInShouldWork() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where numeroTramite in DEFAULT_NUMERO_TRAMITE or UPDATED_NUMERO_TRAMITE
        defaultTramitesShouldBeFound("numeroTramite.in=" + DEFAULT_NUMERO_TRAMITE + "," + UPDATED_NUMERO_TRAMITE);

        // Get all the tramitesList where numeroTramite equals to UPDATED_NUMERO_TRAMITE
        defaultTramitesShouldNotBeFound("numeroTramite.in=" + UPDATED_NUMERO_TRAMITE);
    }

    @Test
    @Transactional
    public void getAllTramitesByNumeroTramiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where numeroTramite is not null
        defaultTramitesShouldBeFound("numeroTramite.specified=true");

        // Get all the tramitesList where numeroTramite is null
        defaultTramitesShouldNotBeFound("numeroTramite.specified=false");
    }
                @Test
    @Transactional
    public void getAllTramitesByNumeroTramiteContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where numeroTramite contains DEFAULT_NUMERO_TRAMITE
        defaultTramitesShouldBeFound("numeroTramite.contains=" + DEFAULT_NUMERO_TRAMITE);

        // Get all the tramitesList where numeroTramite contains UPDATED_NUMERO_TRAMITE
        defaultTramitesShouldNotBeFound("numeroTramite.contains=" + UPDATED_NUMERO_TRAMITE);
    }

    @Test
    @Transactional
    public void getAllTramitesByNumeroTramiteNotContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where numeroTramite does not contain DEFAULT_NUMERO_TRAMITE
        defaultTramitesShouldNotBeFound("numeroTramite.doesNotContain=" + DEFAULT_NUMERO_TRAMITE);

        // Get all the tramitesList where numeroTramite does not contain UPDATED_NUMERO_TRAMITE
        defaultTramitesShouldBeFound("numeroTramite.doesNotContain=" + UPDATED_NUMERO_TRAMITE);
    }


    @Test
    @Transactional
    public void getAllTramitesByAnnoIsEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where anno equals to DEFAULT_ANNO
        defaultTramitesShouldBeFound("anno.equals=" + DEFAULT_ANNO);

        // Get all the tramitesList where anno equals to UPDATED_ANNO
        defaultTramitesShouldNotBeFound("anno.equals=" + UPDATED_ANNO);
    }

    @Test
    @Transactional
    public void getAllTramitesByAnnoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where anno not equals to DEFAULT_ANNO
        defaultTramitesShouldNotBeFound("anno.notEquals=" + DEFAULT_ANNO);

        // Get all the tramitesList where anno not equals to UPDATED_ANNO
        defaultTramitesShouldBeFound("anno.notEquals=" + UPDATED_ANNO);
    }

    @Test
    @Transactional
    public void getAllTramitesByAnnoIsInShouldWork() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where anno in DEFAULT_ANNO or UPDATED_ANNO
        defaultTramitesShouldBeFound("anno.in=" + DEFAULT_ANNO + "," + UPDATED_ANNO);

        // Get all the tramitesList where anno equals to UPDATED_ANNO
        defaultTramitesShouldNotBeFound("anno.in=" + UPDATED_ANNO);
    }

    @Test
    @Transactional
    public void getAllTramitesByAnnoIsNullOrNotNull() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where anno is not null
        defaultTramitesShouldBeFound("anno.specified=true");

        // Get all the tramitesList where anno is null
        defaultTramitesShouldNotBeFound("anno.specified=false");
    }
                @Test
    @Transactional
    public void getAllTramitesByAnnoContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where anno contains DEFAULT_ANNO
        defaultTramitesShouldBeFound("anno.contains=" + DEFAULT_ANNO);

        // Get all the tramitesList where anno contains UPDATED_ANNO
        defaultTramitesShouldNotBeFound("anno.contains=" + UPDATED_ANNO);
    }

    @Test
    @Transactional
    public void getAllTramitesByAnnoNotContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where anno does not contain DEFAULT_ANNO
        defaultTramitesShouldNotBeFound("anno.doesNotContain=" + DEFAULT_ANNO);

        // Get all the tramitesList where anno does not contain UPDATED_ANNO
        defaultTramitesShouldBeFound("anno.doesNotContain=" + UPDATED_ANNO);
    }


    @Test
    @Transactional
    public void getAllTramitesByNombreTramiteIsEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where nombreTramite equals to DEFAULT_NOMBRE_TRAMITE
        defaultTramitesShouldBeFound("nombreTramite.equals=" + DEFAULT_NOMBRE_TRAMITE);

        // Get all the tramitesList where nombreTramite equals to UPDATED_NOMBRE_TRAMITE
        defaultTramitesShouldNotBeFound("nombreTramite.equals=" + UPDATED_NOMBRE_TRAMITE);
    }

    @Test
    @Transactional
    public void getAllTramitesByNombreTramiteIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where nombreTramite not equals to DEFAULT_NOMBRE_TRAMITE
        defaultTramitesShouldNotBeFound("nombreTramite.notEquals=" + DEFAULT_NOMBRE_TRAMITE);

        // Get all the tramitesList where nombreTramite not equals to UPDATED_NOMBRE_TRAMITE
        defaultTramitesShouldBeFound("nombreTramite.notEquals=" + UPDATED_NOMBRE_TRAMITE);
    }

    @Test
    @Transactional
    public void getAllTramitesByNombreTramiteIsInShouldWork() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where nombreTramite in DEFAULT_NOMBRE_TRAMITE or UPDATED_NOMBRE_TRAMITE
        defaultTramitesShouldBeFound("nombreTramite.in=" + DEFAULT_NOMBRE_TRAMITE + "," + UPDATED_NOMBRE_TRAMITE);

        // Get all the tramitesList where nombreTramite equals to UPDATED_NOMBRE_TRAMITE
        defaultTramitesShouldNotBeFound("nombreTramite.in=" + UPDATED_NOMBRE_TRAMITE);
    }

    @Test
    @Transactional
    public void getAllTramitesByNombreTramiteIsNullOrNotNull() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where nombreTramite is not null
        defaultTramitesShouldBeFound("nombreTramite.specified=true");

        // Get all the tramitesList where nombreTramite is null
        defaultTramitesShouldNotBeFound("nombreTramite.specified=false");
    }
                @Test
    @Transactional
    public void getAllTramitesByNombreTramiteContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where nombreTramite contains DEFAULT_NOMBRE_TRAMITE
        defaultTramitesShouldBeFound("nombreTramite.contains=" + DEFAULT_NOMBRE_TRAMITE);

        // Get all the tramitesList where nombreTramite contains UPDATED_NOMBRE_TRAMITE
        defaultTramitesShouldNotBeFound("nombreTramite.contains=" + UPDATED_NOMBRE_TRAMITE);
    }

    @Test
    @Transactional
    public void getAllTramitesByNombreTramiteNotContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where nombreTramite does not contain DEFAULT_NOMBRE_TRAMITE
        defaultTramitesShouldNotBeFound("nombreTramite.doesNotContain=" + DEFAULT_NOMBRE_TRAMITE);

        // Get all the tramitesList where nombreTramite does not contain UPDATED_NOMBRE_TRAMITE
        defaultTramitesShouldBeFound("nombreTramite.doesNotContain=" + UPDATED_NOMBRE_TRAMITE);
    }


    @Test
    @Transactional
    public void getAllTramitesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where descripcion equals to DEFAULT_DESCRIPCION
        defaultTramitesShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the tramitesList where descripcion equals to UPDATED_DESCRIPCION
        defaultTramitesShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTramitesByDescripcionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where descripcion not equals to DEFAULT_DESCRIPCION
        defaultTramitesShouldNotBeFound("descripcion.notEquals=" + DEFAULT_DESCRIPCION);

        // Get all the tramitesList where descripcion not equals to UPDATED_DESCRIPCION
        defaultTramitesShouldBeFound("descripcion.notEquals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTramitesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultTramitesShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the tramitesList where descripcion equals to UPDATED_DESCRIPCION
        defaultTramitesShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTramitesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where descripcion is not null
        defaultTramitesShouldBeFound("descripcion.specified=true");

        // Get all the tramitesList where descripcion is null
        defaultTramitesShouldNotBeFound("descripcion.specified=false");
    }
                @Test
    @Transactional
    public void getAllTramitesByDescripcionContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where descripcion contains DEFAULT_DESCRIPCION
        defaultTramitesShouldBeFound("descripcion.contains=" + DEFAULT_DESCRIPCION);

        // Get all the tramitesList where descripcion contains UPDATED_DESCRIPCION
        defaultTramitesShouldNotBeFound("descripcion.contains=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTramitesByDescripcionNotContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where descripcion does not contain DEFAULT_DESCRIPCION
        defaultTramitesShouldNotBeFound("descripcion.doesNotContain=" + DEFAULT_DESCRIPCION);

        // Get all the tramitesList where descripcion does not contain UPDATED_DESCRIPCION
        defaultTramitesShouldBeFound("descripcion.doesNotContain=" + UPDATED_DESCRIPCION);
    }


    @Test
    @Transactional
    public void getAllTramitesByPersonaRadicaIsEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where personaRadica equals to DEFAULT_PERSONA_RADICA
        defaultTramitesShouldBeFound("personaRadica.equals=" + DEFAULT_PERSONA_RADICA);

        // Get all the tramitesList where personaRadica equals to UPDATED_PERSONA_RADICA
        defaultTramitesShouldNotBeFound("personaRadica.equals=" + UPDATED_PERSONA_RADICA);
    }

    @Test
    @Transactional
    public void getAllTramitesByPersonaRadicaIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where personaRadica not equals to DEFAULT_PERSONA_RADICA
        defaultTramitesShouldNotBeFound("personaRadica.notEquals=" + DEFAULT_PERSONA_RADICA);

        // Get all the tramitesList where personaRadica not equals to UPDATED_PERSONA_RADICA
        defaultTramitesShouldBeFound("personaRadica.notEquals=" + UPDATED_PERSONA_RADICA);
    }

    @Test
    @Transactional
    public void getAllTramitesByPersonaRadicaIsInShouldWork() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where personaRadica in DEFAULT_PERSONA_RADICA or UPDATED_PERSONA_RADICA
        defaultTramitesShouldBeFound("personaRadica.in=" + DEFAULT_PERSONA_RADICA + "," + UPDATED_PERSONA_RADICA);

        // Get all the tramitesList where personaRadica equals to UPDATED_PERSONA_RADICA
        defaultTramitesShouldNotBeFound("personaRadica.in=" + UPDATED_PERSONA_RADICA);
    }

    @Test
    @Transactional
    public void getAllTramitesByPersonaRadicaIsNullOrNotNull() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where personaRadica is not null
        defaultTramitesShouldBeFound("personaRadica.specified=true");

        // Get all the tramitesList where personaRadica is null
        defaultTramitesShouldNotBeFound("personaRadica.specified=false");
    }
                @Test
    @Transactional
    public void getAllTramitesByPersonaRadicaContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where personaRadica contains DEFAULT_PERSONA_RADICA
        defaultTramitesShouldBeFound("personaRadica.contains=" + DEFAULT_PERSONA_RADICA);

        // Get all the tramitesList where personaRadica contains UPDATED_PERSONA_RADICA
        defaultTramitesShouldNotBeFound("personaRadica.contains=" + UPDATED_PERSONA_RADICA);
    }

    @Test
    @Transactional
    public void getAllTramitesByPersonaRadicaNotContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where personaRadica does not contain DEFAULT_PERSONA_RADICA
        defaultTramitesShouldNotBeFound("personaRadica.doesNotContain=" + DEFAULT_PERSONA_RADICA);

        // Get all the tramitesList where personaRadica does not contain UPDATED_PERSONA_RADICA
        defaultTramitesShouldBeFound("personaRadica.doesNotContain=" + UPDATED_PERSONA_RADICA);
    }


    @Test
    @Transactional
    public void getAllTramitesByFuncionarioIsEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where funcionario equals to DEFAULT_FUNCIONARIO
        defaultTramitesShouldBeFound("funcionario.equals=" + DEFAULT_FUNCIONARIO);

        // Get all the tramitesList where funcionario equals to UPDATED_FUNCIONARIO
        defaultTramitesShouldNotBeFound("funcionario.equals=" + UPDATED_FUNCIONARIO);
    }

    @Test
    @Transactional
    public void getAllTramitesByFuncionarioIsNotEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where funcionario not equals to DEFAULT_FUNCIONARIO
        defaultTramitesShouldNotBeFound("funcionario.notEquals=" + DEFAULT_FUNCIONARIO);

        // Get all the tramitesList where funcionario not equals to UPDATED_FUNCIONARIO
        defaultTramitesShouldBeFound("funcionario.notEquals=" + UPDATED_FUNCIONARIO);
    }

    @Test
    @Transactional
    public void getAllTramitesByFuncionarioIsInShouldWork() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where funcionario in DEFAULT_FUNCIONARIO or UPDATED_FUNCIONARIO
        defaultTramitesShouldBeFound("funcionario.in=" + DEFAULT_FUNCIONARIO + "," + UPDATED_FUNCIONARIO);

        // Get all the tramitesList where funcionario equals to UPDATED_FUNCIONARIO
        defaultTramitesShouldNotBeFound("funcionario.in=" + UPDATED_FUNCIONARIO);
    }

    @Test
    @Transactional
    public void getAllTramitesByFuncionarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where funcionario is not null
        defaultTramitesShouldBeFound("funcionario.specified=true");

        // Get all the tramitesList where funcionario is null
        defaultTramitesShouldNotBeFound("funcionario.specified=false");
    }
                @Test
    @Transactional
    public void getAllTramitesByFuncionarioContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where funcionario contains DEFAULT_FUNCIONARIO
        defaultTramitesShouldBeFound("funcionario.contains=" + DEFAULT_FUNCIONARIO);

        // Get all the tramitesList where funcionario contains UPDATED_FUNCIONARIO
        defaultTramitesShouldNotBeFound("funcionario.contains=" + UPDATED_FUNCIONARIO);
    }

    @Test
    @Transactional
    public void getAllTramitesByFuncionarioNotContainsSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);

        // Get all the tramitesList where funcionario does not contain DEFAULT_FUNCIONARIO
        defaultTramitesShouldNotBeFound("funcionario.doesNotContain=" + DEFAULT_FUNCIONARIO);

        // Get all the tramitesList where funcionario does not contain UPDATED_FUNCIONARIO
        defaultTramitesShouldBeFound("funcionario.doesNotContain=" + UPDATED_FUNCIONARIO);
    }


    @Test
    @Transactional
    public void getAllTramitesByRadicaPersonaIsEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);
        Persona radicaPersona = PersonaResourceIT.createEntity(em);
        em.persist(radicaPersona);
        em.flush();
        tramites.setRadicaPersona(radicaPersona);
        tramitesRepository.saveAndFlush(tramites);
        Long radicaPersonaId = radicaPersona.getId();

        // Get all the tramitesList where radicaPersona equals to radicaPersonaId
        defaultTramitesShouldBeFound("radicaPersonaId.equals=" + radicaPersonaId);

        // Get all the tramitesList where radicaPersona equals to radicaPersonaId + 1
        defaultTramitesShouldNotBeFound("radicaPersonaId.equals=" + (radicaPersonaId + 1));
    }


    @Test
    @Transactional
    public void getAllTramitesByFuncionarioPersonaIsEqualToSomething() throws Exception {
        // Initialize the database
        tramitesRepository.saveAndFlush(tramites);
        Persona funcionarioPersona = PersonaResourceIT.createEntity(em);
        em.persist(funcionarioPersona);
        em.flush();
        tramites.setFuncionarioPersona(funcionarioPersona);
        tramitesRepository.saveAndFlush(tramites);
        Long funcionarioPersonaId = funcionarioPersona.getId();

        // Get all the tramitesList where funcionarioPersona equals to funcionarioPersonaId
        defaultTramitesShouldBeFound("funcionarioPersonaId.equals=" + funcionarioPersonaId);

        // Get all the tramitesList where funcionarioPersona equals to funcionarioPersonaId + 1
        defaultTramitesShouldNotBeFound("funcionarioPersonaId.equals=" + (funcionarioPersonaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTramitesShouldBeFound(String filter) throws Exception {
        restTramitesMockMvc.perform(get("/api/tramites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tramites.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroTramite").value(hasItem(DEFAULT_NUMERO_TRAMITE)))
            .andExpect(jsonPath("$.[*].anno").value(hasItem(DEFAULT_ANNO)))
            .andExpect(jsonPath("$.[*].nombreTramite").value(hasItem(DEFAULT_NOMBRE_TRAMITE)))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION)))
            .andExpect(jsonPath("$.[*].personaRadica").value(hasItem(DEFAULT_PERSONA_RADICA)))
            .andExpect(jsonPath("$.[*].funcionario").value(hasItem(DEFAULT_FUNCIONARIO)));

        // Check, that the count call also returns 1
        restTramitesMockMvc.perform(get("/api/tramites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTramitesShouldNotBeFound(String filter) throws Exception {
        restTramitesMockMvc.perform(get("/api/tramites?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTramitesMockMvc.perform(get("/api/tramites/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingTramites() throws Exception {
        // Get the tramites
        restTramitesMockMvc.perform(get("/api/tramites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTramites() throws Exception {
        // Initialize the database
        tramitesService.save(tramites);

        int databaseSizeBeforeUpdate = tramitesRepository.findAll().size();

        // Update the tramites
        Tramites updatedTramites = tramitesRepository.findById(tramites.getId()).get();
        // Disconnect from session so that the updates on updatedTramites are not directly saved in db
        em.detach(updatedTramites);
        updatedTramites
            .numeroTramite(UPDATED_NUMERO_TRAMITE)
            .anno(UPDATED_ANNO)
            .nombreTramite(UPDATED_NOMBRE_TRAMITE)
            .descripcion(UPDATED_DESCRIPCION)
            .personaRadica(UPDATED_PERSONA_RADICA)
            .funcionario(UPDATED_FUNCIONARIO);

        restTramitesMockMvc.perform(put("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedTramites)))
            .andExpect(status().isOk());

        // Validate the Tramites in the database
        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeUpdate);
        Tramites testTramites = tramitesList.get(tramitesList.size() - 1);
        assertThat(testTramites.getNumeroTramite()).isEqualTo(UPDATED_NUMERO_TRAMITE);
        assertThat(testTramites.getAnno()).isEqualTo(UPDATED_ANNO);
        assertThat(testTramites.getNombreTramite()).isEqualTo(UPDATED_NOMBRE_TRAMITE);
        assertThat(testTramites.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTramites.getPersonaRadica()).isEqualTo(UPDATED_PERSONA_RADICA);
        assertThat(testTramites.getFuncionario()).isEqualTo(UPDATED_FUNCIONARIO);
    }

    @Test
    @Transactional
    public void updateNonExistingTramites() throws Exception {
        int databaseSizeBeforeUpdate = tramitesRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTramitesMockMvc.perform(put("/api/tramites")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(tramites)))
            .andExpect(status().isBadRequest());

        // Validate the Tramites in the database
        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTramites() throws Exception {
        // Initialize the database
        tramitesService.save(tramites);

        int databaseSizeBeforeDelete = tramitesRepository.findAll().size();

        // Delete the tramites
        restTramitesMockMvc.perform(delete("/api/tramites/{id}", tramites.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Tramites> tramitesList = tramitesRepository.findAll();
        assertThat(tramitesList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
