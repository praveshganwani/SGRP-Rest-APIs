import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { WebrequestService } from 'src/app/api/webrequest.service';

@Component({
  selector: 'app-see-status',
  templateUrl: './see-status.page.html',
  styleUrls: ['./see-status.page.scss'],
})
export class SeeStatusPage implements OnInit {

  constructor(private route: ActivatedRoute, private web: WebrequestService) { }
  complaintId: string = ''
  Activities = []
  events = []
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.complaintId = params["complaintId"];
      this.getComplaintActivity();
    });
  }

  getComplaintActivity() {
    this.web.Get('activities/activity/' + this.complaintId).subscribe((res: Array<any>) => {
      this.Activities = res
      this.events = []
    })
  }

  getUniversityName() {
    return localStorage.getItem('UniversityName')
  }

  getInstitueName(){
    return localStorage.getItem('InstituteName')

  }
}
