import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { Platform, AlertController } from '@ionic/angular';
import { WebrequestService } from '../api/webrequest.service';
import { UserService } from '../user.service';
import { AndroidPermissions } from '@ionic-native/android-permissions/ngx/';

import { Plugins } from '@capacitor/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(private router: Router, public zone: NgZone, private alert: AlertController, public platform: Platform, private web: WebrequestService, private user: UserService, private androidPermissions: AndroidPermissions,
  ) {

  }
  Email:string
  Password:string
  plt
  data
  passwordType: string = 'password';
  passwordIcon: string = 'eye-off';
  ngOnInit() {
    this.plt = this.platform.platforms();
    if (this.plt.includes('android')) {
      this.androidPermissions.checkPermission(this.androidPermissions.PERMISSION.INTERNET).then(
        result => console.log('Has permission?', String(result.hasPermission)),
        err => this.androidPermissions.requestPermission(this.androidPermissions.PERMISSION.INTERNET)
      );
    }

  }
  Login() {
    try {
      const { Storage } = Plugins;

      this.zone.run(() => {

        let userEmail = this.Email
        let userPassword = this.Password
        let user = { userEmail, userPassword }
        this.web.post('login/student', user).subscribe(async (res: any) => {
          if (res.status == 1) {
            await Storage.set({
              key: 'student',
              value: JSON.stringify(res.studentDetails)
            });
            this.showAlert('Success', 'Login Successful')
            this.user.setStudent(res.studentDetails)
            this.router.navigate(['/menu'])
          }

          else if (res.status == -1) {
            this.showAlert('Error', 'Wrong Password')
          }
          else if (res.status == -2) {
            this.showAlert('Error', 'User not verified.Wait for verification')
          }
          else if (res.status == -3) {
            this.showAlert('Error', 'Student not registered')
          }
        })
      })
    }
    catch (err) {
      this.showAlert('Error', err)
    }

  }


  hideShowPassword() {
    this.passwordType = this.passwordType === 'text' ? 'password' : 'text';
    this.passwordIcon = this.passwordIcon === 'eye-off' ? 'eye' : 'eye-off';
  }
  async showAlert(header: string, message: string) {
    const alert = await this.alert.create({
      header,
      message,
      buttons: ["OK"]
    })

    await alert.present()
  }
}
