import { Component, OnInit } from '@angular/core';
import { Grievance } from 'src/app/Grievance';
import { WebrequestService } from 'src/app/api/webrequest.service';
import { Plugins } from '@capacitor/core';
import { Student } from 'src/app/Student';
import { AlertController } from '@ionic/angular';
import { Route } from '@angular/compiler/src/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-complaint-status',
  templateUrl: './complaint-status.page.html',
  styleUrls: ['./complaint-status.page.scss'],
})
export class ComplaintStatusPage implements OnInit {
  griveances: Array<Grievance> = []
  Student: Student = {}
  Categories = []
  constructor(private web: WebrequestService, private alert: AlertController, private router: Router) { }

  async ngOnInit() {
    const { Storage } = Plugins;
    await Storage.get({ key: 'student' }).then(res => {
      if (res) {
        this.Student = JSON.parse(res.value)
      }
    }).then(async () => {
      await this.GetCategories().then(() => {
        this.GetComplaints()
      })

    })
  }

  GetComplaints() {
    this.web.Get('grievances').toPromise().then((data: Array<any>) => {
      this.griveances = data
      this.griveances = data.filter(ele => ele.complaintStudentId == this.Student.studentId)
      this.griveances.forEach(g => {
        g.categoryName = this.Categories[this.Categories.findIndex(e => e.categoryId == g.categoryId)].categoryName
      })
    })
  }



  async GetCategories() {
    await this.web.Get('categories').toPromise().then((data: Array<any>) => {
      this.Categories = data
    })
  }
}
