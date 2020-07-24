import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { SeeStatusPage } from './see-status.page';

describe('SeeStatusPage', () => {
  let component: SeeStatusPage;
  let fixture: ComponentFixture<SeeStatusPage>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SeeStatusPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(SeeStatusPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
