import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITramites, Tramites } from 'app/shared/model/tramites.model';
import { TramitesService } from './tramites.service';
import { IPersona } from 'app/shared/model/persona.model';
import { PersonaService } from 'app/entities/persona/persona.service';

@Component({
  selector: 'jhi-tramites-update',
  templateUrl: './tramites-update.component.html',
})
export class TramitesUpdateComponent implements OnInit {
  isSaving = false;
  personas: IPersona[] = [];

  editForm = this.fb.group({
    id: [],
    numeroTramite: [null, [Validators.required]],
    anno: [null, [Validators.required]],
    nombreTramite: [null, [Validators.required]],
    descripcion: [null, [Validators.required]],
    personaRadica: [null, [Validators.required]],
    funcionario: [null, [Validators.required]],
    radicaPersona: [],
    funcionarioPersona: [],
  });

  constructor(
    protected tramitesService: TramitesService,
    protected personaService: PersonaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tramites }) => {
      this.updateForm(tramites);

      this.personaService.query().subscribe((res: HttpResponse<IPersona[]>) => (this.personas = res.body || []));
    });
  }

  updateForm(tramites: ITramites): void {
    this.editForm.patchValue({
      id: tramites.id,
      numeroTramite: tramites.numeroTramite,
      anno: tramites.anno,
      nombreTramite: tramites.nombreTramite,
      descripcion: tramites.descripcion,
      personaRadica: tramites.personaRadica,
      funcionario: tramites.funcionario,
      radicaPersona: tramites.radicaPersona,
      funcionarioPersona: tramites.funcionarioPersona,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tramites = this.createFromForm();
    if (tramites.id !== undefined) {
      this.subscribeToSaveResponse(this.tramitesService.update(tramites));
    } else {
      this.subscribeToSaveResponse(this.tramitesService.create(tramites));
    }
  }

  private createFromForm(): ITramites {
    return {
      ...new Tramites(),
      id: this.editForm.get(['id'])!.value,
      numeroTramite: this.editForm.get(['numeroTramite'])!.value,
      anno: this.editForm.get(['anno'])!.value,
      nombreTramite: this.editForm.get(['nombreTramite'])!.value,
      descripcion: this.editForm.get(['descripcion'])!.value,
      personaRadica: this.editForm.get(['personaRadica'])!.value,
      funcionario: this.editForm.get(['funcionario'])!.value,
      radicaPersona: this.editForm.get(['radicaPersona'])!.value,
      funcionarioPersona: this.editForm.get(['funcionarioPersona'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITramites>>): void {
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

  trackById(index: number, item: IPersona): any {
    return item.id;
  }
}
