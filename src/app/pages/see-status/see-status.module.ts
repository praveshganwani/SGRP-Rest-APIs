import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SeeStatusPageRoutingModule } from './see-status-routing.module';

import { SeeStatusPage } from './see-status.page';
import { NO_ERRORS_SCHEMA } from '@angular/compiler';
import { NgVerticalTimelineComponent } from 'ng-vertical-timeline';
import { VerticalTimelineModule } from 'angular-vertical-timeline';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SeeStatusPageRoutingModule,
    VerticalTimelineModule
  ],
 
  declarations: [SeeStatusPage]
})
export class SeeStatusPageModule {}
