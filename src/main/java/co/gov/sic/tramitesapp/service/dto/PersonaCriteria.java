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
 * Criteria class for the {@link co.gov.sic.tramitesapp.domain.Persona} entity. This class is used
 * in {@link co.gov.sic.tramitesapp.web.rest.PersonaResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /personas?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PersonaCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numeroDocumentoIdentificacion;

    private StringFilter nombres;

    private StringFilter apellidos;

    private StringFilter segundoApellido;

    private StringFilter telefono;

    private StringFilter direccion;

    private StringFilter email;

    private LongFilter tipoDocumentoIdentificacionId;

    private LongFilter tipoPersonaId;

    public PersonaCriteria() {
    }

    public PersonaCriteria(PersonaCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numeroDocumentoIdentificacion = other.numeroDocumentoIdentificacion == null ? null : other.numeroDocumentoIdentificacion.copy();
        this.nombres = other.nombres == null ? null : other.nombres.copy();
        this.apellidos = other.apellidos == null ? null : other.apellidos.copy();
        this.segundoApellido = other.segundoApellido == null ? null : other.segundoApellido.copy();
        this.telefono = other.telefono == null ? null : other.telefono.copy();
        this.direccion = other.direccion == null ? null : other.direccion.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.tipoDocumentoIdentificacionId = other.tipoDocumentoIdentificacionId == null ? null : other.tipoDocumentoIdentificacionId.copy();
        this.tipoPersonaId = other.tipoPersonaId == null ? null : other.tipoPersonaId.copy();
    }

    @Override
    public PersonaCriteria copy() {
        return new PersonaCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumeroDocumentoIdentificacion() {
        return numeroDocumentoIdentificacion;
    }

    public void setNumeroDocumentoIdentificacion(StringFilter numeroDocumentoIdentificacion) {
        this.numeroDocumentoIdentificacion = numeroDocumentoIdentificacion;
    }

    public StringFilter getNombres() {
        return nombres;
    }

    public void setNombres(StringFilter nombres) {
        this.nombres = nombres;
    }

    public StringFilter getApellidos() {
        return apellidos;
    }

    public void setApellidos(StringFilter apellidos) {
        this.apellidos = apellidos;
    }

    public StringFilter getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(StringFilter segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public StringFilter getTelefono() {
        return telefono;
    }

    public void setTelefono(StringFilter telefono) {
        this.telefono = telefono;
    }

    public StringFilter getDireccion() {
        return direccion;
    }

    public void setDireccion(StringFilter direccion) {
        this.direccion = direccion;
    }

    public StringFilter getEmail() {
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public LongFilter getTipoDocumentoIdentificacionId() {
        return tipoDocumentoIdentificacionId;
    }

    public void setTipoDocumentoIdentificacionId(LongFilter tipoDocumentoIdentificacionId) {
        this.tipoDocumentoIdentificacionId = tipoDocumentoIdentificacionId;
    }

    public LongFilter getTipoPersonaId() {
        return tipoPersonaId;
    }

    public void setTipoPersonaId(LongFilter tipoPersonaId) {
        this.tipoPersonaId = tipoPersonaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PersonaCriteria that = (PersonaCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numeroDocumentoIdentificacion, that.numeroDocumentoIdentificacion) &&
            Objects.equals(nombres, that.nombres) &&
            Objects.equals(apellidos, that.apellidos) &&
            Objects.equals(segundoApellido, that.segundoApellido) &&
            Objects.equals(telefono, that.telefono) &&
            Objects.equals(direccion, that.direccion) &&
            Objects.equals(email, that.email) &&
            Objects.equals(tipoDocumentoIdentificacionId, that.tipoDocumentoIdentificacionId) &&
            Objects.equals(tipoPersonaId, that.tipoPersonaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numeroDocumentoIdentificacion,
        nombres,
        apellidos,
        segundoApellido,
        telefono,
        direccion,
        email,
        tipoDocumentoIdentificacionId,
        tipoPersonaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PersonaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numeroDocumentoIdentificacion != null ? "numeroDocumentoIdentificacion=" + numeroDocumentoIdentificacion + ", " : "") +
                (nombres != null ? "nombres=" + nombres + ", " : "") +
                (apellidos != null ? "apellidos=" + apellidos + ", " : "") +
                (segundoApellido != null ? "segundoApellido=" + segundoApellido + ", " : "") +
                (telefono != null ? "telefono=" + telefono + ", " : "") +
                (direccion != null ? "direccion=" + direccion + ", " : "") +
                (email != null ? "email=" + email + ", " : "") +
                (tipoDocumentoIdentificacionId != null ? "tipoDocumentoIdentificacionId=" + tipoDocumentoIdentificacionId + ", " : "") +
                (tipoPersonaId != null ? "tipoPersonaId=" + tipoPersonaId + ", " : "") +
            "}";
    }

}
