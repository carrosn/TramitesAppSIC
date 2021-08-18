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
 * Criteria class for the {@link co.gov.sic.tramitesapp.domain.Tramites} entity. This class is used
 * in {@link co.gov.sic.tramitesapp.web.rest.TramitesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /tramites?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TramitesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter numeroTramite;

    private StringFilter anno;

    private StringFilter nombreTramite;

    private StringFilter descripcion;

    private StringFilter personaRadica;

    private StringFilter funcionario;

    private LongFilter radicaPersonaId;

    private LongFilter funcionarioPersonaId;

    public TramitesCriteria() {
    }

    public TramitesCriteria(TramitesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.numeroTramite = other.numeroTramite == null ? null : other.numeroTramite.copy();
        this.anno = other.anno == null ? null : other.anno.copy();
        this.nombreTramite = other.nombreTramite == null ? null : other.nombreTramite.copy();
        this.descripcion = other.descripcion == null ? null : other.descripcion.copy();
        this.personaRadica = other.personaRadica == null ? null : other.personaRadica.copy();
        this.funcionario = other.funcionario == null ? null : other.funcionario.copy();
        this.radicaPersonaId = other.radicaPersonaId == null ? null : other.radicaPersonaId.copy();
        this.funcionarioPersonaId = other.funcionarioPersonaId == null ? null : other.funcionarioPersonaId.copy();
    }

    @Override
    public TramitesCriteria copy() {
        return new TramitesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getNumeroTramite() {
        return numeroTramite;
    }

    public void setNumeroTramite(StringFilter numeroTramite) {
        this.numeroTramite = numeroTramite;
    }

    public StringFilter getAnno() {
        return anno;
    }

    public void setAnno(StringFilter anno) {
        this.anno = anno;
    }

    public StringFilter getNombreTramite() {
        return nombreTramite;
    }

    public void setNombreTramite(StringFilter nombreTramite) {
        this.nombreTramite = nombreTramite;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public StringFilter getPersonaRadica() {
        return personaRadica;
    }

    public void setPersonaRadica(StringFilter personaRadica) {
        this.personaRadica = personaRadica;
    }

    public StringFilter getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(StringFilter funcionario) {
        this.funcionario = funcionario;
    }

    public LongFilter getRadicaPersonaId() {
        return radicaPersonaId;
    }

    public void setRadicaPersonaId(LongFilter radicaPersonaId) {
        this.radicaPersonaId = radicaPersonaId;
    }

    public LongFilter getFuncionarioPersonaId() {
        return funcionarioPersonaId;
    }

    public void setFuncionarioPersonaId(LongFilter funcionarioPersonaId) {
        this.funcionarioPersonaId = funcionarioPersonaId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TramitesCriteria that = (TramitesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(numeroTramite, that.numeroTramite) &&
            Objects.equals(anno, that.anno) &&
            Objects.equals(nombreTramite, that.nombreTramite) &&
            Objects.equals(descripcion, that.descripcion) &&
            Objects.equals(personaRadica, that.personaRadica) &&
            Objects.equals(funcionario, that.funcionario) &&
            Objects.equals(radicaPersonaId, that.radicaPersonaId) &&
            Objects.equals(funcionarioPersonaId, that.funcionarioPersonaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        numeroTramite,
        anno,
        nombreTramite,
        descripcion,
        personaRadica,
        funcionario,
        radicaPersonaId,
        funcionarioPersonaId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TramitesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numeroTramite != null ? "numeroTramite=" + numeroTramite + ", " : "") +
                (anno != null ? "anno=" + anno + ", " : "") +
                (nombreTramite != null ? "nombreTramite=" + nombreTramite + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (personaRadica != null ? "personaRadica=" + personaRadica + ", " : "") +
                (funcionario != null ? "funcionario=" + funcionario + ", " : "") +
                (radicaPersonaId != null ? "radicaPersonaId=" + radicaPersonaId + ", " : "") +
                (funcionarioPersonaId != null ? "funcionarioPersonaId=" + funcionarioPersonaId + ", " : "") +
            "}";
    }

}
