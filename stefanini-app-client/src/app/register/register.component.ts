import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AuthenticationService } from '../security/authentication.service';
import { SignUpInfo } from '../security/signup-info';
import { AuthLoginInfo } from '../security/login-info';

@Component({ templateUrl: './register.component.html' })
export class RegisterComponent implements OnInit {

   signupForm: FormGroup;
   signupInfo: SignUpInfo;
   loading = false;
   submitted = false;
   isSignedUp = false;
   isSignUpFailed = false;
   errorMessage = '';

   constructor(
      private formBuilder: FormBuilder,
      private router: Router,
      private authenticationService: AuthenticationService) { }

   ngOnInit() {
      if (this.signupForm) {
         this.signupForm.reset();
      }
      this.signupForm = this.formBuilder.group({
         name:       ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
         username:   ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50)]],
         email:      ['', [Validators.required, Validators.email, Validators.maxLength(50)]],
         password:   ['', [Validators.required, Validators.minLength(6), Validators.maxLength(100)]]
      });
   }

   // convenience getter for easy access to form fields
   get f() { 
      return this.signupForm.controls; 
   }

   onSubmit() {
      this.submitted = true;

      // stop here if form is invalid
      if (this.signupForm.invalid) {
         return;
      }

      this.loading = true;
      this.signupInfo = new SignUpInfo(
         this.f.name.value,
         this.f.username.value,
         this.f.email.value,
         this.f.password.value
      );
      this.authenticationService.signUp(this.signupInfo)
         .subscribe(data => {
            this.isSignedUp = true;
            this.isSignUpFailed = false;
            this.authenticationService.login(new AuthLoginInfo(this.signupInfo.username, this.signupInfo.password))
               .pipe(first())
               .subscribe(
                  data => {
                     this.router.navigate(['/']);
                     alert('Usuário registrado com sucesso!');
                  });
         }, error => {
            this.errorMessage = error;
            this.isSignUpFailed = true;
            this.loading = false;
         }
      );
   }

}
