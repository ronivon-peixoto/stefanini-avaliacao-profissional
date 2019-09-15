import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';

import { AuthenticationService } from '../security/authentication.service';
import { AuthLoginInfo } from '../security/login-info';


@Component({ templateUrl: './login.component.html' })
export class LoginComponent implements OnInit {

   loginForm: FormGroup;
   loginInfo: AuthLoginInfo;
   loading = false;
   submitted = false;
   returnUrl: string;
   error = '';

   constructor(
      private formBuilder: FormBuilder,
      private route: ActivatedRoute,
      private router: Router,
      private authenticationService: AuthenticationService
   ) { 
      // redirect to home if already logged in
      if (this.authenticationService.currentUserValue) {
         this.router.navigate(['/']);
      }
   }

   ngOnInit() {
      if (this.loginForm) {
         this.loginForm.reset();
      }
      this.loginForm = this.formBuilder.group({
         username: ['', Validators.required],
         password: ['', Validators.required]
      });

      // get return url from route parameters or default to '/'
      this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
   }

   // convenience getter for easy access to form fields
   get f() { 
      return this.loginForm.controls; 
   }

   onSubmit() {
      this.submitted = true;

      // stop here if form is invalid
      if (this.loginForm.invalid) {
         return;
      }

      this.loading = true;
      this.loginInfo = new AuthLoginInfo(this.f.username.value, this.f.password.value);
      this.authenticationService.login(this.loginInfo)
         .pipe(first())
         .subscribe(
               data => {
                  this.router.navigate([this.returnUrl]);
               },
               error => {
                  this.error = 'As credenciais informadas não são válidas!';
                  this.loading = false;
               });
   }
}
