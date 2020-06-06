import { Component, OnInit } from '@angular/core';
import { Router, RouterEvent } from '@angular/router';
import { MenuController } from '@ionic/angular';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.page.html',
  styleUrls: ['./menu.page.scss'],
})
export class MenuPage implements OnInit {

  constructor( private router:Router) { 
    this.router.events.subscribe((event:RouterEvent)=>{
     
      this.SelectedPath=event.url
    })
  }
  SelectedPath=''
  pages=[
    {
      title:'Profile',
      url:'/menu/profile'
    },
    {
      title:'FAQs',
      url:'/menu/faqs'
    },
    {
      title:'Lodge Complaints',
      url:'/menu/lodge-complaint'
    },
    {
      title:'Status',
      url:'/menu/complaint-status'
    }
  ]

  ngOnInit() {
    this.router.navigate(['/menu/profile'])
   
  }

}
