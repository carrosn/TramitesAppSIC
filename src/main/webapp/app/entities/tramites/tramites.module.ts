import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TramitesAppSharedModule } from 'app/shared/shared.module';
import { TramitesComponent } from './tramites.component';
import { TramitesDetailComponent } from './tramites-detail.component';
import { TramitesUpdateComponent } from './tramites-update.component';
import { TramitesDeleteDialogComponent } from './tramites-delete-dialog.component';
import { tramitesRoute } from './tramites.route';

@NgModule({
  imports: [TramitesAppSharedModule, RouterModule.forChild(tramitesRoute)],
  declarations: [TramitesComponent, TramitesDetailComponent, TramitesUpdateComponent, TramitesDeleteDialogComponent],
  entryComponents: [TramitesDeleteDialogComponent],
})
export class TramitesAppTramitesModule {}
