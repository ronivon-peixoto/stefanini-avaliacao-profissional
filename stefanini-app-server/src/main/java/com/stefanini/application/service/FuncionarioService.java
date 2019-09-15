package com.stefanini.application.service;

import java.util.List;
import java.util.Optional;

import com.stefanini.application.domain.dto.DetalhesFuncionario;
import com.stefanini.application.message.response.InfoFuncionarioResponse;

public interface FuncionarioService {

	List<DetalhesFuncionario> listarDetalhesFuncionarios() throws Exception;

	Optional<InfoFuncionarioResponse> obterDetalhesFuncionarioPorId(Long funcionarioId) throws Exception;

	Optional<InfoFuncionarioResponse> obterDetalhesFuncionarioPorUsuarioId(Long usuarioId) throws Exception;

	Boolean promoverFuncionario(Long funcionarioId);

}
