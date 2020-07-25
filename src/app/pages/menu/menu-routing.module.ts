import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { MenuPage } from './menu.page';

const routes: Routes = [
 
  {
    path: '',
    component: MenuPage,
    children:[
      
        {
          path: 'profile',
          loadChildren: () => import('../profile/profile.module').then( m => m.ProfilePageModule)
        },
        {
          path: 'faqs',
          loadChildren: () => import('../faqs/faqs.module').then( m => m.FaqsPageModule)
        },
        {
          path: 'lodge-complaint',
          loadChildren: () => import('../lodge-complaint/lodge-complaint.module').then( m => m.LodgeComplaintPageModule)
        },
        {
          path: 'complaint-status',
          loadChildren: () => import('../complaint-status/complaint-status.module').then( m => m.ComplaintStatusPageModule)
        },
        {
          path: 'notifications',
          loadChildren: () => import('../notifications/notifications.module').then( m => m.NotificationsPageModule)
        },
        {
          path: 'notices',
          loadChildren: () => import('../notices/notices.module').then( m => m.NoticesPageModule)
        },
      
    ]
  },
  // {
  //   path:'',
  //   redirectTo:'/menu/profile'
  // },
 
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class MenuPageRoutingModule {}
