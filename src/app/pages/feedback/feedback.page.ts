import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-feedback',
  templateUrl: './feedback.page.html',
  styleUrls: ['./feedback.page.scss'],
})
export class FeedbackPage implements OnInit {

  constructor(private route: ActivatedRoute) { }
  complaintId=''
  ngOnInit() {
   this.route.params.subscribe(params => {
    this.complaintId=params["complaintId"];
    });
  }

}
