import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEncuesta } from 'app/shared/model/encuesta.model';
import { EncuestaService } from './encuesta.service';

@Component({
  templateUrl: './encuesta-delete-dialog.component.html',
})
export class EncuestaDeleteDialogComponent {
  encuesta?: IEncuesta;

  constructor(protected encuestaService: EncuestaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.encuestaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('encuestaListModification');
      this.activeModal.close();
    });
  }
}
