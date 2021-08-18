import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'tipo-persona',
        loadChildren: () => import('./tipo-persona/tipo-persona.module').then(m => m.TramitesAppTipoPersonaModule),
      },
      {
        path: 'tipo-documento-identificacion',
        loadChildren: () =>
          import('./tipo-documento-identificacion/tipo-documento-identificacion.module').then(
            m => m.TramitesAppTipoDocumentoIdentificacionModule
          ),
      },
      {
        path: 'persona',
        loadChildren: () => import('./persona/persona.module').then(m => m.TramitesAppPersonaModule),
      },
      {
        path: 'tramites',
        loadChildren: () => import('./tramites/tramites.module').then(m => m.TramitesAppTramitesModule),
      },
      {
        path: 'nivel-satisfacion',
        loadChildren: () => import('./nivel-satisfacion/nivel-satisfacion.module').then(m => m.TramitesAppNivelSatisfacionModule),
      },
      {
        path: 'encuesta',
        loadChildren: () => import('./encuesta/encuesta.module').then(m => m.TramitesAppEncuestaModule),
      },
      {
        path: 'usuarios',
        loadChildren: () => import('./usuarios/usuarios.module').then(m => m.TramitesAppUsuariosModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class TramitesAppEntityModule {}
