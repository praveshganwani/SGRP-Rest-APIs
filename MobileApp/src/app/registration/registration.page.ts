import { Component, OnInit } from '@angular/core';
import { Student } from '../Student';
import { RegistrationService } from '../api/registration.service';
import { Router } from '@angular/router';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.page.html',
  styleUrls: ['./registration.page.scss'],
})
export class RegistrationPage implements OnInit {
  Student: Student = {}
  Committees = []
  Universities = []
  Institutes = []
  Courses = []
  ConfirmPassword = ''
  showPassword = false;
  
  constructor(private regiser: RegistrationService, private router: Router,private alert: AlertController) { }

  ngOnInit() {
    this.regiser.GetCommittee().toPromise().then((data: Array<any>) => {
      this.Universities = data.filter(e => e.committeeType === 'univ')
      this.Committees = data
    })

  }

  TogglePassword(){
    this.showPassword = !this.showPassword
  }

  GetInstitutes() {

    this.Institutes = this.Committees.filter(e => e.parentId == this.Student.University)
    console.log(this.Institutes)
  }

  GetCourses() {
    this.regiser.GetCourses().toPromise().then((data: Array<any>) => {
      this.Courses = data.filter(e => e.instituteId === this.Student.instituteId)
      console.log(this.Courses)
    })
  }

  RegisterStudent() {
    this.Student.isActive = 1
    this.Student.isVerified = 0
    try {
      if(this.ConfirmPassword != this.Student.studentPassword){
        throw new Error('Passwords do not match.')
      }
      this.regiser.RegisterStudent(this.Student).subscribe((res: any) => {
        if (res.status == 1) {
          this.showAlert('Success','Registered Successfully')
          this.router.navigateByUrl('/')
        }
        else {
          this.showAlert('Failed','Some error occured, make sure all details are filled in.')
        }
      })
    }
    catch (err) {
      this.showAlert('Failed',err)
    }
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
