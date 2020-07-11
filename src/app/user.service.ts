import { Injectable } from '@angular/core';
import { Student } from './Student';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  student:Student 
  constructor() { }
  setStudent(s:Student){
    this.student =s
  }

  getStudent(){
    if(this.student == undefined){
      this.setStudent(JSON.parse(localStorage.getItem('student')))
    }
    return this.student
  }
}
