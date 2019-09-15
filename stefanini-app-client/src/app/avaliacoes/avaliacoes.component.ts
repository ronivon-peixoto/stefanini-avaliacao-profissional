import { Component, OnInit } from '@angular/core';
import { AvaliacaoService } from '../services/avaliacao.service';
import { AuthenticationService } from '../security/authentication.service';
import { JwtInfo } from '../security/jwt-info';
import { Role } from '../security/role.enum';

@Component({ templateUrl: './avaliacoes.component.html' })
export class AvaliacoesComponent implements OnInit {

   currentUser: JwtInfo;

   constructor(
      private authenticationService: AuthenticationService,
      private avaliacaoService: AvaliacaoService) {
         this.authenticationService.currentUser.subscribe(res => this.currentUser = res);
      }

   ngOnInit() {
      this.avaliacaoService.refreshList();
   }

   is(role: Role) {
      return this.currentUser.authorities.some(r => r === role);
   }

   hasRole(roles: Role[]) {
      let hasRole = false;
      for (let i = 0; i < roles.length; i++) {
         if (this.is(roles[i])) {
            hasRole = true;
            break;
         }
      }
      return hasRole;
   }
}
