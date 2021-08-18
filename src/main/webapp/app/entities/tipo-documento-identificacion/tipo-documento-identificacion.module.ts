import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TramitesAppSharedModule } from 'app/shared/shared.module';
import { TipoDocumentoIdentificacionComponent } from './tipo-documento-identificacion.component';
import { TipoDocumentoIdentificacionDetailComponent } from './tipo-documento-identificacion-detail.component';
import { TipoDocumentoIdentificacionUpdateComponent } from './tipo-documento-identificacion-update.component';
import { TipoDocumentoIdentificacionDeleteDialogComponent } from './tipo-documento-identificacion-delete-dialog.component';
import { tipoDocumentoIdentificacionRoute } from './tipo-documento-identificacion.route';

@NgModule({
  imports: [TramitesAppSharedModule, RouterModule.forChild(tipoDocumentoIdentificacionRoute)],
  declarations: [
    TipoDocumentoIdentificacionComponent,
    TipoDocumentoIdentificacionDetailComponent,
    TipoDocumentoIdentificacionUpdateComponent,
    TipoDocumentoIdentificacionDeleteDialogComponent,
  ],
  entryComponents: [TipoDocumentoIdentificacionDeleteDialogComponent],
})
export class TramitesAppTipoDocumentoIdentificacionModule {}
