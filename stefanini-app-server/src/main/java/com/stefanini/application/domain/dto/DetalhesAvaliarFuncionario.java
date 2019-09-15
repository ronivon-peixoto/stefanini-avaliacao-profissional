package com.stefanini.application.domain.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface DetalhesAvaliarFuncionario {
	
	@Value("#{target.id_avaliacao}")
	Long getIdAvaliacao();

	@Value("#{target.data_criacao}")
	Date getDataCriacaoAvaliacao();

	@Value("#{target.id_funcionario}")
	Long getIdFuncionario();

	@Value("#{target.nome}")
	String getNomeFuncionario();

	@Value("#{target.cpf}")
	String getCpfFuncionario();

	@Value("#{target.nivel}")
	Integer getNivelFuncionario();

	@Value("#{target.cargo}")
	String getCargoFuncionario();

	@Value("#{target.data_admissao}")
	Date getDataAdmissaoFuncionario();

	@Value("#{target.nome_mentor}")
	String getNomeMentor();

	@Value("#{target.nome_gerente}")
	String getNomeGerente();

	@Value("#{target.nome_centro_custo}")
	String getNomeCentroCusto();

}
