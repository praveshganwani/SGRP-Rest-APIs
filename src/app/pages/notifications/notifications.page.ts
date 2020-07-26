import { Component, OnInit } from '@angular/core';
import { WebrequestService } from 'src/app/api/webrequest.service';
import { Plugins } from '@capacitor/core';
import { Student } from 'src/app/Student';
import { Route } from '@angular/compiler/src/core';
import { Router } from '@angular/router';
import { UserService } from 'src/app/user.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.page.html',
  styleUrls: ['./notifications.page.scss'],
})
export class NotificationsPage implements OnInit {

  griveances = []
  Responses = []
  Student: Student = {}
  constructor(private webReq: WebrequestService,private router:Router,private user:UserService) { }
  async ngOnInit() {
    const { Storage } = Plugins;
    await Storage.get({ key: 'student' }).then(res => {
      if (res) {
        this.Student = JSON.parse(res.value)
      }
    }).then( async () => {
      await this.GetComplaints();    
    })
  }

  async GetComplaints() {
    this.webReq.Get('grievances').subscribe((data: Array<any>) => {
      this.griveances = data;
      this.griveances = data.filter(ele => ele.complaintStudentId == this.Student.studentId);
      this.griveances.forEach(g=>{
        this.webReq.Get('responses/response/'+g.complaintId).subscribe((res:Array<any>)=>{
            if(res.length != 0){
              console.log(res)
              res[0].complaintTitle = g.complaintTitle;
              this.Responses.push(res)
            }
            
        })
      })
    })
  }

  GoToResponse(res){
    console.log(this.Responses)
    this.user.setReponse(res)
    this.router.navigateByUrl('/menu/notifications/response')
  }

}
