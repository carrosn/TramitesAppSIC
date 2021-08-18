import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';

@Component({
  selector: 'jhi-tipo-documento-identificacion-detail',
  templateUrl: './tipo-documento-identificacion-detail.component.html',
})
export class TipoDocumentoIdentificacionDetailComponent implements OnInit {
  tipoDocumentoIdentificacion: ITipoDocumentoIdentificacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(
      ({ tipoDocumentoIdentificacion }) => (this.tipoDocumentoIdentificacion = tipoDocumentoIdentificacion)
    );
  }

  previousState(): void {
    window.history.back();
  }
}
