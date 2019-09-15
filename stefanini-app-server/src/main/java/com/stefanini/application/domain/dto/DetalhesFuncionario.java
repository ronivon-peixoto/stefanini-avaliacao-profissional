package com.stefanini.application.domain.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface DetalhesFuncionario {

	String getUsername();

	String getEmail();

	Long getId();

	String getNome();

	String getCpf();

	@Value("#{target.data_admissao}")
	Date getDataAdmissao();

	Integer getNivel();

	String getCargo();

	@Value("#{target.data_promocao}")
	Date getDataPromocao();

	@Value("#{target.nome_mentor}")
	String getNomeMentor();

	@Value("#{target.nome_gerente}")
	String getNomeGerente();

	@Value("#{target.nome_centro_custo}")
	String getNomeCentroCusto();

	@Value("#{target.endereco_centro_custo}")
	String getEnderecoCentroCusto();

}
