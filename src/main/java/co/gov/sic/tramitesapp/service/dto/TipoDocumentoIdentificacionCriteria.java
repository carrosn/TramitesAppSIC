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
 * Criteria class for the {@link co.gov.sic.tramitesapp.domain.TipoDocumentoIdentificacion} entity. This class is used
 * in {@link co.gov.sic.tramitesapp.web.rest.TipoDocumentoIdentificacionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tipo-documento-identificacions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TipoDocumentoIdentificacionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter nombre;

    private StringFilter abreviatura;

    public TipoDocumentoIdentificacionCriteria() {
    }

    public TipoDocumentoIdentificacionCriteria(TipoDocumentoIdentificacionCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.nombre = other.nombre == null ? null : other.nombre.copy();
        this.abreviatura = other.abreviatura == null ? null : other.abreviatura.copy();
    }

    @Override
    public TipoDocumentoIdentificacionCriteria copy() {
        return new TipoDocumentoIdentificacionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getAbreviatura() {
        return abreviatura;
    }

    public void setAbreviatura(StringFilter abreviatura) {
        this.abreviatura = abreviatura;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TipoDocumentoIdentificacionCriteria that = (TipoDocumentoIdentificacionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(nombre, that.nombre) &&
            Objects.equals(abreviatura, that.abreviatura);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        nombre,
        abreviatura
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TipoDocumentoIdentificacionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (abreviatura != null ? "abreviatura=" + abreviatura + ", " : "") +
            "}";
    }

}
