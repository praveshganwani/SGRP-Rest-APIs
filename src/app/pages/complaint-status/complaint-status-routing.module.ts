import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ComplaintStatusPage } from './complaint-status.page';

const routes: Routes = [
  {
    path: '',
    component: ComplaintStatusPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ComplaintStatusPageRoutingModule {}
