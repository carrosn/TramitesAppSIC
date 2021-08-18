import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IUsuarios } from 'app/shared/model/usuarios.model';
import { UsuariosService } from './usuarios.service';
import { UsuariosDeleteDialogComponent } from './usuarios-delete-dialog.component';

@Component({
  selector: 'jhi-usuarios',
  templateUrl: './usuarios.component.html',
})
export class UsuariosComponent implements OnInit, OnDestroy {
  usuarios?: IUsuarios[];
  eventSubscriber?: Subscription;

  constructor(protected usuariosService: UsuariosService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.usuariosService.query().subscribe((res: HttpResponse<IUsuarios[]>) => (this.usuarios = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInUsuarios();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IUsuarios): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInUsuarios(): void {
    this.eventSubscriber = this.eventManager.subscribe('usuariosListModification', () => this.loadAll());
  }

  delete(usuarios: IUsuarios): void {
    const modalRef = this.modalService.open(UsuariosDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.usuarios = usuarios;
  }
}
