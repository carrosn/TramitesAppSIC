import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { INivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';
import { NivelSatisfacionService } from './nivel-satisfacion.service';

@Component({
  templateUrl: './nivel-satisfacion-delete-dialog.component.html',
})
export class NivelSatisfacionDeleteDialogComponent {
  nivelSatisfacion?: INivelSatisfacion;

  constructor(
    protected nivelSatisfacionService: NivelSatisfacionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.nivelSatisfacionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('nivelSatisfacionListModification');
      this.activeModal.close();
    });
  }
}
