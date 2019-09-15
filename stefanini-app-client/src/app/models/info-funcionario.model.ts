import { DetalhesFuncionario } from './detalhes-funcionario.model';
import { DetalhesAvaliacaoProfissional } from './detalhes-avaliacao-profissional.model';

export class InfoFuncionario {

    funcionario: DetalhesFuncionario;

    promover: boolean;

    detalhesAvaliacoes: DetalhesAvaliacaoProfissional[];

}