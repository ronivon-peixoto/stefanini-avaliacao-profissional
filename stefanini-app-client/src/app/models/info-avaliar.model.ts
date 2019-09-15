import { CriterioAvaliacao } from './criterio-avaliacao.model';
import { property } from './property.model';

export class InfoAvaliar {

   // info
   nomeMentor: String;
   notaMentor: number;
   comentariosMentor: String;
   nomeBp: String;
   notaBp: number;
   comentariosBp: String;
   ressalvasAvaliacaoAnterior: String;

   // form
   idAvaliacao: number;
   ressalvas: String;
   comentarios: String;
   criterios: CriterioAvaliacao[];
   pesos: property[];

}
