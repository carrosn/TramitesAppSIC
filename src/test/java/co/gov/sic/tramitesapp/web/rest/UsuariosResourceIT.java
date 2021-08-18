package co.gov.sic.tramitesapp.web.rest;

import co.gov.sic.tramitesapp.TramitesApp;
import co.gov.sic.tramitesapp.domain.Usuarios;
import co.gov.sic.tramitesapp.repository.UsuariosRepository;
import co.gov.sic.tramitesapp.service.UsuariosService;
import co.gov.sic.tramitesapp.service.dto.UsuariosCriteria;
import co.gov.sic.tramitesapp.service.UsuariosQueryService;

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
 * Integration tests for the {@link UsuariosResource} REST controller.
 */
@SpringBootTest(classes = TramitesApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class UsuariosResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private UsuariosService usuariosService;

    @Autowired
    private UsuariosQueryService usuariosQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUsuariosMockMvc;

    private Usuarios usuarios;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuarios createEntity(EntityManager em) {
        Usuarios usuarios = new Usuarios()
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .gender(DEFAULT_GENDER)
            .status(DEFAULT_STATUS);
        return usuarios;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Usuarios createUpdatedEntity(EntityManager em) {
        Usuarios usuarios = new Usuarios()
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .gender(UPDATED_GENDER)
            .status(UPDATED_STATUS);
        return usuarios;
    }

    @BeforeEach
    public void initTest() {
        usuarios = createEntity(em);
    }

    @Test
    @Transactional
    public void createUsuarios() throws Exception {
        int databaseSizeBeforeCreate = usuariosRepository.findAll().size();
        // Create the Usuarios
        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isCreated());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate + 1);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testUsuarios.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testUsuarios.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUsuarios.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createUsuariosWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = usuariosRepository.findAll().size();

        // Create the Usuarios with an existing ID
        usuarios.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().size();
        // set the field null
        usuarios.setName(null);

        // Create the Usuarios, which fails.


        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().size();
        // set the field null
        usuarios.setEmail(null);

        // Create the Usuarios, which fails.


        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().size();
        // set the field null
        usuarios.setGender(null);

        // Create the Usuarios, which fails.


        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = usuariosRepository.findAll().size();
        // set the field null
        usuarios.setStatus(null);

        // Create the Usuarios, which fails.


        restUsuariosMockMvc.perform(post("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList
        restUsuariosMockMvc.perform(get("/api/usuarios?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getUsuarios() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get the usuarios
        restUsuariosMockMvc.perform(get("/api/usuarios/{id}", usuarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(usuarios.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }


    @Test
    @Transactional
    public void getUsuariosByIdFiltering() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        Long id = usuarios.getId();

        defaultUsuariosShouldBeFound("id.equals=" + id);
        defaultUsuariosShouldNotBeFound("id.notEquals=" + id);

        defaultUsuariosShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUsuariosShouldNotBeFound("id.greaterThan=" + id);

        defaultUsuariosShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUsuariosShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllUsuariosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where name equals to DEFAULT_NAME
        defaultUsuariosShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the usuariosList where name equals to UPDATED_NAME
        defaultUsuariosShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where name not equals to DEFAULT_NAME
        defaultUsuariosShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the usuariosList where name not equals to UPDATED_NAME
        defaultUsuariosShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where name in DEFAULT_NAME or UPDATED_NAME
        defaultUsuariosShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the usuariosList where name equals to UPDATED_NAME
        defaultUsuariosShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where name is not null
        defaultUsuariosShouldBeFound("name.specified=true");

        // Get all the usuariosList where name is null
        defaultUsuariosShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByNameContainsSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where name contains DEFAULT_NAME
        defaultUsuariosShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the usuariosList where name contains UPDATED_NAME
        defaultUsuariosShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllUsuariosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where name does not contain DEFAULT_NAME
        defaultUsuariosShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the usuariosList where name does not contain UPDATED_NAME
        defaultUsuariosShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllUsuariosByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where email equals to DEFAULT_EMAIL
        defaultUsuariosShouldBeFound("email.equals=" + DEFAULT_EMAIL);

        // Get all the usuariosList where email equals to UPDATED_EMAIL
        defaultUsuariosShouldNotBeFound("email.equals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEmailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where email not equals to DEFAULT_EMAIL
        defaultUsuariosShouldNotBeFound("email.notEquals=" + DEFAULT_EMAIL);

        // Get all the usuariosList where email not equals to UPDATED_EMAIL
        defaultUsuariosShouldBeFound("email.notEquals=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where email in DEFAULT_EMAIL or UPDATED_EMAIL
        defaultUsuariosShouldBeFound("email.in=" + DEFAULT_EMAIL + "," + UPDATED_EMAIL);

        // Get all the usuariosList where email equals to UPDATED_EMAIL
        defaultUsuariosShouldNotBeFound("email.in=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where email is not null
        defaultUsuariosShouldBeFound("email.specified=true");

        // Get all the usuariosList where email is null
        defaultUsuariosShouldNotBeFound("email.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByEmailContainsSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where email contains DEFAULT_EMAIL
        defaultUsuariosShouldBeFound("email.contains=" + DEFAULT_EMAIL);

        // Get all the usuariosList where email contains UPDATED_EMAIL
        defaultUsuariosShouldNotBeFound("email.contains=" + UPDATED_EMAIL);
    }

    @Test
    @Transactional
    public void getAllUsuariosByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where email does not contain DEFAULT_EMAIL
        defaultUsuariosShouldNotBeFound("email.doesNotContain=" + DEFAULT_EMAIL);

        // Get all the usuariosList where email does not contain UPDATED_EMAIL
        defaultUsuariosShouldBeFound("email.doesNotContain=" + UPDATED_EMAIL);
    }


    @Test
    @Transactional
    public void getAllUsuariosByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where gender equals to DEFAULT_GENDER
        defaultUsuariosShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the usuariosList where gender equals to UPDATED_GENDER
        defaultUsuariosShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUsuariosByGenderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where gender not equals to DEFAULT_GENDER
        defaultUsuariosShouldNotBeFound("gender.notEquals=" + DEFAULT_GENDER);

        // Get all the usuariosList where gender not equals to UPDATED_GENDER
        defaultUsuariosShouldBeFound("gender.notEquals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUsuariosByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultUsuariosShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the usuariosList where gender equals to UPDATED_GENDER
        defaultUsuariosShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUsuariosByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where gender is not null
        defaultUsuariosShouldBeFound("gender.specified=true");

        // Get all the usuariosList where gender is null
        defaultUsuariosShouldNotBeFound("gender.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByGenderContainsSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where gender contains DEFAULT_GENDER
        defaultUsuariosShouldBeFound("gender.contains=" + DEFAULT_GENDER);

        // Get all the usuariosList where gender contains UPDATED_GENDER
        defaultUsuariosShouldNotBeFound("gender.contains=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUsuariosByGenderNotContainsSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where gender does not contain DEFAULT_GENDER
        defaultUsuariosShouldNotBeFound("gender.doesNotContain=" + DEFAULT_GENDER);

        // Get all the usuariosList where gender does not contain UPDATED_GENDER
        defaultUsuariosShouldBeFound("gender.doesNotContain=" + UPDATED_GENDER);
    }


    @Test
    @Transactional
    public void getAllUsuariosByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where status equals to DEFAULT_STATUS
        defaultUsuariosShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the usuariosList where status equals to UPDATED_STATUS
        defaultUsuariosShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsuariosByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where status not equals to DEFAULT_STATUS
        defaultUsuariosShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the usuariosList where status not equals to UPDATED_STATUS
        defaultUsuariosShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsuariosByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultUsuariosShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the usuariosList where status equals to UPDATED_STATUS
        defaultUsuariosShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsuariosByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where status is not null
        defaultUsuariosShouldBeFound("status.specified=true");

        // Get all the usuariosList where status is null
        defaultUsuariosShouldNotBeFound("status.specified=false");
    }
                @Test
    @Transactional
    public void getAllUsuariosByStatusContainsSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where status contains DEFAULT_STATUS
        defaultUsuariosShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the usuariosList where status contains UPDATED_STATUS
        defaultUsuariosShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void getAllUsuariosByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        usuariosRepository.saveAndFlush(usuarios);

        // Get all the usuariosList where status does not contain DEFAULT_STATUS
        defaultUsuariosShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the usuariosList where status does not contain UPDATED_STATUS
        defaultUsuariosShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUsuariosShouldBeFound(String filter) throws Exception {
        restUsuariosMockMvc.perform(get("/api/usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(usuarios.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));

        // Check, that the count call also returns 1
        restUsuariosMockMvc.perform(get("/api/usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUsuariosShouldNotBeFound(String filter) throws Exception {
        restUsuariosMockMvc.perform(get("/api/usuarios?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUsuariosMockMvc.perform(get("/api/usuarios/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    public void getNonExistingUsuarios() throws Exception {
        // Get the usuarios
        restUsuariosMockMvc.perform(get("/api/usuarios/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUsuarios() throws Exception {
        // Initialize the database
        usuariosService.save(usuarios);

        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();

        // Update the usuarios
        Usuarios updatedUsuarios = usuariosRepository.findById(usuarios.getId()).get();
        // Disconnect from session so that the updates on updatedUsuarios are not directly saved in db
        em.detach(updatedUsuarios);
        updatedUsuarios
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .gender(UPDATED_GENDER)
            .status(UPDATED_STATUS);

        restUsuariosMockMvc.perform(put("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedUsuarios)))
            .andExpect(status().isOk());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
        Usuarios testUsuarios = usuariosList.get(usuariosList.size() - 1);
        assertThat(testUsuarios.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testUsuarios.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testUsuarios.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUsuarios.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingUsuarios() throws Exception {
        int databaseSizeBeforeUpdate = usuariosRepository.findAll().size();

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUsuariosMockMvc.perform(put("/api/usuarios")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(usuarios)))
            .andExpect(status().isBadRequest());

        // Validate the Usuarios in the database
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUsuarios() throws Exception {
        // Initialize the database
        usuariosService.save(usuarios);

        int databaseSizeBeforeDelete = usuariosRepository.findAll().size();

        // Delete the usuarios
        restUsuariosMockMvc.perform(delete("/api/usuarios/{id}", usuarios.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Usuarios> usuariosList = usuariosRepository.findAll();
        assertThat(usuariosList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
