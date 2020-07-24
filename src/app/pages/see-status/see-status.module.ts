import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SeeStatusPageRoutingModule } from './see-status-routing.module';

import { SeeStatusPage } from './see-status.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SeeStatusPageRoutingModule
  ],
  declarations: [SeeStatusPage]
})
export class SeeStatusPageModule {}
