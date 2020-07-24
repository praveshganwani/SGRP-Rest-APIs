import { Directive,ElementRef, Input} from '@angular/core';

@Directive({
  selector: '[appDatetimeline]'
})
export class DatetimelineDirective {
  @Input('appDatetimeline') datetime: string;
  constructor(el: ElementRef) {
        
   }

}
