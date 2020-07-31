import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ComplaintStatusPageRoutingModule } from './complaint-status-routing.module';

import { ComplaintStatusPage } from './complaint-status.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ComplaintStatusPageRoutingModule
  ],
  declarations: [ComplaintStatusPage]
})
export class ComplaintStatusPageModule {}
