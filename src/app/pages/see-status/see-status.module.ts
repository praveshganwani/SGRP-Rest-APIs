import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SeeStatusPageRoutingModule } from './see-status-routing.module';

import { SeeStatusPage } from './see-status.page';
import { DatetimelineDirective } from '../../datetimeline.directive';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SeeStatusPageRoutingModule,
  ],
 
  declarations: [SeeStatusPage,DatetimelineDirective]
})
export class SeeStatusPageModule {}
