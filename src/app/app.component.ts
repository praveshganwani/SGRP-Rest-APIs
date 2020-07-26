import { Component } from '@angular/core';

import { Platform } from '@ionic/angular';
import { SplashScreen } from '@ionic-native/splash-screen/ngx';
import { StatusBar } from '@ionic-native/status-bar/ngx';
import { Plugins } from '@capacitor/core';
import { Router } from '@angular/router';

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

  ) {


    this.initializeApp();
  }

  Student = undefined

  initializeApp() {
    this.platform.ready().then(() => {
      this.statusBar.styleDefault();
      this.splashScreen.hide();
      const { Storage } = Plugins;
      Storage.get({ key: 'student' }).then(res => {
        if (res) {
          if (res.value) {
            this.Student = res.value
            this.router.navigate(['/menu'])
          }
        }
        else {
          this.router.navigate(['/'])
        }
      });
    })
  }


  onToggleTheme(event) {
    console.log(event.detail.checked)
    if (event.detail.checked) {
      document.body.setAttribute('color-theme', 'dark')
    }
    else {
      document.body.setAttribute('color-theme', 'light')

    }
  }

  
}
