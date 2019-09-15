import { Component, OnInit } from '@angular/core';
import { FuncionarioService } from '../services/funcionario.service';
import { JwtInfo } from '../security/jwt-info';
import { AuthenticationService } from '../security/authentication.service';
import { Role } from '../security/role.enum';

@Component({ templateUrl: './funcionarios.component.html' })
export class FuncionariosComponent implements OnInit {

   constructor(
      private service: FuncionarioService) {}

   ngOnInit() {
      this.service.refreshList();
   }

}
