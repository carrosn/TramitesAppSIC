package co.gov.sic.tramitesapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link co.gov.sic.tramitesapp.domain.Encuesta} entity. This class is used
 * in {@link co.gov.sic.tramitesapp.web.rest.EncuestaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /encuestas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EncuestaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter preguntaOne;

    private StringFilter niveSatisfacion;

    private LongFilter nivelSatisfacionId;

    public EncuestaCriteria() {
    }

    public EncuestaCriteria(EncuestaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.preguntaOne = other.preguntaOne == null ? null : other.preguntaOne.copy();
        this.niveSatisfacion = other.niveSatisfacion == null ? null : other.niveSatisfacion.copy();
        this.nivelSatisfacionId = other.nivelSatisfacionId == null ? null : other.nivelSatisfacionId.copy();
    }

    @Override
    public EncuestaCriteria copy() {
        return new EncuestaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getPreguntaOne() {
        return preguntaOne;
    }

    public void setPreguntaOne(StringFilter preguntaOne) {
        this.preguntaOne = preguntaOne;
    }

    public StringFilter getNiveSatisfacion() {
        return niveSatisfacion;
    }

    public void setNiveSatisfacion(StringFilter niveSatisfacion) {
        this.niveSatisfacion = niveSatisfacion;
    }

    public LongFilter getNivelSatisfacionId() {
        return nivelSatisfacionId;
    }

    public void setNivelSatisfacionId(LongFilter nivelSatisfacionId) {
        this.nivelSatisfacionId = nivelSatisfacionId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EncuestaCriteria that = (EncuestaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(preguntaOne, that.preguntaOne) &&
            Objects.equals(niveSatisfacion, that.niveSatisfacion) &&
            Objects.equals(nivelSatisfacionId, that.nivelSatisfacionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        preguntaOne,
        niveSatisfacion,
        nivelSatisfacionId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EncuestaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (preguntaOne != null ? "preguntaOne=" + preguntaOne + ", " : "") +
                (niveSatisfacion != null ? "niveSatisfacion=" + niveSatisfacion + ", " : "") +
                (nivelSatisfacionId != null ? "nivelSatisfacionId=" + nivelSatisfacionId + ", " : "") +
            "}";
    }

}
