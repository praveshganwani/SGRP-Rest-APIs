import { Injectable } from '@angular/core';
import { Student } from './Student';
import { Plugins } from '@capacitor/core';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  student:Student 
  Response = []
  constructor() { }

  setReponse(res){
    this.Response = res
  }
  setStudent(s:Student){
    this.student =s
  }

  getResponse(){
    return this.Response
  }

  async getStudent(){
    if(this.student == undefined){
      const { Storage } = Plugins;
      let res = await Storage.get({ key: 'student' });
     this.setStudent(JSON.parse(res.value))
    }
    return this.student
  }
}
