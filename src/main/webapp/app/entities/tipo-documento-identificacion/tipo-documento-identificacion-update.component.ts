import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITipoDocumentoIdentificacion, TipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';
import { TipoDocumentoIdentificacionService } from './tipo-documento-identificacion.service';

@Component({
  selector: 'jhi-tipo-documento-identificacion-update',
  templateUrl: './tipo-documento-identificacion-update.component.html',
})
export class TipoDocumentoIdentificacionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    abreviatura: [null, [Validators.required]],
  });

  constructor(
    protected tipoDocumentoIdentificacionService: TipoDocumentoIdentificacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoDocumentoIdentificacion }) => {
      this.updateForm(tipoDocumentoIdentificacion);
    });
  }

  updateForm(tipoDocumentoIdentificacion: ITipoDocumentoIdentificacion): void {
    this.editForm.patchValue({
      id: tipoDocumentoIdentificacion.id,
      nombre: tipoDocumentoIdentificacion.nombre,
      abreviatura: tipoDocumentoIdentificacion.abreviatura,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tipoDocumentoIdentificacion = this.createFromForm();
    if (tipoDocumentoIdentificacion.id !== undefined) {
      this.subscribeToSaveResponse(this.tipoDocumentoIdentificacionService.update(tipoDocumentoIdentificacion));
    } else {
      this.subscribeToSaveResponse(this.tipoDocumentoIdentificacionService.create(tipoDocumentoIdentificacion));
    }
  }

  private createFromForm(): ITipoDocumentoIdentificacion {
    return {
      ...new TipoDocumentoIdentificacion(),
      id: this.editForm.get(['id'])!.value,
      nombre: this.editForm.get(['nombre'])!.value,
      abreviatura: this.editForm.get(['abreviatura'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipoDocumentoIdentificacion>>): void {
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
