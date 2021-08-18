import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';

@Component({
  selector: 'jhi-nivel-satisfacion-detail',
  templateUrl: './nivel-satisfacion-detail.component.html',
})
export class NivelSatisfacionDetailComponent implements OnInit {
  nivelSatisfacion: INivelSatisfacion | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nivelSatisfacion }) => (this.nivelSatisfacion = nivelSatisfacion));
  }

  previousState(): void {
    window.history.back();
  }
}
