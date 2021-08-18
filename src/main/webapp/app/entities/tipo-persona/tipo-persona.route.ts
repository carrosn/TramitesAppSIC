import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITipoPersona, TipoPersona } from 'app/shared/model/tipo-persona.model';
import { TipoPersonaService } from './tipo-persona.service';
import { TipoPersonaComponent } from './tipo-persona.component';
import { TipoPersonaDetailComponent } from './tipo-persona-detail.component';
import { TipoPersonaUpdateComponent } from './tipo-persona-update.component';

@Injectable({ providedIn: 'root' })
export class TipoPersonaResolve implements Resolve<ITipoPersona> {
  constructor(private service: TipoPersonaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITipoPersona> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tipoPersona: HttpResponse<TipoPersona>) => {
          if (tipoPersona.body) {
            return of(tipoPersona.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TipoPersona());
  }
}

export const tipoPersonaRoute: Routes = [
  {
    path: '',
    component: TipoPersonaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.tipoPersona.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TipoPersonaDetailComponent,
    resolve: {
      tipoPersona: TipoPersonaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.tipoPersona.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TipoPersonaUpdateComponent,
    resolve: {
      tipoPersona: TipoPersonaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.tipoPersona.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TipoPersonaUpdateComponent,
    resolve: {
      tipoPersona: TipoPersonaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.tipoPersona.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
