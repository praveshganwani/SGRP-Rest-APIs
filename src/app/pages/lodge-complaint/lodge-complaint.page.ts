import { Component, OnInit } from '@angular/core';
import { Grievance } from 'src/app/Grievance';
import { WebrequestService } from 'src/app/api/webrequest.service';
import { Plugins } from '@capacitor/core';
import { Student } from 'src/app/Student';
import { AlertController } from '@ionic/angular';
import Notiflix from "notiflix";

@Component({
  selector: 'app-lodge-complaint',
  templateUrl: './lodge-complaint.page.html',
  styleUrls: ['./lodge-complaint.page.scss'],
})
export class LodgeComplaintPage implements OnInit {
  grievance: Grievance = {}
  constructor(private web: WebrequestService, private alert: AlertController) { }
  Categories = []
  Student: Student = {}
  async ngOnInit() {

    this.GetCategories()
    const { Storage } = Plugins;
    await Storage.get({ key: 'student' }).then(res => {
      if (res) {
        this.Student = JSON.parse(res.value)
      }
    })
    this.grievance.complaintStudentId = this.Student.studentId;
  }


  GetCategories() {
    this.web.Get('categories').toPromise().then((data: Array<any>) => {
      this.Categories = data
    })
  }
  DetectComplaint() {
    try {
      if (this.grievance.complaintDetail && this.grievance.complaintTitle) {
        this.web.post('grievances/check/spam', { complaintDetail: this.grievance.complaintDetail }).subscribe((res: any) => {
          console.log(res)
          if (res.status == -100) {
            Notiflix.Notify.Failure('Spam Complaints not allowed.Simplify your complaint')
          }
          else if (res.status == 1) {
            this.web.post('grievances/check/category', { complaintDetail: this.grievance.complaintDetail }).subscribe((res: any) => {
              console.log(res)
              this.grievance.categoryId = res.categoryId
            })
          }
        })
      }
      else {
        Notiflix.Notify.Failure('Please add more detail to the complaint.')
      }
    }
    catch (err) {
      console.log(err)
    }
  }
  LodgeComplaint() {
    try {
      Notiflix.Loading.Standard();
      if (this.grievance.complaintDetail.length < 15) {
        Notiflix.Loading.Remove();
        Notiflix.Notify.Warning('Please add more detail to the complaint.')
      }
      else if (this.grievance.complaintDetail && this.grievance.complaintTitle && this.grievance.categoryId && (this.grievance.complaintIsAnonymous == 0 || this.grievance.complaintIsAnonymous == 1)) {
        this.web.post('grievances/grievance', this.grievance).subscribe((res: any) => {
          Notiflix.Loading.Remove();
          if (res.status == 1) {
            this.showAlert('Sucess', 'Complaint logded successfully.')
            this.grievance = {}
          }
          else {
            this.showAlert('Failed', 'Something Went Wrong.')
          }
        })
      }
      else {
        console.log(this.grievance)
        Notiflix.Loading.Remove();
        Notiflix.Notify.Warning('Empty Complaint Not allowed.Make sure your category is detected')
      }
    }
    catch (err) {
      this.showAlert('Failed', 'Something Went Wrong.')
      Notiflix.Loading.Remove();
    }
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
