import { Component, OnInit } from '@angular/core';
import { Grievance } from 'src/app/Grievance';
import { WebrequestService } from 'src/app/api/webrequest.service';
import { Plugins } from '@capacitor/core';
import { Student } from 'src/app/Student';
import { AlertController } from '@ionic/angular';
import { Route } from '@angular/compiler/src/core';
import { Router } from '@angular/router';
import * as Notiflix from 'notiflix'
@Component({
  selector: 'app-complaint-status',
  templateUrl: './complaint-status.page.html',
  styleUrls: ['./complaint-status.page.scss'],
})
export class ComplaintStatusPage implements OnInit {
  griveances: Array<Grievance> = []
  Student: Student = {}
  Categories = []
  filter = 'all'
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
      this.griveances.sort((a, b) => a.complaintDateTime > b.complaintDateTime ? -1 : 1)
      console.log(this.griveances)
      this.griveances.forEach(g => {
        g.categoryName = this.Categories[this.Categories.findIndex(e => e.categoryId == g.categoryId)].categoryName
      })
    })
  }

  Withdraw(complaintId) {
    Notiflix.Confirm.Show('Notiflix Confirm',
      'Are you sure to withdraw the complaint?',
      'Yes', 'No', () => {//Yes Button
        Notiflix.Loading.Standard()
        this.web.delete(`grievances/grievance/${complaintId}`).toPromise().then((res: any) => {
          if (res.status == 1) {
            this.griveances = this.griveances.filter(g => g.complaintId != complaintId)
            Notiflix.Loading.Remove();
            Notiflix.Notify.Success('Complaint has been withdrawn.')

          }
          else {
            Notiflix.Loading.Remove();
            Notiflix.Notify.Failure('Something went wrong.')
          }
        }).catch(err => {
          Notiflix.Loading.Remove();
          Notiflix.Notify.Failure('Error ' + err)
        })
      }, () => { //No button
        Notiflix.Notify.Failure('The withdraw has been cancelled')
      });
  }


  async GetCategories() {
    await this.web.Get('categories').toPromise().then((data: Array<any>) => {
      this.Categories = data
    })
  }
}
