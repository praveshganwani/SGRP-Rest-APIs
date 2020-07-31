import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user.service';
import { Student } from 'src/app/Student';
import { Router } from '@angular/router';
import { Plugins } from '@capacitor/core';
import { RegistrationService } from 'src/app/api/registration.service';
import { NavController } from '@ionic/angular';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  constructor(private user: UserService, private router: Router, private register: RegistrationService,private navCtrl:NavController) { }
  student: Student = {}
  InstituteName = ''
  UniversityName = ''
  CourseName = ''
  ngOnInit() {
    this.navCtrl.navigateRoot("/menu/profile");
    this.user.getStudent().then(data => {

      if (this.student == undefined) {
        this.router.navigateByUrl('/')
      }
      else {
       
        this.student = data
        this.CourseName = localStorage.getItem('CourseName')
        this.InstituteName = localStorage.getItem('InstituteName')
        this.UniversityName = localStorage.getItem('UniversityName')
        if (this.CourseName == undefined) {
          this.register.GetCourses().subscribe((res: Array<any>) => {
            this.CourseName = res.find(e => e.courseId == this.student.courseId).courseName
            localStorage.setItem('CourseName', this.CourseName)
          })
        }
        if (this.InstituteName == undefined) {
          this.register.GetCommittee().subscribe((res: Array<any>) => {
            let inst = (res.find(e => e.committeeId == this.student.instituteId))
            this.InstituteName = inst.committeeName
            localStorage.setItem('InstituteName', this.InstituteName)
            this.UniversityName = (res.find(e => e.committeeId == inst.parentId)).committeeName
            localStorage.setItem('UniversityName', this.UniversityName)
          })
        }
      }
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
  Logout() {
    const { Storage } = Plugins;
    Storage.clear()
    localStorage.clear()
    
    this.router.navigate(['/'],{replaceUrl:true})
  
  }
}
