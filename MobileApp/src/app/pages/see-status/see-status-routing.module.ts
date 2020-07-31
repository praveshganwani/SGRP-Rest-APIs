import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SeeStatusPage } from './see-status.page';

const routes: Routes = [
  {
    path: '',
    component: SeeStatusPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SeeStatusPageRoutingModule {}
