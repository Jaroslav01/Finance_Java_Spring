import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor() {
    this.updateAUthorizedStatus();
  }
  username = "John Doe"
  isAuthorized = false;
  isQuery = false;
  // public navBarChange = new BehaviorSubject<boolean> (false);
  updateAUthorizedStatus(){
    this.isAuthorized = localStorage.getItem("access_token") != null;
    console.log(this.isAuthorized)
  }
}
