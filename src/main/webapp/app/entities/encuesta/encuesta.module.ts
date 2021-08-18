import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TramitesAppSharedModule } from 'app/shared/shared.module';
import { EncuestaComponent } from './encuesta.component';
import { EncuestaDetailComponent } from './encuesta-detail.component';
import { EncuestaUpdateComponent } from './encuesta-update.component';
import { EncuestaDeleteDialogComponent } from './encuesta-delete-dialog.component';
import { encuestaRoute } from './encuesta.route';

@NgModule({
  imports: [TramitesAppSharedModule, RouterModule.forChild(encuestaRoute)],
  declarations: [EncuestaComponent, EncuestaDetailComponent, EncuestaUpdateComponent, EncuestaDeleteDialogComponent],
  entryComponents: [EncuestaDeleteDialogComponent],
})
export class TramitesAppEncuestaModule {}
