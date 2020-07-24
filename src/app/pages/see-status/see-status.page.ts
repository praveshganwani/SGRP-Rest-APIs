import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-see-status',
  templateUrl: './see-status.page.html',
  styleUrls: ['./see-status.page.scss'],
})
export class SeeStatusPage implements OnInit {

  constructor(private route:ActivatedRoute) { }
  complaintId:string=''
  ngOnInit() {
   
    this.route.params.subscribe(params => {
     this.complaintId=params["complaintId"];
     });
  }

}
