import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { INivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';
import { NivelSatisfacionService } from './nivel-satisfacion.service';
import { NivelSatisfacionDeleteDialogComponent } from './nivel-satisfacion-delete-dialog.component';

@Component({
  selector: 'jhi-nivel-satisfacion',
  templateUrl: './nivel-satisfacion.component.html',
})
export class NivelSatisfacionComponent implements OnInit, OnDestroy {
  nivelSatisfacions?: INivelSatisfacion[];
  eventSubscriber?: Subscription;

  constructor(
    protected nivelSatisfacionService: NivelSatisfacionService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.nivelSatisfacionService.query().subscribe((res: HttpResponse<INivelSatisfacion[]>) => (this.nivelSatisfacions = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInNivelSatisfacions();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: INivelSatisfacion): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInNivelSatisfacions(): void {
    this.eventSubscriber = this.eventManager.subscribe('nivelSatisfacionListModification', () => this.loadAll());
  }

  delete(nivelSatisfacion: INivelSatisfacion): void {
    const modalRef = this.modalService.open(NivelSatisfacionDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.nivelSatisfacion = nivelSatisfacion;
  }
}
