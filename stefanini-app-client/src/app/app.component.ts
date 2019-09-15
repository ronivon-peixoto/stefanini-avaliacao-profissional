import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from './security/authentication.service';
import { JwtInfo } from './security/jwt-info';
import { Role } from './security/role.enum';

@Component({
	selector: 'app-root',
	templateUrl: './app.component.html',
	styleUrls: []
})
export class AppComponent {

   currentUser: JwtInfo;

   constructor(
      private router: Router, 
      private authenticationService: AuthenticationService) {
      this.authenticationService.currentUser.subscribe(res => this.currentUser = res);
   }

   logout() {
      this.authenticationService.logout();
      this.router.navigate(['/login']);
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
