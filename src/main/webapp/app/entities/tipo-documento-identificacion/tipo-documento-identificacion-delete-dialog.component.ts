import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';
import { TipoDocumentoIdentificacionService } from './tipo-documento-identificacion.service';

@Component({
  templateUrl: './tipo-documento-identificacion-delete-dialog.component.html',
})
export class TipoDocumentoIdentificacionDeleteDialogComponent {
  tipoDocumentoIdentificacion?: ITipoDocumentoIdentificacion;

  constructor(
    protected tipoDocumentoIdentificacionService: TipoDocumentoIdentificacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoDocumentoIdentificacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoDocumentoIdentificacionListModification');
      this.activeModal.close();
    });
  }
}
