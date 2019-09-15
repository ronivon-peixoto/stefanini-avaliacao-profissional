import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { DetalhesFuncionario } from '../models/detalhes-funcionario.model';
import { InfoFuncionario } from '../models/info-funcionario.model';

@Injectable({
  providedIn: 'root'
})
export class FuncionarioService {

    list: DetalhesFuncionario[];

    constructor(private http: HttpClient) { }

    refreshList() {
        this.http.get<DetalhesFuncionario[]>(`${environment.api}/funcionarios`)
            .toPromise()
            .then(res => this.list = res as DetalhesFuncionario[]);
    }

    obterDetalhesFuncionario(id: number): Observable<InfoFuncionario> {
        return this.http.get<InfoFuncionario>(`${environment.api}/funcionarios/${id}`);
    }

    promover(idFuncionario: number): any {
        return this.http.put(`${environment.api}/funcionarios/${idFuncionario}/promover`, {});
    }

}
