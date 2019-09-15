package com.stefanini.application.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stefanini.application.domain.dto.DetalhesFuncionario;
import com.stefanini.application.domain.entity.Funcionario;
import com.stefanini.application.domain.enums.Cargo;
import com.stefanini.application.domain.enums.RoleName;
import com.stefanini.application.domain.repository.AvaliacaoProfissionalRepository;
import com.stefanini.application.domain.repository.FuncionarioRepository;
import com.stefanini.application.message.response.InfoFuncionarioResponse;
import com.stefanini.application.service.FuncionarioService;

@Service
public class FuncionarioServiceImpl extends BaseService implements FuncionarioService {

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private AvaliacaoProfissionalRepository avaliacaoProfissionalRepository;

	@Override
	@Transactional(readOnly = true)
	public List<DetalhesFuncionario> listarDetalhesFuncionarios() throws Exception {

		if (hasRole(RoleName.ROLE_GERENTE)) {
			return funcionarioRepository.listarDetalhesFuncionariosGerente(user().getId());
		} else if (hasRole(RoleName.ROLE_BP)) {
			return funcionarioRepository.listarDetalhesFuncionariosBP(user().getId());
		} else if (hasRole(RoleName.ROLE_MENTOR)) {
			return funcionarioRepository.listarDetalhesFuncionariosMentor(user().getId());
		}

		return new ArrayList<DetalhesFuncionario>();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<InfoFuncionarioResponse> obterDetalhesFuncionarioPorId(Long funcionarioId) throws Exception {
		InfoFuncionarioResponse response = null;
		Optional<DetalhesFuncionario> funcionarioOpt = funcionarioRepository.obterDetalhesFuncionario(funcionarioId);
		if (funcionarioOpt.isPresent()) {
			response = new InfoFuncionarioResponse();
			response.setFuncionario(funcionarioOpt.get());
			response.setDetalhesAvaliacoes(avaliacaoProfissionalRepository.obterDetalhesAvaliacoesFuncionario(funcionarioId));
		}
		return Optional.ofNullable(response);
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<InfoFuncionarioResponse> obterDetalhesFuncionarioPorUsuarioId(Long usuarioId) throws Exception {
		InfoFuncionarioResponse response = null;
		Optional<DetalhesFuncionario> funcionarioOpt = funcionarioRepository.obterDetalhesFuncionarioPorUsuarioId(usuarioId);
		if (funcionarioOpt.isPresent()) {
			response = new InfoFuncionarioResponse();
			response.setFuncionario(funcionarioOpt.get());
			response.setDetalhesAvaliacoes(avaliacaoProfissionalRepository.obterDetalhesAvaliacoesFuncionario(funcionarioOpt.get().getId()));
		}
		return Optional.ofNullable(response);
	}

	@Override
	@Transactional
	public Boolean promoverFuncionario(Long funcionarioId) {
		try {
			Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(funcionarioId);
			if (funcionarioOpt.isPresent()) {
				Funcionario funcionario = funcionarioOpt.get();

				Cargo proximoCargo = Cargo.proximo(funcionario.getNivel(), funcionario.getCargo());
				if (proximoCargo.equals(funcionario.getCargo())) {
					return false;
				}

				funcionario.setCargo(proximoCargo);
				funcionario.setDataPromocao(new Date());
				funcionarioRepository.saveAndFlush(funcionario);

				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

}
