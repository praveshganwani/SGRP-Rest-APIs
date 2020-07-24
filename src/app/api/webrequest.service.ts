import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http'
@Injectable({
  providedIn: 'root'
})
export class WebrequestService {
  ROOT_URL='http://sgrprestservice-env.eba-njsui4dk.us-east-1.elasticbeanstalk.com/webapi'
  constructor(private http:HttpClient) { }

  Get(url:string){
    return this.http.get(`${this.ROOT_URL}/${url}`)
  }
  post(url:string,data){
    return this.http.post(`${this.ROOT_URL}/${url}`,data)
  }

}
