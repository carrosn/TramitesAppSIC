import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoPersona, TipoPersona } from 'app/shared/model/tipo-persona.model';
import { TipoPersonaService } from './tipo-persona.service';

@Component({
  selector: 'jhi-tipo-persona-update',
  templateUrl: './tipo-persona-update.component.html',
})
export class TipoPersonaUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
  });

  constructor(protected tipoPersonaService: TipoPersonaService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoPersona }) => {
      this.updateForm(tipoPersona);
    });
  }

  updateForm(tipoPersona: ITipoPersona): void {
    this.editForm.patchValue({
      id: tipoPersona.id,
      codigo: tipoPersona.codigo,
      descripcion: tipoPersona.descripcion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoPersona = this.createFromForm();
    if (tipoPersona.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoPersonaService.update(tipoPersona));
    } else {
      this.subscribeToSaveResponse(this.tipoPersonaService.create(tipoPersona));
    }
  }

  private createFromForm(): ITipoPersona {
    return {
      ...new TipoPersona(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoPersona>>): void {
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
}
