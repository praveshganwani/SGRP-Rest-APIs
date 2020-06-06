import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(private router:Router,public zone:NgZone) { }
  Email
  Password
  ngOnInit() {
  }
  Login(){
    this.zone.run(()=>{
      this.router.navigate(['/menu'])
    })
    
  }
}
