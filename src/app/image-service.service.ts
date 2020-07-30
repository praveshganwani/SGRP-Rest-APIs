import { Injectable } from '@angular/core';
import { Camera, CameraOptions } from '@ionic-native/camera/ngx';
import { Platform } from '@ionic/angular';
import { File } from '@ionic-native/file/ngx';
import * as AWS from 'aws-sdk';
@Injectable({
  providedIn: 'root'
})
export class ImageServiceService {

  constructor(private camera: Camera, private platform: Platform, private file: File) { }
  private options: CameraOptions = {
    targetWidth: 384,
    targetHeight: 384,
    quality: 100,
    destinationType: this.camera.DestinationType.DATA_URL,
    encodingType: this.camera.EncodingType.JPEG,
    mediaType: this.camera.MediaType.PICTURE
  }

  s3Putimage(file, key) {
    return new Promise((resolve, reject) => {
      AWS.config.accessKeyId = 'ASIASWWWYSCOMP7IZVGA';
      AWS.config.secretAccessKey = 'lt4mziEKTpAriTAKNapzTHuQ3SmXTYn5I+HATF0f';
      AWS.config.sessionToken = 'FwoGZXIvYXdzEHkaDBUBu4/CgMnkE9ZikSLGAYM1xDnHQM0fc7KEgCwtkLmGYAsgryTdrHJe2MBtWh9ywbdvbxLSbSXcjndcW2sp4mtKEdzL3/vdk+2aTXNyzvwWo4+8ENVJKOmtLAvSo17FeS2Bv0sVH4sSXn3HXq7wRW9qbWpDPKvv+dfi+YaDg7l54eEd4MHp9iSCf7FmZRd2+8Rn8B7cPAMKy2X+Wno+gSbqzMFG8FjKlINhbzqjFYVK4PO6lj4k3QZSbyMfMVwIkq/WEUdT5qT4uxBHi+fpj1dCUla0Viju24v5BTIto7SBGiejUy34wisV39MVnIBb0SxE5LXuQh9kXa3K9Q+NR7cBGVr3ZhXURmeK';
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
