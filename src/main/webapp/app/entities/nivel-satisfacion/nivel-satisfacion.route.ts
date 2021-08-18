import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { INivelSatisfacion, NivelSatisfacion } from 'app/shared/model/nivel-satisfacion.model';
import { NivelSatisfacionService } from './nivel-satisfacion.service';
import { NivelSatisfacionComponent } from './nivel-satisfacion.component';
import { NivelSatisfacionDetailComponent } from './nivel-satisfacion-detail.component';
import { NivelSatisfacionUpdateComponent } from './nivel-satisfacion-update.component';

@Injectable({ providedIn: 'root' })
export class NivelSatisfacionResolve implements Resolve<INivelSatisfacion> {
  constructor(private service: NivelSatisfacionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<INivelSatisfacion> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((nivelSatisfacion: HttpResponse<NivelSatisfacion>) => {
          if (nivelSatisfacion.body) {
            return of(nivelSatisfacion.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new NivelSatisfacion());
  }
}

export const nivelSatisfacionRoute: Routes = [
  {
    path: '',
    component: NivelSatisfacionComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.nivelSatisfacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NivelSatisfacionDetailComponent,
    resolve: {
      nivelSatisfacion: NivelSatisfacionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.nivelSatisfacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NivelSatisfacionUpdateComponent,
    resolve: {
      nivelSatisfacion: NivelSatisfacionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.nivelSatisfacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NivelSatisfacionUpdateComponent,
    resolve: {
      nivelSatisfacion: NivelSatisfacionResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.nivelSatisfacion.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
