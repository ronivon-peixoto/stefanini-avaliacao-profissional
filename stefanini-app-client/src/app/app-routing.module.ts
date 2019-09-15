import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AuthGuard } from './security/guards/auth.guard';
import { Role } from './security/role.enum';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { UserDetailsComponent } from './user-details/user-details.component';
import { HomeComponent } from './home/home.component';
import { FuncionariosComponent } from './funcionarios/funcionarios.component';
import { FuncionarioDetalhesComponent } from './funcionarios/funcionario-detalhes/funcionario-detalhes.component';
import { AvaliacoesComponent } from './avaliacoes/avaliacoes.component';
import { AvaliacaoFormComponent } from './avaliacoes/avaliacao-form/avaliacao-form.component';
import { AvaliacaoAprovarComponent } from './avaliacoes/avaliacao-aprovar/avaliacao-aprovar.component';

const routes: Routes = [
   { path: 'login',                          component: LoginComponent },
   { path: 'signup',                         component: RegisterComponent },
   { path: '',                               component: HomeComponent,                canActivate: [AuthGuard] },
   { path: 'user-details',                   component: UserDetailsComponent,         canActivate: [AuthGuard], data: { roles: [Role.Gerente, Role.BusinessPartner, Role.Mentor, Role.Funcionario, Role.User] }},
   { path: 'funcionarios',                   component: FuncionariosComponent,        canActivate: [AuthGuard], data: { roles: [Role.Gerente, Role.BusinessPartner, Role.Mentor] }},
   { path: 'funcionario/:funcionarioId',     component: FuncionarioDetalhesComponent, canActivate: [AuthGuard], data: { roles: [Role.Gerente, Role.BusinessPartner, Role.Mentor] }},
   { path: 'avaliacoes',                     component: AvaliacoesComponent,          canActivate: [AuthGuard], data: { roles: [Role.Gerente, Role.BusinessPartner, Role.Mentor] }},
   { path: 'avaliacao/:avaliacaoId',         component: AvaliacaoFormComponent,       canActivate: [AuthGuard], data: { roles: [Role.BusinessPartner, Role.Mentor] }},
   { path: 'avaliacao/aprovar/:avaliacaoId', component: AvaliacaoAprovarComponent,    canActivate: [AuthGuard], data: { roles: [Role.Gerente] }},
   { path: '**', redirectTo: '' }
];

@NgModule({
   imports: [RouterModule.forRoot(routes)],
   exports: [RouterModule]
})
export class AppRoutingModule { }
