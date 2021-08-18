import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITramites } from 'app/shared/model/tramites.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { TramitesService } from './tramites.service';
import { TramitesDeleteDialogComponent } from './tramites-delete-dialog.component';

@Component({
  selector: 'jhi-tramites',
  templateUrl: './tramites.component.html',
})
export class TramitesComponent implements OnInit, OnDestroy {
  tramites: ITramites[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected tramitesService: TramitesService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.tramites = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.tramitesService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ITramites[]>) => this.paginateTramites(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.tramites = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTramites();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITramites): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTramites(): void {
    this.eventSubscriber = this.eventManager.subscribe('tramitesListModification', () => this.reset());
  }

  delete(tramites: ITramites): void {
    const modalRef = this.modalService.open(TramitesDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.tramites = tramites;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateTramites(data: ITramites[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.tramites.push(data[i]);
      }
    }
  }
}
