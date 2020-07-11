import { Component, OnInit } from '@angular/core';
import { Student } from '../Student';
import { RegistrationService } from '../api/registration.service';
import { Router } from '@angular/router';

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
  constructor(private regiser: RegistrationService, private router: Router) { }

  ngOnInit() {
    this.regiser.GetCommittee().toPromise().then((data: Array<any>) => {
      this.Universities = data.filter(e => e.committeeType === 'univ')
      this.Committees = data
    })

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
      this.regiser.RegisterStudent(this.Student).subscribe((res: any) => {
        if (res.status == 1) {
          alert('Registered Successfully')
          this.router.navigateByUrl('/')
        }
        else {
          alert('Some error occured, make sure all details are filled in.')
        }
      })
    }
    catch (err) {
      alert('Something Went wrong, please make sure to fill all details correctly')
    }
  }

}
