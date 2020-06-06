import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { LodgeComplaintPage } from './lodge-complaint.page';

const routes: Routes = [
  {
    path: '',
    component: LodgeComplaintPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class LodgeComplaintPageRoutingModule {}
