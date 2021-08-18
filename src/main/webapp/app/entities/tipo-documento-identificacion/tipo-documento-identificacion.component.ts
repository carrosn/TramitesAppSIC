import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITipoDocumentoIdentificacion } from 'app/shared/model/tipo-documento-identificacion.model';
import { TipoDocumentoIdentificacionService } from './tipo-documento-identificacion.service';
import { TipoDocumentoIdentificacionDeleteDialogComponent } from './tipo-documento-identificacion-delete-dialog.component';

@Component({
  selector: 'jhi-tipo-documento-identificacion',
  templateUrl: './tipo-documento-identificacion.component.html',
})
export class TipoDocumentoIdentificacionComponent implements OnInit, OnDestroy {
  tipoDocumentoIdentificacions?: ITipoDocumentoIdentificacion[];
  eventSubscriber?: Subscription;

  constructor(
    protected tipoDocumentoIdentificacionService: TipoDocumentoIdentificacionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.tipoDocumentoIdentificacionService
      .query()
      .subscribe((res: HttpResponse<ITipoDocumentoIdentificacion[]>) => (this.tipoDocumentoIdentificacions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTipoDocumentoIdentificacions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITipoDocumentoIdentificacion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTipoDocumentoIdentificacions(): void {
    this.eventSubscriber = this.eventManager.subscribe('tipoDocumentoIdentificacionListModification', () => this.loadAll());
  }

  delete(tipoDocumentoIdentificacion: ITipoDocumentoIdentificacion): void {
    const modalRef = this.modalService.open(TipoDocumentoIdentificacionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tipoDocumentoIdentificacion = tipoDocumentoIdentificacion;
  }
}
