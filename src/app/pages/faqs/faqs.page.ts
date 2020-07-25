import { Component, OnInit, NgZone } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-faqs',
  templateUrl: './faqs.page.html',
  styleUrls: ['./faqs.page.scss'],
})
export class FaqsPage implements OnInit {

  constructor(private router:Router,public zone:NgZone) { }
  
  
 
  
  ngOnInit() {
    
    const items = document.querySelectorAll(".accordion button");
    function toggleAccordion(items: any) {
        const itemToggle = this.getAttribute('aria-expanded');
        
        
        
        if (itemToggle == 'false') {
          this.setAttribute('aria-expanded', 'true');
        }
        if (itemToggle == 'true') {
          this.setAttribute('aria-expanded', 'false');
        }
      }
      items.forEach(item => item.addEventListener('click', toggleAccordion));
  }
  
}