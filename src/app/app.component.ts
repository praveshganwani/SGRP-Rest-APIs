import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { Plugins } from '@capacitor/core';
import { Router } from '@angular/router';
import { RegistrationService } from './api/registration.service';

@Component({
  selector: 'app-root',
  templateUrl: 'app.component.html',
  styleUrls: ['app.component.scss']
})
export class AppComponent {
  constructor(
    private platform: Platform,
    private splashScreen: SplashScreen,
    private statusBar: StatusBar,
    private router: Router,
    private register: RegistrationService

  ) {


    this.initializeApp();
  }

  Student = undefined

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
      const { Storage } = Plugins;
      Storage.get({ key: 'student' }).then(async res => {
        if (res) {
          if (res.value) {

            this.Student = res.value
            let copy = res.value
            let sid = JSON.parse(copy).studentId
            let Isactive= await this.register.Isactive(sid)
           
            if (Isactive) {
              this.router.navigate(['/menu'])
            }
            else {
              Storage.clear()
              localStorage.clear()
              this.router.navigate(['/'])
            }
          }
        }
        else {
          this.router.navigate(['/'])
        }
      });
    })
  }




  getUniversity() {
    return localStorage.getItem('UniversityName') || ''
  }


}
