package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.TramitesApp;
import co.gov.sic.tramitesapp.domain.Persona;
import co.gov.sic.tramitesapp.domain.TipoDocumentoIdentificacion;
import co.gov.sic.tramitesapp.domain.TipoPersona;
import co.gov.sic.tramitesapp.repository.PersonaRepository;
import co.gov.sic.tramitesapp.service.PersonaService;
import co.gov.sic.tramitesapp.service.dto.PersonaCriteria;
import co.gov.sic.tramitesapp.service.PersonaQueryService;

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
 * Integration tests for the {@link PersonaResource} REST controller.
 */
@SpringBootTest(classes = TramitesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class PersonaResourceIT {

    private static final String DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRES = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRES = "BBBBBBBBBB";

    private static final String DEFAULT_APELLIDOS = "AAAAAAAAAA";
    private static final String UPDATED_APELLIDOS = "BBBBBBBBBB";

    private static final String DEFAULT_SEGUNDO_APELLIDO = "AAAAAAAAAA";
    private static final String UPDATED_SEGUNDO_APELLIDO = "BBBBBBBBBB";

    private static final String DEFAULT_TELEFONO = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONO = "BBBBBBBBBB";

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private PersonaService personaService;

    @Autowired
    private PersonaQueryService personaQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPersonaMockMvc;

    private Persona persona;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persona createEntity(EntityManager em) {
        Persona persona = new Persona()
            .numeroDocumentoIdentificacion(DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION)
            .nombres(DEFAULT_NOMBRES)
            .apellidos(DEFAULT_APELLIDOS)
            .segundoApellido(DEFAULT_SEGUNDO_APELLIDO)
            .telefono(DEFAULT_TELEFONO)
            .direccion(DEFAULT_DIRECCION)
            .email(DEFAULT_EMAIL);
        // Add required entity
        TipoDocumentoIdentificacion tipoDocumentoIdentificacion;
        if (TestUtil.findAll(em, TipoDocumentoIdentificacion.class).isEmpty()) {
            tipoDocumentoIdentificacion = TipoDocumentoIdentificacionResourceIT.createEntity(em);
            em.persist(tipoDocumentoIdentificacion);
            em.flush();
        } else {
            tipoDocumentoIdentificacion = TestUtil.findAll(em, TipoDocumentoIdentificacion.class).get(0);
        }
        persona.setTipoDocumentoIdentificacion(tipoDocumentoIdentificacion);
        // Add required entity
        TipoPersona tipoPersona;
        if (TestUtil.findAll(em, TipoPersona.class).isEmpty()) {
            tipoPersona = TipoPersonaResourceIT.createEntity(em);
            em.persist(tipoPersona);
            em.flush();
        } else {
            tipoPersona = TestUtil.findAll(em, TipoPersona.class).get(0);
        }
        persona.setTipoPersona(tipoPersona);
        return persona;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Persona createUpdatedEntity(EntityManager em) {
        Persona persona = new Persona()
            .numeroDocumentoIdentificacion(UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION)
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .telefono(UPDATED_TELEFONO)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL);
        // Add required entity
        TipoDocumentoIdentificacion tipoDocumentoIdentificacion;
        if (TestUtil.findAll(em, TipoDocumentoIdentificacion.class).isEmpty()) {
            tipoDocumentoIdentificacion = TipoDocumentoIdentificacionResourceIT.createUpdatedEntity(em);
            em.persist(tipoDocumentoIdentificacion);
            em.flush();
        } else {
            tipoDocumentoIdentificacion = TestUtil.findAll(em, TipoDocumentoIdentificacion.class).get(0);
        }
        persona.setTipoDocumentoIdentificacion(tipoDocumentoIdentificacion);
        // Add required entity
        TipoPersona tipoPersona;
        if (TestUtil.findAll(em, TipoPersona.class).isEmpty()) {
            tipoPersona = TipoPersonaResourceIT.createUpdatedEntity(em);
            em.persist(tipoPersona);
            em.flush();
        } else {
            tipoPersona = TestUtil.findAll(em, TipoPersona.class).get(0);
        }
        persona.setTipoPersona(tipoPersona);
        return persona;
    }

    @BeforeEach
    public void initTest() {
        persona = createEntity(em);
    }

    @Test
    @Transactional
    public void createPersona() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();
        // Create the Persona
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isCreated());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate + 1);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNumeroDocumentoIdentificacion()).isEqualTo(DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION);
        assertThat(testPersona.getNombres()).isEqualTo(DEFAULT_NOMBRES);
        assertThat(testPersona.getApellidos()).isEqualTo(DEFAULT_APELLIDOS);
        assertThat(testPersona.getSegundoApellido()).isEqualTo(DEFAULT_SEGUNDO_APELLIDO);
        assertThat(testPersona.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
        assertThat(testPersona.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testPersona.getEmail()).isEqualTo(DEFAULT_EMAIL);
    }

    @Test
    @Transactional
    public void createPersonaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = personaRepository.findAll().size();

        // Create the Persona with an existing ID
        persona.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNumeroDocumentoIdentificacionIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setNumeroDocumentoIdentificacion(null);

        // Create the Persona, which fails.


        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNombresIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setNombres(null);

        // Create the Persona, which fails.


        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkApellidosIsRequired() throws Exception {
        int databaseSizeBeforeTest = personaRepository.findAll().size();
        // set the field null
        persona.setApellidos(null);

        // Create the Persona, which fails.


        restPersonaMockMvc.perform(post("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPersonas() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList
        restPersonaMockMvc.perform(get("/api/personas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDocumentoIdentificacion").value(hasItem(DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));
    }
    
    @Test
    @Transactional
    public void getPersona() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", persona.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(persona.getId().intValue()))
            .andExpect(jsonPath("$.numeroDocumentoIdentificacion").value(DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION))
            .andExpect(jsonPath("$.nombres").value(DEFAULT_NOMBRES))
            .andExpect(jsonPath("$.apellidos").value(DEFAULT_APELLIDOS))
            .andExpect(jsonPath("$.segundoApellido").value(DEFAULT_SEGUNDO_APELLIDO))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL));
    }


    @Test
    @Transactional
    public void getPersonasByIdFiltering() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        Long id = persona.getId();

        defaultPersonaShouldBeFound("id.equals=" + id);
        defaultPersonaShouldNotBeFound("id.notEquals=" + id);

        defaultPersonaShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPersonaShouldNotBeFound("id.greaterThan=" + id);

        defaultPersonaShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPersonaShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPersonasByNumeroDocumentoIdentificacionIsEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where numeroDocumentoIdentificacion equals to DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldBeFound("numeroDocumentoIdentificacion.equals=" + DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION);

        // Get all the personaList where numeroDocumentoIdentificacion equals to UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldNotBeFound("numeroDocumentoIdentificacion.equals=" + UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPersonasByNumeroDocumentoIdentificacionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where numeroDocumentoIdentificacion not equals to DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldNotBeFound("numeroDocumentoIdentificacion.notEquals=" + DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION);

        // Get all the personaList where numeroDocumentoIdentificacion not equals to UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldBeFound("numeroDocumentoIdentificacion.notEquals=" + UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPersonasByNumeroDocumentoIdentificacionIsInShouldWork() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where numeroDocumentoIdentificacion in DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION or UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldBeFound("numeroDocumentoIdentificacion.in=" + DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION + "," + UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION);

        // Get all the personaList where numeroDocumentoIdentificacion equals to UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldNotBeFound("numeroDocumentoIdentificacion.in=" + UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPersonasByNumeroDocumentoIdentificacionIsNullOrNotNull() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where numeroDocumentoIdentificacion is not null
        defaultPersonaShouldBeFound("numeroDocumentoIdentificacion.specified=true");

        // Get all the personaList where numeroDocumentoIdentificacion is null
        defaultPersonaShouldNotBeFound("numeroDocumentoIdentificacion.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonasByNumeroDocumentoIdentificacionContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where numeroDocumentoIdentificacion contains DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldBeFound("numeroDocumentoIdentificacion.contains=" + DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION);

        // Get all the personaList where numeroDocumentoIdentificacion contains UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldNotBeFound("numeroDocumentoIdentificacion.contains=" + UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION);
    }

    @Test
    @Transactional
    public void getAllPersonasByNumeroDocumentoIdentificacionNotContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where numeroDocumentoIdentificacion does not contain DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldNotBeFound("numeroDocumentoIdentificacion.doesNotContain=" + DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION);

        // Get all the personaList where numeroDocumentoIdentificacion does not contain UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION
        defaultPersonaShouldBeFound("numeroDocumentoIdentificacion.doesNotContain=" + UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION);
    }


    @Test
    @Transactional
    public void getAllPersonasByNombresIsEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where nombres equals to DEFAULT_NOMBRES
        defaultPersonaShouldBeFound("nombres.equals=" + DEFAULT_NOMBRES);

        // Get all the personaList where nombres equals to UPDATED_NOMBRES
        defaultPersonaShouldNotBeFound("nombres.equals=" + UPDATED_NOMBRES);
    }

    @Test
    @Transactional
    public void getAllPersonasByNombresIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where nombres not equals to DEFAULT_NOMBRES
        defaultPersonaShouldNotBeFound("nombres.notEquals=" + DEFAULT_NOMBRES);

        // Get all the personaList where nombres not equals to UPDATED_NOMBRES
        defaultPersonaShouldBeFound("nombres.notEquals=" + UPDATED_NOMBRES);
    }

    @Test
    @Transactional
    public void getAllPersonasByNombresIsInShouldWork() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where nombres in DEFAULT_NOMBRES or UPDATED_NOMBRES
        defaultPersonaShouldBeFound("nombres.in=" + DEFAULT_NOMBRES + "," + UPDATED_NOMBRES);

        // Get all the personaList where nombres equals to UPDATED_NOMBRES
        defaultPersonaShouldNotBeFound("nombres.in=" + UPDATED_NOMBRES);
    }

    @Test
    @Transactional
    public void getAllPersonasByNombresIsNullOrNotNull() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where nombres is not null
        defaultPersonaShouldBeFound("nombres.specified=true");

        // Get all the personaList where nombres is null
        defaultPersonaShouldNotBeFound("nombres.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonasByNombresContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where nombres contains DEFAULT_NOMBRES
        defaultPersonaShouldBeFound("nombres.contains=" + DEFAULT_NOMBRES);

        // Get all the personaList where nombres contains UPDATED_NOMBRES
        defaultPersonaShouldNotBeFound("nombres.contains=" + UPDATED_NOMBRES);
    }

    @Test
    @Transactional
    public void getAllPersonasByNombresNotContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where nombres does not contain DEFAULT_NOMBRES
        defaultPersonaShouldNotBeFound("nombres.doesNotContain=" + DEFAULT_NOMBRES);

        // Get all the personaList where nombres does not contain UPDATED_NOMBRES
        defaultPersonaShouldBeFound("nombres.doesNotContain=" + UPDATED_NOMBRES);
    }


    @Test
    @Transactional
    public void getAllPersonasByApellidosIsEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where apellidos equals to DEFAULT_APELLIDOS
        defaultPersonaShouldBeFound("apellidos.equals=" + DEFAULT_APELLIDOS);

        // Get all the personaList where apellidos equals to UPDATED_APELLIDOS
        defaultPersonaShouldNotBeFound("apellidos.equals=" + UPDATED_APELLIDOS);
    }

    @Test
    @Transactional
    public void getAllPersonasByApellidosIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where apellidos not equals to DEFAULT_APELLIDOS
        defaultPersonaShouldNotBeFound("apellidos.notEquals=" + DEFAULT_APELLIDOS);

        // Get all the personaList where apellidos not equals to UPDATED_APELLIDOS
        defaultPersonaShouldBeFound("apellidos.notEquals=" + UPDATED_APELLIDOS);
    }

    @Test
    @Transactional
    public void getAllPersonasByApellidosIsInShouldWork() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where apellidos in DEFAULT_APELLIDOS or UPDATED_APELLIDOS
        defaultPersonaShouldBeFound("apellidos.in=" + DEFAULT_APELLIDOS + "," + UPDATED_APELLIDOS);

        // Get all the personaList where apellidos equals to UPDATED_APELLIDOS
        defaultPersonaShouldNotBeFound("apellidos.in=" + UPDATED_APELLIDOS);
    }

    @Test
    @Transactional
    public void getAllPersonasByApellidosIsNullOrNotNull() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where apellidos is not null
        defaultPersonaShouldBeFound("apellidos.specified=true");

        // Get all the personaList where apellidos is null
        defaultPersonaShouldNotBeFound("apellidos.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonasByApellidosContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where apellidos contains DEFAULT_APELLIDOS
        defaultPersonaShouldBeFound("apellidos.contains=" + DEFAULT_APELLIDOS);

        // Get all the personaList where apellidos contains UPDATED_APELLIDOS
        defaultPersonaShouldNotBeFound("apellidos.contains=" + UPDATED_APELLIDOS);
    }

    @Test
    @Transactional
    public void getAllPersonasByApellidosNotContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where apellidos does not contain DEFAULT_APELLIDOS
        defaultPersonaShouldNotBeFound("apellidos.doesNotContain=" + DEFAULT_APELLIDOS);

        // Get all the personaList where apellidos does not contain UPDATED_APELLIDOS
        defaultPersonaShouldBeFound("apellidos.doesNotContain=" + UPDATED_APELLIDOS);
    }


    @Test
    @Transactional
    public void getAllPersonasBySegundoApellidoIsEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where segundoApellido equals to DEFAULT_SEGUNDO_APELLIDO
        defaultPersonaShouldBeFound("segundoApellido.equals=" + DEFAULT_SEGUNDO_APELLIDO);

        // Get all the personaList where segundoApellido equals to UPDATED_SEGUNDO_APELLIDO
        defaultPersonaShouldNotBeFound("segundoApellido.equals=" + UPDATED_SEGUNDO_APELLIDO);
    }

    @Test
    @Transactional
    public void getAllPersonasBySegundoApellidoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where segundoApellido not equals to DEFAULT_SEGUNDO_APELLIDO
        defaultPersonaShouldNotBeFound("segundoApellido.notEquals=" + DEFAULT_SEGUNDO_APELLIDO);

        // Get all the personaList where segundoApellido not equals to UPDATED_SEGUNDO_APELLIDO
        defaultPersonaShouldBeFound("segundoApellido.notEquals=" + UPDATED_SEGUNDO_APELLIDO);
    }

    @Test
    @Transactional
    public void getAllPersonasBySegundoApellidoIsInShouldWork() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where segundoApellido in DEFAULT_SEGUNDO_APELLIDO or UPDATED_SEGUNDO_APELLIDO
        defaultPersonaShouldBeFound("segundoApellido.in=" + DEFAULT_SEGUNDO_APELLIDO + "," + UPDATED_SEGUNDO_APELLIDO);

        // Get all the personaList where segundoApellido equals to UPDATED_SEGUNDO_APELLIDO
        defaultPersonaShouldNotBeFound("segundoApellido.in=" + UPDATED_SEGUNDO_APELLIDO);
    }

    @Test
    @Transactional
    public void getAllPersonasBySegundoApellidoIsNullOrNotNull() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where segundoApellido is not null
        defaultPersonaShouldBeFound("segundoApellido.specified=true");

        // Get all the personaList where segundoApellido is null
        defaultPersonaShouldNotBeFound("segundoApellido.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonasBySegundoApellidoContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where segundoApellido contains DEFAULT_SEGUNDO_APELLIDO
        defaultPersonaShouldBeFound("segundoApellido.contains=" + DEFAULT_SEGUNDO_APELLIDO);

        // Get all the personaList where segundoApellido contains UPDATED_SEGUNDO_APELLIDO
        defaultPersonaShouldNotBeFound("segundoApellido.contains=" + UPDATED_SEGUNDO_APELLIDO);
    }

    @Test
    @Transactional
    public void getAllPersonasBySegundoApellidoNotContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where segundoApellido does not contain DEFAULT_SEGUNDO_APELLIDO
        defaultPersonaShouldNotBeFound("segundoApellido.doesNotContain=" + DEFAULT_SEGUNDO_APELLIDO);

        // Get all the personaList where segundoApellido does not contain UPDATED_SEGUNDO_APELLIDO
        defaultPersonaShouldBeFound("segundoApellido.doesNotContain=" + UPDATED_SEGUNDO_APELLIDO);
    }


    @Test
    @Transactional
    public void getAllPersonasByTelefonoIsEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where telefono equals to DEFAULT_TELEFONO
        defaultPersonaShouldBeFound("telefono.equals=" + DEFAULT_TELEFONO);

        // Get all the personaList where telefono equals to UPDATED_TELEFONO
        defaultPersonaShouldNotBeFound("telefono.equals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllPersonasByTelefonoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where telefono not equals to DEFAULT_TELEFONO
        defaultPersonaShouldNotBeFound("telefono.notEquals=" + DEFAULT_TELEFONO);

        // Get all the personaList where telefono not equals to UPDATED_TELEFONO
        defaultPersonaShouldBeFound("telefono.notEquals=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllPersonasByTelefonoIsInShouldWork() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where telefono in DEFAULT_TELEFONO or UPDATED_TELEFONO
        defaultPersonaShouldBeFound("telefono.in=" + DEFAULT_TELEFONO + "," + UPDATED_TELEFONO);

        // Get all the personaList where telefono equals to UPDATED_TELEFONO
        defaultPersonaShouldNotBeFound("telefono.in=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllPersonasByTelefonoIsNullOrNotNull() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where telefono is not null
        defaultPersonaShouldBeFound("telefono.specified=true");

        // Get all the personaList where telefono is null
        defaultPersonaShouldNotBeFound("telefono.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonasByTelefonoContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where telefono contains DEFAULT_TELEFONO
        defaultPersonaShouldBeFound("telefono.contains=" + DEFAULT_TELEFONO);

        // Get all the personaList where telefono contains UPDATED_TELEFONO
        defaultPersonaShouldNotBeFound("telefono.contains=" + UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllPersonasByTelefonoNotContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where telefono does not contain DEFAULT_TELEFONO
        defaultPersonaShouldNotBeFound("telefono.doesNotContain=" + DEFAULT_TELEFONO);

        // Get all the personaList where telefono does not contain UPDATED_TELEFONO
        defaultPersonaShouldBeFound("telefono.doesNotContain=" + UPDATED_TELEFONO);
    }


    @Test
    @Transactional
    public void getAllPersonasByDireccionIsEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where direccion equals to DEFAULT_DIRECCION
        defaultPersonaShouldBeFound("direccion.equals=" + DEFAULT_DIRECCION);

        // Get all the personaList where direccion equals to UPDATED_DIRECCION
        defaultPersonaShouldNotBeFound("direccion.equals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllPersonasByDireccionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where direccion not equals to DEFAULT_DIRECCION
        defaultPersonaShouldNotBeFound("direccion.notEquals=" + DEFAULT_DIRECCION);

        // Get all the personaList where direccion not equals to UPDATED_DIRECCION
        defaultPersonaShouldBeFound("direccion.notEquals=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllPersonasByDireccionIsInShouldWork() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where direccion in DEFAULT_DIRECCION or UPDATED_DIRECCION
        defaultPersonaShouldBeFound("direccion.in=" + DEFAULT_DIRECCION + "," + UPDATED_DIRECCION);

        // Get all the personaList where direccion equals to UPDATED_DIRECCION
        defaultPersonaShouldNotBeFound("direccion.in=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllPersonasByDireccionIsNullOrNotNull() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where direccion is not null
        defaultPersonaShouldBeFound("direccion.specified=true");

        // Get all the personaList where direccion is null
        defaultPersonaShouldNotBeFound("direccion.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonasByDireccionContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where direccion contains DEFAULT_DIRECCION
        defaultPersonaShouldBeFound("direccion.contains=" + DEFAULT_DIRECCION);

        // Get all the personaList where direccion contains UPDATED_DIRECCION
        defaultPersonaShouldNotBeFound("direccion.contains=" + UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void getAllPersonasByDireccionNotContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where direccion does not contain DEFAULT_DIRECCION
        defaultPersonaShouldNotBeFound("direccion.doesNotContain=" + DEFAULT_DIRECCION);

        // Get all the personaList where direccion does not contain UPDATED_DIRECCION
        defaultPersonaShouldBeFound("direccion.doesNotContain=" + UPDATED_DIRECCION);
    }


    @Test
    @Transactional
    public void getAllPersonasByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where email equals to DEFAULT_EMAIL
        defaultPersonaShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the personaList where email equals to UPDATED_EMAIL
        defaultPersonaShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPersonasByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where email not equals to DEFAULT_EMAIL
        defaultPersonaShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the personaList where email not equals to UPDATED_EMAIL
        defaultPersonaShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPersonasByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultPersonaShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the personaList where email equals to UPDATED_EMAIL
        defaultPersonaShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPersonasByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where email is not null
        defaultPersonaShouldBeFound("email.specified=true");

        // Get all the personaList where email is null
        defaultPersonaShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllPersonasByEmailContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where email contains DEFAULT_EMAIL
        defaultPersonaShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the personaList where email contains UPDATED_EMAIL
        defaultPersonaShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllPersonasByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        personaRepository.saveAndFlush(persona);

        // Get all the personaList where email does not contain DEFAULT_EMAIL
        defaultPersonaShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the personaList where email does not contain UPDATED_EMAIL
        defaultPersonaShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllPersonasByTipoDocumentoIdentificacionIsEqualToSomething() throws Exception {
        // Get already existing entity
        TipoDocumentoIdentificacion tipoDocumentoIdentificacion = persona.getTipoDocumentoIdentificacion();
        personaRepository.saveAndFlush(persona);
        Long tipoDocumentoIdentificacionId = tipoDocumentoIdentificacion.getId();

        // Get all the personaList where tipoDocumentoIdentificacion equals to tipoDocumentoIdentificacionId
        defaultPersonaShouldBeFound("tipoDocumentoIdentificacionId.equals=" + tipoDocumentoIdentificacionId);

        // Get all the personaList where tipoDocumentoIdentificacion equals to tipoDocumentoIdentificacionId + 1
        defaultPersonaShouldNotBeFound("tipoDocumentoIdentificacionId.equals=" + (tipoDocumentoIdentificacionId + 1));
    }


    @Test
    @Transactional
    public void getAllPersonasByTipoPersonaIsEqualToSomething() throws Exception {
        // Get already existing entity
        TipoPersona tipoPersona = persona.getTipoPersona();
        personaRepository.saveAndFlush(persona);
        Long tipoPersonaId = tipoPersona.getId();

        // Get all the personaList where tipoPersona equals to tipoPersonaId
        defaultPersonaShouldBeFound("tipoPersonaId.equals=" + tipoPersonaId);

        // Get all the personaList where tipoPersona equals to tipoPersonaId + 1
        defaultPersonaShouldNotBeFound("tipoPersonaId.equals=" + (tipoPersonaId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPersonaShouldBeFound(String filter) throws Exception {
        restPersonaMockMvc.perform(get("/api/personas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(persona.getId().intValue())))
            .andExpect(jsonPath("$.[*].numeroDocumentoIdentificacion").value(hasItem(DEFAULT_NUMERO_DOCUMENTO_IDENTIFICACION)))
            .andExpect(jsonPath("$.[*].nombres").value(hasItem(DEFAULT_NOMBRES)))
            .andExpect(jsonPath("$.[*].apellidos").value(hasItem(DEFAULT_APELLIDOS)))
            .andExpect(jsonPath("$.[*].segundoApellido").value(hasItem(DEFAULT_SEGUNDO_APELLIDO)))
            .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO)))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)));

        // Check, that the count call also returns 1
        restPersonaMockMvc.perform(get("/api/personas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPersonaShouldNotBeFound(String filter) throws Exception {
        restPersonaMockMvc.perform(get("/api/personas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPersonaMockMvc.perform(get("/api/personas/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingPersona() throws Exception {
        // Get the persona
        restPersonaMockMvc.perform(get("/api/personas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePersona() throws Exception {
        // Initialize the database
        personaService.save(persona);

        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // Update the persona
        Persona updatedPersona = personaRepository.findById(persona.getId()).get();
        // Disconnect from session so that the updates on updatedPersona are not directly saved in db
        em.detach(updatedPersona);
        updatedPersona
            .numeroDocumentoIdentificacion(UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION)
            .nombres(UPDATED_NOMBRES)
            .apellidos(UPDATED_APELLIDOS)
            .segundoApellido(UPDATED_SEGUNDO_APELLIDO)
            .telefono(UPDATED_TELEFONO)
            .direccion(UPDATED_DIRECCION)
            .email(UPDATED_EMAIL);

        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPersona)))
            .andExpect(status().isOk());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
        Persona testPersona = personaList.get(personaList.size() - 1);
        assertThat(testPersona.getNumeroDocumentoIdentificacion()).isEqualTo(UPDATED_NUMERO_DOCUMENTO_IDENTIFICACION);
        assertThat(testPersona.getNombres()).isEqualTo(UPDATED_NOMBRES);
        assertThat(testPersona.getApellidos()).isEqualTo(UPDATED_APELLIDOS);
        assertThat(testPersona.getSegundoApellido()).isEqualTo(UPDATED_SEGUNDO_APELLIDO);
        assertThat(testPersona.getTelefono()).isEqualTo(UPDATED_TELEFONO);
        assertThat(testPersona.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testPersona.getEmail()).isEqualTo(UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void updateNonExistingPersona() throws Exception {
        int databaseSizeBeforeUpdate = personaRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPersonaMockMvc.perform(put("/api/personas")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(persona)))
            .andExpect(status().isBadRequest());

        // Validate the Persona in the database
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePersona() throws Exception {
        // Initialize the database
        personaService.save(persona);

        int databaseSizeBeforeDelete = personaRepository.findAll().size();

        // Delete the persona
        restPersonaMockMvc.perform(delete("/api/personas/{id}", persona.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Persona> personaList = personaRepository.findAll();
        assertThat(personaList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
