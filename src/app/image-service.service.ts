import { Injectable } from '@angular/core';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { Platform } from '@ionic/angular';
import { File } from '@ionic-native/file/ngx';
import * as AWS from 'aws-sdk';
import { WebrequestService } from './api/webrequest.service';
@Injectable({
  providedIn: 'root'
})
export class ImageServiceService {

  constructor(private camera: Camera,private webreq:WebrequestService) { }
  private options: CameraOptions = {
    targetWidth: 384,
    targetHeight: 384,
    quality: 100,
    destinationType: this.camera.DestinationType.DATA_URL,
    encodingType: this.camera.EncodingType.JPEG,
    mediaType: this.camera.MediaType.PICTURE
  }

  s3Putimage(file, key) {
    return new Promise(async (resolve, reject) => {
      await this.webreq.Get('properties').toPromise().then((res:any)=>{
        AWS.config.accessKeyId = res.accessKeyId;
        AWS.config.secretAccessKey = res.secretAccessKey;
        AWS.config.sessionToken = res.sessionToken
      })
    
      AWS.config.region = 'us-east-1';
      AWS.config.signatureVersion = 'v4';
      let s3 = new AWS.S3();

      const params = {
        Body: file.body,
        Bucket: 'sih2020complaints',
        Key: key,
        ACL: "public-read",
      };

      s3.putObject(params, (err, data) => {
        console.log("Response is", data)
        if (err) {
          reject(err);
        } else {
          resolve(key);
        }
      });
    })
  }
  uploadImage(image, imageName) {
    return new Promise((resolve, reject) => {
     
      const body = image
      let date = Date.now();
      const key = imageName + date + '.jpg';
      this.s3Putimage({ body, mime: `image/jpeg` }, key).then((result) => { resolve(result); }).catch((err) => { reject(err); });
    })
  }
}
