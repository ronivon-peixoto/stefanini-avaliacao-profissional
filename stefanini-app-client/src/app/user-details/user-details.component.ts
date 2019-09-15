import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from '../security/authentication.service';
import { InfoFuncionario } from '../models/info-funcionario.model';

@Component({ templateUrl: './user-details.component.html' })
export class UserDetailsComponent implements OnInit {

   infoFuncionario: InfoFuncionario;

   constructor(
      private router: Router,
      private authenticationService: AuthenticationService) { }
   
   ngOnInit() {
      this.authenticationService.userDetails()
         .subscribe(result => { 
            this.infoFuncionario = result;
         }, err => {
            alert("Desculpe! Não foi possível atender esta requisição.");
            this.router.navigate(['/']);
         });
   }

}
