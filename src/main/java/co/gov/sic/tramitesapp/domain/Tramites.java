package co.gov.sic.tramitesapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Tramites.
 */
@Entity
@Table(name = "tramites")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tramites implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "numero_tramite", nullable = false)
    private String numeroTramite;

    @NotNull
    @Column(name = "anno", nullable = false)
    private String anno;

    @NotNull
    @Column(name = "nombre_tramite", nullable = false)
    private String nombreTramite;

    @NotNull
    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @NotNull
    @Column(name = "persona_radica", nullable = false)
    private String personaRadica;

    @NotNull
    @Column(name = "funcionario", nullable = false)
    private String funcionario;

    @ManyToOne
    @JsonIgnoreProperties(value = "tramites", allowSetters = true)
    private Persona radicaPersona;

    @ManyToOne
    @JsonIgnoreProperties(value = "tramites", allowSetters = true)
    private Persona funcionarioPersona;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTramite() {
        return numeroTramite;
    }

    public Tramites numeroTramite(String numeroTramite) {
        this.numeroTramite = numeroTramite;
        return this;
    }

    public void setNumeroTramite(String numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public String getAnno() {
        return anno;
    }

    public Tramites anno(String anno) {
        this.anno = anno;
        return this;
    }

    public void setAnno(String anno) {
        this.anno = anno;
    }

    public String getNombreTramite() {
        return nombreTramite;
    }

    public Tramites nombreTramite(String nombreTramite) {
        this.nombreTramite = nombreTramite;
        return this;
    }

    public void setNombreTramite(String nombreTramite) {
        this.nombreTramite = nombreTramite;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Tramites descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPersonaRadica() {
        return personaRadica;
    }

    public Tramites personaRadica(String personaRadica) {
        this.personaRadica = personaRadica;
        return this;
    }

    public void setPersonaRadica(String personaRadica) {
        this.personaRadica = personaRadica;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public Tramites funcionario(String funcionario) {
        this.funcionario = funcionario;
        return this;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public Persona getRadicaPersona() {
        return radicaPersona;
    }

    public Tramites radicaPersona(Persona persona) {
        this.radicaPersona = persona;
        return this;
    }

    public void setRadicaPersona(Persona persona) {
        this.radicaPersona = persona;
    }

    public Persona getFuncionarioPersona() {
        return funcionarioPersona;
    }

    public Tramites funcionarioPersona(Persona persona) {
        this.funcionarioPersona = persona;
        return this;
    }

    public void setFuncionarioPersona(Persona persona) {
        this.funcionarioPersona = persona;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tramites)) {
            return false;
        }
        return id != null && id.equals(((Tramites) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Tramites{" +
            "id=" + getId() +
            ", numeroTramite='" + getNumeroTramite() + "'" +
            ", anno='" + getAnno() + "'" +
            ", nombreTramite='" + getNombreTramite() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", personaRadica='" + getPersonaRadica() + "'" +
            ", funcionario='" + getFuncionario() + "'" +
            "}";
    }
}
