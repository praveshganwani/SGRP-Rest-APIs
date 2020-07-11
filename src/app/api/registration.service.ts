import { Injectable } from '@angular/core';
import { WebrequestService } from './webrequest.service';
import { Student } from '../Student';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private web:WebrequestService) { }

  GetCommittee(){
    return this.web.Get('committees')
  }

  GetCourses(){
    return this.web.Get('courses')
  }

  RegisterStudent(student:Student){
    return this.web.post('students/student',student)
  }
}
