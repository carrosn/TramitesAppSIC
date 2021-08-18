import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { TramitesAppSharedModule } from 'app/shared/shared.module';
import { UsuariosComponent } from './usuarios.component';
import { UsuariosDetailComponent } from './usuarios-detail.component';
import { UsuariosUpdateComponent } from './usuarios-update.component';
import { UsuariosDeleteDialogComponent } from './usuarios-delete-dialog.component';
import { usuariosRoute } from './usuarios.route';

@NgModule({
  imports: [TramitesAppSharedModule, RouterModule.forChild(usuariosRoute)],
  declarations: [UsuariosComponent, UsuariosDetailComponent, UsuariosUpdateComponent, UsuariosDeleteDialogComponent],
  entryComponents: [UsuariosDeleteDialogComponent],
})
export class TramitesAppUsuariosModule {}
