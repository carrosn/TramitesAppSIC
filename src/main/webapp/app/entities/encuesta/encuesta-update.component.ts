import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEncuesta, Encuesta } from 'app/shared/model/encuesta.model';
import { EncuestaService } from './encuesta.service';
import { INivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';
import { NivelSatisfacionService } from 'app/entities/nivel-satisfacion/nivel-satisfacion.service';

@Component({
  selector: 'jhi-encuesta-update',
  templateUrl: './encuesta-update.component.html',
})
export class EncuestaUpdateComponent implements OnInit {
  isSaving = false;
  nivelsatisfacions: INivelSatisfacion[] = [];

  editForm = this.fb.group({
    id: [],
    preguntaOne: [null, [Validators.required]],
    niveSatisfacion: [null, [Validators.required]],
    nivelSatisfacion: [null, Validators.required],
  });

  constructor(
    protected encuestaService: EncuestaService,
    protected nivelSatisfacionService: NivelSatisfacionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ encuesta }) => {
      this.updateForm(encuesta);

      this.nivelSatisfacionService.query().subscribe((res: HttpResponse<INivelSatisfacion[]>) => (this.nivelsatisfacions = res.body || []));
    });
  }

  updateForm(encuesta: IEncuesta): void {
    this.editForm.patchValue({
      id: encuesta.id,
      preguntaOne: encuesta.preguntaOne,
      niveSatisfacion: encuesta.niveSatisfacion,
      nivelSatisfacion: encuesta.nivelSatisfacion,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const encuesta = this.createFromForm();
    if (encuesta.id !== undefined) {
      this.subscribeToSaveResponse(this.encuestaService.update(encuesta));
    } else {
      this.subscribeToSaveResponse(this.encuestaService.create(encuesta));
    }
  }

  private createFromForm(): IEncuesta {
    return {
      ...new Encuesta(),
      id: this.editForm.get(['id'])!.value,
      preguntaOne: this.editForm.get(['preguntaOne'])!.value,
      niveSatisfacion: this.editForm.get(['niveSatisfacion'])!.value,
      nivelSatisfacion: this.editForm.get(['nivelSatisfacion'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEncuesta>>): void {
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

  trackById(index: number, item: INivelSatisfacion): any {
    return item.id;
  }
}
