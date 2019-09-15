import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { AvaliacaoService } from 'src/app/services/avaliacao.service';
import { InfoAvaliar } from 'src/app/models/info-avaliar.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({ templateUrl: './avaliacao-aprovar.component.html' })
export class AvaliacaoAprovarComponent implements OnInit {

   public form: FormGroup;
   public infoAvaliar: InfoAvaliar;

   constructor(
      private router: Router,
      private route: ActivatedRoute,
      private fb: FormBuilder,
      private avaliacaoService: AvaliacaoService) { }

   ngOnInit() {
      let avaliacaoId = this.route.snapshot.paramMap.get('avaliacaoId');
      if(avaliacaoId) {
         this.avaliacaoService.obterCriteriosAvaliacao(+avaliacaoId)
            .subscribe(result => {
               this.infoAvaliar = result;
               this.configurarForm();
            }, err => {
               alert("Desculpe! Não foi possível atender esta requisição.");
               this.redirectToHome();
            });
      }
   }

   configurarForm() {
      if (this.form) {
         this.form.reset();
      }
      this.form = this.fb.group({
         idAvaliacao: [this.infoAvaliar.idAvaliacao],
         ressalvas:   [null],
         comentarios: [null]
      });
   }

   get f() {
      return this.form.controls;
   }

   submit() {
      if (this.form.invalid) {
         return;
      }
      this.avaliacaoService.aprovar(this.form.value).subscribe(res => {
         alert('Aprovação realizada com sucesso!');
         this.redirectToHome();
      }, err => {
         alert(err);
      });
   }

   redirectToHome() {
      if (this.form) {
         this.form.reset();
      }
      this.router.navigate(['avaliacoes']);
   }

}
