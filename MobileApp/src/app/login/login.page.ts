import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { Platform, AlertController } from '@ionic/angular';
import { WebrequestService } from '../api/webrequest.service';
import { UserService } from '../user.service';
import { AndroidPermissions } from '@ionic-native/android-permissions/ngx/';

import { Plugins } from '@capacitor/core';
import Notiflix from "notiflix";



@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(private router: Router, public zone: NgZone, private alert: AlertController, public platform: Platform, private web: WebrequestService,
     private user: UserService, private androidPermissions: AndroidPermissions) {

  }
  Email:string
  Password:string
  plt
  data
  showPassword = false;
  
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
      Notiflix.Loading.Standard();
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
            Notiflix.Loading.Remove();
            Notiflix.Notify.Success('Login Successful')
            this.user.setStudent(res.studentDetails)
            this.Email = ''
            this.Password = ''
           
            this.router.navigate(['/menu'],{replaceUrl:true})
          }
          else if (res.status == -1) {
            Notiflix.Loading.Remove();
            this.showAlert('Error', 'Wrong Password')
          }
          else if (res.status == -2) {
            Notiflix.Loading.Remove();
            this.showAlert('Error', 'User not verified.Wait for verification')
          }
          else if (res.status == -3) {
            Notiflix.Loading.Remove();
            this.showAlert('Error', 'Student not registered')
          }
          else if(res.status == -100){
            Notiflix.Loading.Remove();
            this.showAlert('Error', 'You have been blocked.')
          }
        })
      })
    }
    catch (err) {
      Notiflix.Loading.Standard();
      this.showAlert('Error', err)
    }
   
   


  }


  
  TogglePassword(){
    this.showPassword = !this.showPassword
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
