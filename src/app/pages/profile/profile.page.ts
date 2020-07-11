import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user.service';
import { Student } from 'src/app/Student';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.page.html',
  styleUrls: ['./profile.page.scss'],
})
export class ProfilePage implements OnInit {

  constructor(private user:UserService) { }
  student:Student
  ngOnInit() {
    this.student=this.user.getStudent()
  }

}
