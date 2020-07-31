import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { WebrequestService } from 'src/app/api/webrequest.service';
import { AlertController } from '@ionic/angular';
import { Location } from '@angular/common';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.page.html',
  styleUrls: ['./feedback.page.scss'],
})
export class FeedbackPage implements OnInit {

  constructor(private route: ActivatedRoute, private web: WebrequestService, private alert: AlertController, private router: Router, private location: Location) { }
  complaintId = ''
  feedback: string = ''
  Satisfied: string
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.complaintId = params["complaintId"];
      this.web.Get('grievances/grievance/' + this.complaintId).toPromise().then((data: any) => {
        if(data.feedback){
          this.feedback = data.feedbackComment
          this.Satisfied = data.feedback
        }
      })
    });
  }

  SendFeedback() {
    let data = {
      feedback: this.Satisfied,
      feedbackComment: this.feedback,
      complaintId: this.complaintId
    }
    this.web.post('grievances/feedback', data).subscribe((res: any) => {
      if (res.status == 1) {
        this.showAlert('Success', 'Feedback sent successfully')
        this.location.back();
      }
    })
  }
  async showAlert(header: string, message: string) {
    const alert = await this.alert.create({
      header,
      message,
      buttons: ["OK"]
    })

    await alert.present()
  }
}
