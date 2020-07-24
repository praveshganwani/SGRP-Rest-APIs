import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-faqs',
  templateUrl: './faqs.page.html',
  styleUrls: ['./faqs.page.scss'],
})
export class FaqsPage implements OnInit {

  constructor(private router:Router,public zone:NgZone) { }
  
  
  // private fn: Function toggleAccordion() {
  //   const itemToggle = this.getAttribute('aria-expanded');
    
  //   for (let i = 0; i < this.items.length; i++) {
  //     this.items[i].setAttribute('aria-expanded', 'false');
  //   }
    
  //   if (itemToggle == 'false') {
  //     this.setAttribute('aria-expanded', 'true');
  //   }
  // };
  
  ngOnInit() {
    
    const items = document.querySelectorAll(".accordion button div");
    function toggleAccordion(items) {
        const itemToggle = this.getAttribute('aria-expanded');
        
        for (let i = 0; i < this.items.length; i++) {
          this.items[i].setAttribute('aria-expanded', 'false');
        }
        
        if (itemToggle == 'false') {
          this.setAttribute('aria-expanded', 'true');
        }
      }
      items.forEach(item => item.addEventListener('click', toggleAccordion));
  }
  
}