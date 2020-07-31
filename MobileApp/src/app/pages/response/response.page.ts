import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/user.service';
import { WebrequestService } from 'src/app/api/webrequest.service';
import Notiflix from "notiflix";

@Component({
  selector: 'response',
  templateUrl: './response.page.html',
  styleUrls: ['./response.page.scss'],
})
export class ResponsePage implements OnInit {
  Message: string = ''
  constructor(private user: UserService, private webReq: WebrequestService) { }
  Response = []
  ngOnInit() {
    this.Response = JSON.parse(JSON.stringify(this.user.getResponse()))
    this.Response.reverse()
   this.Response.forEach(res=>{
     let arr = (new Date(res.responseTime).toString().split(' '))
     arr = arr.slice(0,5)
     res.responseTime = arr.join(' ')
   })
  }

  SendMessage() {
    if (this.Message != "") {
      let Chat_Response = {
        "responseFrom": this.Response[0].responseTo,
        "responseTo": this.Response[0].responseFrom,
        "complaintId": this.Response[0].complaintId,
        "response": this.Message
      }
      this.webReq.post('responses/response', Chat_Response).toPromise().then((res: any) => {
        if (res.status == 1) {
          
          this.Response.push(Chat_Response)
          this.Message=''
          Notiflix.Notify.Success('Message Sent Successfully')
        } 
      })
    }
    else{
      Notiflix.Notify.Warning('Empty Message Not allowed')
    }
  }

  getUniversityName() {
    return localStorage.getItem('UniversityName')
  }

  getInstitueName(){
    return localStorage.getItem('InstituteName')

  }

}
