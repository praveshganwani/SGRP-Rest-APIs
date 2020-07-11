import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { Platform } from '@ionic/angular';
import { WebrequestService } from '../api/webrequest.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(private router:Router,public zone:NgZone,public platform: Platform,private web:WebrequestService,private user:UserService) { }
  Email
  Password
  plt
  ngOnInit() {
    this.plt = this.platform.platforms();

  }
  Login(){
    this.zone.run(()=>{
      let userEmail =this.Email
      let userPassword = this.Password
      let user = {userEmail,userPassword}
      this.web.post('login/student',user).subscribe((res:any)=>{
        console.log(res)
        if(res.status ==1){
          localStorage.setItem('student', JSON.stringify(res.studentDetails));
          this.user.setStudent(res.studentDetails )
          this.router.navigate(['/menu'])
        }

        else if(res.status == -1){
          alert('Wrong Password')
        }
        else if(res.status == -2){
          alert('User not verified.Wait for verification')
        }
        else if(res.status == -3){
          alert('Student not registered')
        }
      })
    })
  
  }
}
