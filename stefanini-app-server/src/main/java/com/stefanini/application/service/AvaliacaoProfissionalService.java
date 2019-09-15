package com.stefanini.application.service;

import java.util.List;

import com.stefanini.application.domain.dto.DetalhesAvaliarFuncionario;
import com.stefanini.application.domain.entity.AvaliacaoProfissional;
import com.stefanini.application.message.request.AvaliacaoForm;
import com.stefanini.application.message.response.InfoAvaliarResponse;
import com.stefanini.application.service.exception.AvaliacaoException;

public interface AvaliacaoProfissionalService {

	Integer processarNovasAvaliacoes() throws AvaliacaoException;

	List<DetalhesAvaliarFuncionario> listarAvaliacoesPendentesVotacao();

	InfoAvaliarResponse obterInfoAvaliacao(Long avaliacaoId);

	AvaliacaoProfissional avaliarFuncionario(AvaliacaoForm form) throws AvaliacaoException;

	AvaliacaoProfissional aprovarAvaliacaoFuncionario(AvaliacaoForm form) throws AvaliacaoException;

}
