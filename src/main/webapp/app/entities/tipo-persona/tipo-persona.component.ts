import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoPersona } from 'app/shared/model/tipo-persona.model';
import { TipoPersonaService } from './tipo-persona.service';
import { TipoPersonaDeleteDialogComponent } from './tipo-persona-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-persona',
  templateUrl: './tipo-persona.component.html',
})
export class TipoPersonaComponent implements OnInit, OnDestroy {
  tipoPersonas?: ITipoPersona[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoPersonaService: TipoPersonaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoPersonaService.query().subscribe((res: HttpResponse<ITipoPersona[]>) => (this.tipoPersonas = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoPersonas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoPersona): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoPersonas(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoPersonaListModification', () => this.loadAll());
  }

  delete(tipoPersona: ITipoPersona): void {
    const modalRef = this.modalService.open(TipoPersonaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoPersona = tipoPersona;
  }
}
