import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IPersona, Persona } from 'app/shared/model/persona.model';
import { PersonaService } from './persona.service';
import { ITipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';
import { TipoDocumentoIdentificacionService } from 'app/entities/tipo-documento-identificacion/tipo-documento-identificacion.service';
import { ITipoPersona } from 'app/shared/model/tipo-persona.model';
import { TipoPersonaService } from 'app/entities/tipo-persona/tipo-persona.service';

type SelectableEntity = ITipoDocumentoIdentificacion | ITipoPersona;

@Component({
  selector: 'jhi-persona-update',
  templateUrl: './persona-update.component.html',
})
export class PersonaUpdateComponent implements OnInit {
  isSaving = false;
  tipodocumentoidentificacions: ITipoDocumentoIdentificacion[] = [];
  tipopersonas: ITipoPersona[] = [];

  editForm = this.fb.group({
    id: [],
    numeroDocumentoIdentificacion: [null, [Validators.required]],
    nombres: [null, [Validators.required]],
    apellidos: [null, [Validators.required]],
    segundoApellido: [],
    telefono: [],
    direccion: [],
    email: [],
    tipoDocumentoIdentificacion: [null, Validators.required],
    tipoPersona: [null, Validators.required],
  });

  constructor(
    protected personaService: PersonaService,
    protected tipoDocumentoIdentificacionService: TipoDocumentoIdentificacionService,
    protected tipoPersonaService: TipoPersonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ persona }) => {
      this.updateForm(persona);

      this.tipoDocumentoIdentificacionService
        .query()
        .subscribe((res: HttpResponse<ITipoDocumentoIdentificacion[]>) => (this.tipodocumentoidentificacions = res.body || []));

      this.tipoPersonaService.query().subscribe((res: HttpResponse<ITipoPersona[]>) => (this.tipopersonas = res.body || []));
    });
  }

  updateForm(persona: IPersona): void {
    this.editForm.patchValue({
      id: persona.id,
      numeroDocumentoIdentificacion: persona.numeroDocumentoIdentificacion,
      nombres: persona.nombres,
      apellidos: persona.apellidos,
      segundoApellido: persona.segundoApellido,
      telefono: persona.telefono,
      direccion: persona.direccion,
      email: persona.email,
      tipoDocumentoIdentificacion: persona.tipoDocumentoIdentificacion,
      tipoPersona: persona.tipoPersona,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const persona = this.createFromForm();
    if (persona.id !== undefined) {
      this.subscribeToSaveResponse(this.personaService.update(persona));
    } else {
      this.subscribeToSaveResponse(this.personaService.create(persona));
    }
  }

  private createFromForm(): IPersona {
    return {
      ...new Persona(),
      id: this.editForm.get(['id'])!.value,
      numeroDocumentoIdentificacion: this.editForm.get(['numeroDocumentoIdentificacion'])!.value,
      nombres: this.editForm.get(['nombres'])!.value,
      apellidos: this.editForm.get(['apellidos'])!.value,
      segundoApellido: this.editForm.get(['segundoApellido'])!.value,
      telefono: this.editForm.get(['telefono'])!.value,
      direccion: this.editForm.get(['direccion'])!.value,
      email: this.editForm.get(['email'])!.value,
      tipoDocumentoIdentificacion: this.editForm.get(['tipoDocumentoIdentificacion'])!.value,
      tipoPersona: this.editForm.get(['tipoPersona'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPersona>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
