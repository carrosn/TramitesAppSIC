import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITramites, Tramites } from 'app/shared/model/tramites.model';
import { TramitesService } from './tramites.service';
import { TramitesComponent } from './tramites.component';
import { TramitesDetailComponent } from './tramites-detail.component';
import { TramitesUpdateComponent } from './tramites-update.component';

@Injectable({ providedIn: 'root' })
export class TramitesResolve implements Resolve<ITramites> {
  constructor(private service: TramitesService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITramites> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((tramites: HttpResponse<Tramites>) => {
          if (tramites.body) {
            return of(tramites.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Tramites());
  }
}

export const tramitesRoute: Routes = [
  {
    path: '',
    component: TramitesComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.tramites.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TramitesDetailComponent,
    resolve: {
      tramites: TramitesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.tramites.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TramitesUpdateComponent,
    resolve: {
      tramites: TramitesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.tramites.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TramitesUpdateComponent,
    resolve: {
      tramites: TramitesResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.tramites.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
