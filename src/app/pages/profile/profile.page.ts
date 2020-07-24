import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user.service';
import { Student } from 'src/app/Student';
import { Router } from '@angular/router';
import { Plugins } from '@capacitor/core';
import { RegistrationService } from 'src/app/api/registration.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  constructor(private user: UserService, private router: Router, private register: RegistrationService) { }
  student: Student = {}
  InstituteName = ''
  UniversityName = ''
  CourseName = ''
  ngOnInit() {
    this.user.getStudent().then(data => {

      if (this.student == undefined) {
        this.router.navigateByUrl('/')
      }
      else {
        this.student = data
        this.register.GetCourses().subscribe((res:Array<any>)=>{
          console.log(res)
          this.CourseName = res.find(e=>e.courseId == this.student.courseId).courseName
        })
        this.register.GetCommittee().subscribe((res: Array<any>) => {
          let inst = (res.find(e => e.committeeId == this.student.instituteId))
          this.InstituteName = inst.committeeName
          this.UniversityName =  (res.find(e=>e.committeeId == inst.parentId)).committeeName
        })
      }
    })
  }
  Logout() {
    this.router.navigateByUrl('/')
    const { Storage } = Plugins;
    Storage.clear()
  }
}
