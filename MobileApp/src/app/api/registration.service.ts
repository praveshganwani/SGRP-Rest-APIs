import { Injectable } from '@angular/core';
import { WebrequestService } from './webrequest.service';
import { Student } from '../Student';
import { resolve } from 'dns';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private web: WebrequestService) { }

  GetCommittee() {
    return this.web.Get('committees')
  }

  GetCourses() {
    return this.web.Get('courses')
  }

  RegisterStudent(student: Student) {
    return this.web.post('students/student', student)
  }

  async Isactive(studentId: string) {
    let isactive = 0
    await this.web.Get(`students/student/${studentId}`).toPromise().then((res: any) => {
      isactive = res.isActive
    })
    return isactive

  }
}
