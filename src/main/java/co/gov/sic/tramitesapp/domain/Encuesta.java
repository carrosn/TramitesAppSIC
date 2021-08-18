package co.gov.sic.tramitesapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Encuesta.
 */
@Entity
@Table(name = "encuesta")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Encuesta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "pregunta_one", nullable = false)
    private String preguntaOne;

    @NotNull
    @Column(name = "nive_satisfacion", nullable = false)
    private String niveSatisfacion;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = "encuestas", allowSetters = true)
    private NivelSatisfacion nivelSatisfacion;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPreguntaOne() {
        return preguntaOne;
    }

    public Encuesta preguntaOne(String preguntaOne) {
        this.preguntaOne = preguntaOne;
        return this;
    }

    public void setPreguntaOne(String preguntaOne) {
        this.preguntaOne = preguntaOne;
    }

    public String getNiveSatisfacion() {
        return niveSatisfacion;
    }

    public Encuesta niveSatisfacion(String niveSatisfacion) {
        this.niveSatisfacion = niveSatisfacion;
        return this;
    }

    public void setNiveSatisfacion(String niveSatisfacion) {
        this.niveSatisfacion = niveSatisfacion;
    }

    public NivelSatisfacion getNivelSatisfacion() {
        return nivelSatisfacion;
    }

    public Encuesta nivelSatisfacion(NivelSatisfacion nivelSatisfacion) {
        this.nivelSatisfacion = nivelSatisfacion;
        return this;
    }

    public void setNivelSatisfacion(NivelSatisfacion nivelSatisfacion) {
        this.nivelSatisfacion = nivelSatisfacion;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Encuesta)) {
            return false;
        }
        return id != null && id.equals(((Encuesta) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Encuesta{" +
            "id=" + getId() +
            ", preguntaOne='" + getPreguntaOne() + "'" +
            ", niveSatisfacion='" + getNiveSatisfacion() + "'" +
            "}";
    }
}
