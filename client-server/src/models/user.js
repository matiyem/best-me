export class User {
  constructor(username, password, email, id,hiddenCaptcha,realCaptcha){
    this.username = username;
    this.password = password;
    this.email = email;
    this.id = id;
    this.roles=[];
    this.hiddenCaptcha=hiddenCaptcha;
    this.realCaptcha=realCaptcha;

  }
}
