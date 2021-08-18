import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITramites } from 'app/shared/model/tramites.model';
import { TramitesService } from './tramites.service';

@Component({
  templateUrl: './tramites-delete-dialog.component.html',
})
export class TramitesDeleteDialogComponent {
  tramites?: ITramites;

  constructor(protected tramitesService: TramitesService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.tramitesService.delete(id).subscribe(() => {
      this.eventManager.broadcast('tramitesListModification');
      this.activeModal.close();
    });
  }
}
