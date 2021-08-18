import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITramites } from 'app/shared/model/tramites.model';

@Component({
  selector: 'jhi-tramites-detail',
  templateUrl: './tramites-detail.component.html',
})
export class TramitesDetailComponent implements OnInit {
  tramites: ITramites | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tramites }) => (this.tramites = tramites));
  }

  previousState(): void {
    window.history.back();
  }
}
