import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { environment } from 'src/environments/environment';
import { DetalhesAvaliarFuncionario } from '../models/detalhes-avaliar-funcionario.model';
import { Observable } from 'rxjs';
import { InfoAvaliar } from '../models/info-avaliar.model';

@Injectable({
  providedIn: 'root'
})
export class AvaliacaoService {

   list: DetalhesAvaliarFuncionario[];
   
   constructor(private http: HttpClient) { }

   refreshList() {
      this.http.get<DetalhesAvaliarFuncionario[]>(`${environment.api}/avaliacoes`)
         .toPromise()
         .then(res => this.list = res as DetalhesAvaliarFuncionario[]);
   }

   obterCriteriosAvaliacao(avaliacaoId: number): Observable<InfoAvaliar> {
      return this.http.get<InfoAvaliar>(`${environment.api}/avaliacoes/${avaliacaoId}/criterios`);
   }

   avaliar(formData: InfoAvaliar): any {
      return this.http.put(`${environment.api}/avaliacoes/${formData.idAvaliacao}/avaliar`, formData);
   }

   aprovar(formData: InfoAvaliar): any {
      return this.http.put(`${environment.api}/avaliacoes/${formData.idAvaliacao}/aprovar`, formData);
   }

}
