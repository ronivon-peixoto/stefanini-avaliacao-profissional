import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AvaliacaoService } from 'src/app/services/avaliacao.service';
import { InfoAvaliar } from 'src/app/models/info-avaliar.model';
import { ActivatedRoute, Router } from '@angular/router';

@Component({ templateUrl: './avaliacao-form.component.html' })
export class AvaliacaoFormComponent implements OnInit {

   public form: FormGroup;
   public criteriosEmpresa: FormArray;
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
         comentarios: [null],
         criterios: this.fb.array([])
      });
      this.criteriosEmpresa = this.form.get('criterios') as FormArray;

      for (let i = 0; i < this.infoAvaliar.criterios.length; i++) {
         this.criteriosEmpresa.push(this.fb.group({
            idCriterio: [this.infoAvaliar.criterios[i].idCriterio],
            nota:       [this.infoAvaliar.criterios[i].nota, Validators.compose([Validators.required])],
         }));
      }
   }

   get f() {
      return this.form.controls;
   }

   sf(index): FormGroup {
      return this.criteriosEmpresa.controls[index] as FormGroup;
   }

   get criteriosEmpresaFormArray() {
      return this.form.get('criterios') as FormArray;
   }

   submit() {
      if (this.form.invalid) {
         return;
      }
      this.avaliacaoService.avaliar(this.form.value).subscribe(res => {
         alert('Avaliação realizada com sucesso!');
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
