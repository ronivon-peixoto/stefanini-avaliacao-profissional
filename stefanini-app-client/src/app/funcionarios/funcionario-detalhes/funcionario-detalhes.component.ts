import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';

import { FuncionarioService } from 'src/app/services/funcionario.service';
import { InfoFuncionario } from 'src/app/models/info-funcionario.model';
import { AuthenticationService } from 'src/app/security/authentication.service';
import { Role } from 'src/app/security/role.enum';

@Component({ templateUrl: './funcionario-detalhes.component.html' })
export class FuncionarioDetalhesComponent implements OnInit {

   userAuthGerente: boolean;
   infoFuncionario: InfoFuncionario;

   constructor(
      private router: Router,
      private route: ActivatedRoute,
      private authenticationService: AuthenticationService,
      private funcionarioService: FuncionarioService) {
         this.authenticationService.currentUser.subscribe(res => 
            this.userAuthGerente = res.authorities.some(r => r === Role.Gerente));
      }
   
   ngOnInit() {
      let funcionarioId = this.route.snapshot.paramMap.get('funcionarioId');
      if(funcionarioId) {
         this.funcionarioService.obterDetalhesFuncionario(+funcionarioId)
            .subscribe(result => { 
               this.infoFuncionario = result;
            }, err => {
               alert("Desculpe! Não foi possível atender esta requisição.");
               this.router.navigate(['funcionarios']);
            });
      }
   }

   promover() {
      if (this.userAuthGerente && confirm('Você tem certeza que deseja promover este funcionário?')) {
         this.funcionarioService.promover(this.infoFuncionario.funcionario.id).subscribe(res => {
            alert('Funcionário promovido com sucesso!');
            this.funcionarioService.obterDetalhesFuncionario(this.infoFuncionario.funcionario.id).subscribe(result => { 
               this.infoFuncionario = result;
            });
         }, err => {
            alert("Desculpe! Não foi possível realizar esta ação.");
         });
      }
   }

}
