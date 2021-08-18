import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TramitesAppSharedModule } from 'app/shared/shared.module';
import { NivelSatisfacionComponent } from './nivel-satisfacion.component';
import { NivelSatisfacionDetailComponent } from './nivel-satisfacion-detail.component';
import { NivelSatisfacionUpdateComponent } from './nivel-satisfacion-update.component';
import { NivelSatisfacionDeleteDialogComponent } from './nivel-satisfacion-delete-dialog.component';
import { nivelSatisfacionRoute } from './nivel-satisfacion.route';

@NgModule({
  imports: [TramitesAppSharedModule, RouterModule.forChild(nivelSatisfacionRoute)],
  declarations: [
    NivelSatisfacionComponent,
    NivelSatisfacionDetailComponent,
    NivelSatisfacionUpdateComponent,
    NivelSatisfacionDeleteDialogComponent,
  ],
  entryComponents: [NivelSatisfacionDeleteDialogComponent],
})
export class TramitesAppNivelSatisfacionModule {}
