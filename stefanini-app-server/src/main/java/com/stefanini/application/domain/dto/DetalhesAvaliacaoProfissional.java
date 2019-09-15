package com.stefanini.application.domain.dto;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;

public interface DetalhesAvaliacaoProfissional {

	Long getId();

	@Value("#{target.data_aprovacao_gerente}")
	Date getDataAprovacao();

	String getNomeMentorAvaliador();

	String getNomeBpAvaliador();

	String getNomeGerenteAprovador();

	@Value("#{target.nota_final}")
	Double getNotaFinal();

	@Value("#{target.comentarios_gerente}")
	String getComentariosGerente();

	@Value("#{target.ressalvas_gerente}")
	String getRessalvasGerente();

}
