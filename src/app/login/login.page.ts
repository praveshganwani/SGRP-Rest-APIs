import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from '@angular/router';
import { Platform } from '@ionic/angular';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  constructor(private router:Router,public zone:NgZone,public platform: Platform) { }
  Email
  Password
  plt
  ngOnInit() {
    this.plt = this.platform.platforms();

  }
  Login(){
    this.zone.run(()=>{
      this.router.navigate(['/menu'])
    })
    
  }
}
