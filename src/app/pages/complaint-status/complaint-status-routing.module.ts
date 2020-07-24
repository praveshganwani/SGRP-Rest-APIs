import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ComplaintStatusPage } from './complaint-status.page';

const routes: Routes = [
  {
    path: '',
    component: ComplaintStatusPage
  },
  {
    path: 'see-status/:complaintId',
    loadChildren: () => import('../see-status/see-status.module').then( m => m.SeeStatusPageModule)
  },
  {
    path: 'feedback/:complaintId',
    loadChildren: () => import('../feedback/feedback.module').then( m => m.FeedbackPageModule)
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ComplaintStatusPageRoutingModule {}
