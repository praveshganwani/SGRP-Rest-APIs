import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { LodgeComplaintPageRoutingModule } from './lodge-complaint-routing.module';

import { LodgeComplaintPage } from './lodge-complaint.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    LodgeComplaintPageRoutingModule
  ],
  declarations: [LodgeComplaintPage]
})
export class LodgeComplaintPageModule {}
