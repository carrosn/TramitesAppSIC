import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { Authority } from 'app/shared/constants/authority.constants';
import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEncuesta, Encuesta } from 'app/shared/model/encuesta.model';
import { EncuestaService } from './encuesta.service';
import { EncuestaComponent } from './encuesta.component';
import { EncuestaDetailComponent } from './encuesta-detail.component';
import { EncuestaUpdateComponent } from './encuesta-update.component';

@Injectable({ providedIn: 'root' })
export class EncuestaResolve implements Resolve<IEncuesta> {
  constructor(private service: EncuestaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEncuesta> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((encuesta: HttpResponse<Encuesta>) => {
          if (encuesta.body) {
            return of(encuesta.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Encuesta());
  }
}

export const encuestaRoute: Routes = [
  {
    path: '',
    component: EncuestaComponent,
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.encuesta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: EncuestaDetailComponent,
    resolve: {
      encuesta: EncuestaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.encuesta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: EncuestaUpdateComponent,
    resolve: {
      encuesta: EncuestaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.encuesta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: EncuestaUpdateComponent,
    resolve: {
      encuesta: EncuestaResolve,
    },
    data: {
      authorities: [Authority.USER],
      pageTitle: 'tramitesApp.encuesta.home.title',
    },
    canActivate: [UserRouteAccessService],
  },
];
