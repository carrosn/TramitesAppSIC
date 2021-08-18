import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TramitesAppSharedModule } from 'app/shared/shared.module';
import { TipoPersonaComponent } from './tipo-persona.component';
import { TipoPersonaDetailComponent } from './tipo-persona-detail.component';
import { TipoPersonaUpdateComponent } from './tipo-persona-update.component';
import { TipoPersonaDeleteDialogComponent } from './tipo-persona-delete-dialog.component';
import { tipoPersonaRoute } from './tipo-persona.route';

@NgModule({
  imports: [TramitesAppSharedModule, RouterModule.forChild(tipoPersonaRoute)],
  declarations: [TipoPersonaComponent, TipoPersonaDetailComponent, TipoPersonaUpdateComponent, TipoPersonaDeleteDialogComponent],
  entryComponents: [TipoPersonaDeleteDialogComponent],
})
export class TramitesAppTipoPersonaModule {}
