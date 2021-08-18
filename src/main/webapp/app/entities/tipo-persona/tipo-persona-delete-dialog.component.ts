import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipoPersona } from 'app/shared/model/tipo-persona.model';
import { TipoPersonaService } from './tipo-persona.service';

@Component({
  templateUrl: './tipo-persona-delete-dialog.component.html',
})
export class TipoPersonaDeleteDialogComponent {
  tipoPersona?: ITipoPersona;

  constructor(
    protected tipoPersonaService: TipoPersonaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tipoPersonaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tipoPersonaListModification');
      this.activeModal.close();
    });
  }
}
