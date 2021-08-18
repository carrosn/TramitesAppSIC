import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { INivelSatisfacion, NivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';
import { NivelSatisfacionService } from './nivel-satisfacion.service';

@Component({
  selector: 'jhi-nivel-satisfacion-update',
  templateUrl: './nivel-satisfacion-update.component.html',
})
export class NivelSatisfacionUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    codigo: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
  });

  constructor(
    protected nivelSatisfacionService: NivelSatisfacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nivelSatisfacion }) => {
      this.updateForm(nivelSatisfacion);
    });
  }

  updateForm(nivelSatisfacion: INivelSatisfacion): void {
    this.editForm.patchValue({
      id: nivelSatisfacion.id,
      codigo: nivelSatisfacion.codigo,
      descripcion: nivelSatisfacion.descripcion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nivelSatisfacion = this.createFromForm();
    if (nivelSatisfacion.id !== undefined) {
      this.subscribeToSaveResponse(this.nivelSatisfacionService.update(nivelSatisfacion));
    } else {
      this.subscribeToSaveResponse(this.nivelSatisfacionService.create(nivelSatisfacion));
    }
  }

  private createFromForm(): INivelSatisfacion {
    return {
      ...new NivelSatisfacion(),
      id: this.editForm.get(['id'])!.value,
      codigo: this.editForm.get(['codigo'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INivelSatisfacion>>): void {
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
