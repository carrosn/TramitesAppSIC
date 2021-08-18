import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoPersona } from 'app/shared/model/tipo-persona.model';

@Component({
  selector: 'jhi-tipo-persona-detail',
  templateUrl: './tipo-persona-detail.component.html',
})
export class TipoPersonaDetailComponent implements OnInit {
  tipoPersona: ITipoPersona | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tipoPersona }) => (this.tipoPersona = tipoPersona));
  }

  previousState(): void {
    window.history.back();
  }
}
