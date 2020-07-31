import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { LodgeComplaintPage } from './lodge-complaint.page';

describe('LodgeComplaintPage', () => {
  let component: LodgeComplaintPage;
  let fixture: ComponentFixture<LodgeComplaintPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LodgeComplaintPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(LodgeComplaintPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
