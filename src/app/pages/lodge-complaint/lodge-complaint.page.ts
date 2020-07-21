import { Component, OnInit } from '@angular/core';
import { Grievance } from 'src/app/Grievance';
import { WebrequestService } from 'src/app/api/webrequest.service';
import { Plugins } from '@capacitor/core';
import { Student } from 'src/app/Student';
import { AlertController } from '@ionic/angular';

@Component({
  selector: 'app-lodge-complaint',
  templateUrl: './lodge-complaint.page.html',
  styleUrls: ['./lodge-complaint.page.scss'],
})
export class LodgeComplaintPage implements OnInit {
  grievance: Grievance = {}
  constructor(private web: WebrequestService,private alert: AlertController) { }
  Categories = []
  Student:Student = {}
  async ngOnInit() {
    this.GetCategories()
    const { Storage } = Plugins;
    await Storage.get({ key: 'student' }).then(res => {
      if (res) {
        this.Student = JSON.parse(res.value)
      }
    })
   
    console.log(this.Student)
    this.grievance.complaintStudentId = this.Student.studentId;
  }


  GetCategories() {
    this.web.Get('categories').toPromise().then((data:Array<any>) => {
      this.Categories = data
    })
  }


  LodgeComplaint(){
    console.log(this.grievance)
    this.web.post('grievances/grievance',this.grievance).subscribe((res:any)=>{
      console.log(res)
      if(res.status == 1){
        this.showAlert('Sucess','Complaint logded successfully.')
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
