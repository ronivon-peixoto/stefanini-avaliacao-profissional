package com.stefanini.application.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.stefanini.application.domain.dto.CriterioAvaliacaoDto;
import com.stefanini.application.domain.dto.DetalhesAvaliarFuncionario;
import com.stefanini.application.domain.entity.AvaliacaoProfissional;
import com.stefanini.application.domain.entity.CriterioAvaliacao;
import com.stefanini.application.domain.entity.Funcionario;
import com.stefanini.application.domain.entity.ValorEmpresa;
import com.stefanini.application.domain.enums.RoleName;
import com.stefanini.application.domain.enums.StatusAvaliacao;
import com.stefanini.application.domain.enums.ValorCriterio;
import com.stefanini.application.domain.repository.AvaliacaoProfissionalRepository;
import com.stefanini.application.domain.repository.CriterioAvaliacaoRepository;
import com.stefanini.application.domain.repository.FuncionarioRepository;
import com.stefanini.application.domain.repository.ValorEmpresaRepository;
import com.stefanini.application.message.request.AvaliacaoForm;
import com.stefanini.application.message.response.InfoAvaliarResponse;
import com.stefanini.application.service.AvaliacaoProfissionalService;
import com.stefanini.application.service.exception.AvaliacaoException;

@Service
public class AvaliacaoProfissionalServiceImpl extends BaseService implements AvaliacaoProfissionalService {

	private static final Double PERCENT_APROV_PROX_NIVEL = 80.0;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private AvaliacaoProfissionalRepository avaliacaoProfissionalRepository;

	@Autowired
	private CriterioAvaliacaoRepository criterioAvaliacaoRepository;

	@Autowired
	private ValorEmpresaRepository valorEmpresaRepository;

	@Value("${intervalo.meses.avaliar.funcionario}")
	private Integer intervaloMesesAvaliarFuncionario;

	@Override
	@Transactional
	public Integer processarNovasAvaliacoes() throws AvaliacaoException {
		Map<Long, List<ValorEmpresa>> valoresEmpresaMap = new HashMap<>();
		List<AvaliacaoProfissional> listaAvaliacoes = new ArrayList<AvaliacaoProfissional>();

		List<Funcionario> funcionarios = funcionarioRepository.obterFuncionariosParaAvaliavao(this.intervaloMesesAvaliarFuncionario);
		if (!funcionarios.isEmpty()) {
			for (Funcionario funcionario : funcionarios) {
				List<ValorEmpresa> valores = valoresEmpresaMap.get(funcionario.getEmpresa().getId());
				if (valores == null) {
					valores = valorEmpresaRepository.findAllByEmpresa(funcionario.getEmpresa());
					Double sum = valores.stream().mapToDouble(ValorEmpresa::getPeso).sum();
					if (sum < 100) {
						throw new AvaliacaoException("Somatório dos pesos inconsistente para avaliação do funcionário!");
					}
					valoresEmpresaMap.put(funcionario.getEmpresa().getId(), valores);
				}

				AvaliacaoProfissional avaliacao = new AvaliacaoProfissional();
				avaliacao.setStatus(StatusAvaliacao.AGUARDANDO_AVALIACAO);
				avaliacao.setFuncionario(funcionario);
				for (ValorEmpresa valorEmpresa : valores) {
					avaliacao.addCriterioAvaliacao(new CriterioAvaliacao(valorEmpresa));
				}
				listaAvaliacoes.add(avaliacao);
			}

			avaliacaoProfissionalRepository.saveAll(listaAvaliacoes);
		}

		return listaAvaliacoes.size();
	}

	@Override
	@Transactional(readOnly = true)
	public List<DetalhesAvaliarFuncionario> listarAvaliacoesPendentesVotacao() {
		if (hasRole(RoleName.ROLE_GERENTE)) {
			return avaliacaoProfissionalRepository.listarAvaliacoesPendentesAprovacaoGerente(user().getId());
		} else if (hasRole(RoleName.ROLE_BP)) {
			return avaliacaoProfissionalRepository.listarAvaliacoesPendentesVotacaoBP(user().getId());
		} else if (hasRole(RoleName.ROLE_MENTOR)) {
			return avaliacaoProfissionalRepository.listarAvaliacoesPendentesVotacaoMentor(user().getId());
		}

		return new ArrayList<DetalhesAvaliarFuncionario>();
	}

	@Override
	@Transactional(readOnly = true)
	public InfoAvaliarResponse obterInfoAvaliacao(Long avaliacaoId) {
		InfoAvaliarResponse info = new InfoAvaliarResponse();
		info.setIdAvaliacao(avaliacaoId);
		info.setRessalvasAvaliacaoAnterior(avaliacaoProfissionalRepository.obterRessalvasAvaliacaoAnterior(avaliacaoId));
		info.setCriterios(criterioAvaliacaoRepository.listarCriteriosPorAvaliacao(avaliacaoId));
		info.setPesos(ValorCriterio.toProperties());

		if (hasRole(RoleName.ROLE_GERENTE)) {
			Optional<AvaliacaoProfissional> avaliacaoOpt = avaliacaoProfissionalRepository.findById(avaliacaoId);
			if (avaliacaoOpt.isPresent()) {
				AvaliacaoProfissional avaliacao = avaliacaoOpt.get();
				if (avaliacao.getBpAvaliador() != null) {
					info.setNomeBp(avaliacao.getBpAvaliador().getNome());
					info.setNotaBp(avaliacao.getCriteriosAvaliacao().stream().mapToDouble(CriterioAvaliacao::calcularNotaBp).sum());
					info.setComentariosBp(avaliacao.getComentariosBp());
				}
				if (avaliacao.getMentorAvaliador() != null) {
					info.setNomeMentor(avaliacao.getMentorAvaliador().getNome());
					info.setNotaMentor(avaliacao.getCriteriosAvaliacao().stream().mapToDouble(CriterioAvaliacao::calcularNotaMentor).sum());
					info.setComentariosMentor(avaliacao.getComentariosMentor());
				}
			}
		}
		return info;
	}

	@Override
	@Transactional
	public AvaliacaoProfissional avaliarFuncionario(AvaliacaoForm form) throws AvaliacaoException {

		Optional<Funcionario> funcionarioOpt = funcionarioRepository.obterFuncionarioPorUsuarioID(user().getId());
		if (!funcionarioOpt.isPresent() || (!hasRole(RoleName.ROLE_MENTOR) && !hasRole(RoleName.ROLE_BP))) {
			throw new AvaliacaoException("Usuário inválido para avaliação!");
		}
		Optional<AvaliacaoProfissional> avaliacaoOpt = avaliacaoProfissionalRepository.findById(form.getIdAvaliacao());
		if (!avaliacaoOpt.isPresent() || !avaliacaoOpt.get().getStatus().equals(StatusAvaliacao.AGUARDANDO_AVALIACAO)) {
			throw new AvaliacaoException("Avaliação indisponível para avaliação!");
		}

		AvaliacaoProfissional avaliacao = avaliacaoOpt.get();
		Funcionario funcionarioAvaliador = funcionarioOpt.get();

		List<Long> criteriosId = avaliacao.getCriteriosAvaliacao().stream().map(CriterioAvaliacao::getId).collect(Collectors.toList());
		List<Long> formCriteriosId = form.getCriterios().stream().map(CriterioAvaliacaoDto::getIdCriterio).collect(Collectors.toList());
		if (criteriosId.size() != formCriteriosId.size() || !criteriosId.containsAll(formCriteriosId)) {
			throw new AvaliacaoException("Todos os critérios da avaliação devem ser informados!");
		}
		if (funcionarioAvaliador == avaliacao.getFuncionario()) {
			throw new AvaliacaoException("O funcionário autenticado não pode se avaliar!");
		}

		try {
			if (hasRole(RoleName.ROLE_MENTOR)) {
				if (funcionarioAvaliador != avaliacao.getFuncionario().getMentor()) {
					throw new AvaliacaoException("Apenas o mentor do funcionário pode realizar esta avaliação!");
				}
				avaliacao.setDataAvaliacaoMentor(new Date());
				avaliacao.setMentorAvaliador(funcionarioAvaliador);
				avaliacao.setComentariosMentor(form.getComentarios());
				for (CriterioAvaliacaoDto dto : form.getCriterios()) {
					Optional<CriterioAvaliacao> opt = avaliacao.getCriteriosAvaliacao().stream().filter(c -> c.getId().compareTo(dto.getIdCriterio()) == 0).findFirst();
					if (opt.isPresent() && dto.getNota() != null) {
						opt.get().setNotaMentor(dto.getNota());
					} else {
						throw new AvaliacaoException("Avaliação inconsistente! Um do critérios desta avaliação foi satisfeito!");
					}
				}
				if (avaliacao.getBpAvaliador() != null) {
					avaliacao.setStatus(StatusAvaliacao.AGUARDANDO_APROVACAO);
				}
			} else if (hasRole(RoleName.ROLE_BP)) {
				avaliacao.setDataAvaliacaoBp(new Date());
				avaliacao.setBpAvaliador(funcionarioAvaliador);
				avaliacao.setComentariosBp(form.getComentarios());
				for (CriterioAvaliacaoDto dto : form.getCriterios()) {
					Optional<CriterioAvaliacao> opt = avaliacao.getCriteriosAvaliacao().stream().filter(c -> c.getId().compareTo(dto.getIdCriterio()) == 0).findFirst();
					if (opt.isPresent() && dto.getNota() != null) {
						opt.get().setNotaBp(dto.getNota());
					} else {
						throw new AvaliacaoException("Avaliação inconsistente! Um do critérios desta avaliação foi satisfeito!");
					}
				}
				if (avaliacao.getMentorAvaliador() != null || avaliacao.getFuncionario().getMentor() == null) {
					avaliacao.setStatus(StatusAvaliacao.AGUARDANDO_APROVACAO);
				}
			}

			return avaliacaoProfissionalRepository.saveAndFlush(avaliacao);

		} catch (AvaliacaoException ae) {
			throw new AvaliacaoException(ae.getMessage());

		} catch (Exception e) {
			throw new AvaliacaoException("Ocorreu um erro ao realizar a avaliação do funcionário!");
		}
	}

	@Override
	@Transactional
	public AvaliacaoProfissional aprovarAvaliacaoFuncionario(AvaliacaoForm form) throws AvaliacaoException {

		Optional<Funcionario> funcionarioOpt = funcionarioRepository.obterFuncionarioPorUsuarioID(user().getId());
		if (!funcionarioOpt.isPresent() || !hasRole(RoleName.ROLE_GERENTE)) {
			throw new AvaliacaoException("Usuário inválido para aprovação!!");
		}
		Optional<AvaliacaoProfissional> avaliacaoOpt = avaliacaoProfissionalRepository.findById(form.getIdAvaliacao());
		if (!avaliacaoOpt.isPresent() || !avaliacaoOpt.get().getStatus().equals(StatusAvaliacao.AGUARDANDO_APROVACAO)) {
			throw new AvaliacaoException("Avaliação indisponível para aprovação!");
		}

		AvaliacaoProfissional avaliacao = avaliacaoOpt.get();
		Funcionario funcionario = avaliacao.getFuncionario();
		Funcionario funcionarioAvaliador = funcionarioOpt.get();

		if (funcionarioAvaliador == avaliacao.getFuncionario()) {
			throw new AvaliacaoException("O funcionário autenticado não pode aprovar sua avaliação!");
		}
		if (funcionarioAvaliador != avaliacao.getFuncionario().getCentroCusto().getGerente()) {
			throw new AvaliacaoException("A aprovação da avaliação do funcionário deve ser realizada apenas pelo seu gerente!");
		}

		try {
			Double sum = avaliacao.getCriteriosAvaliacao().stream().mapToDouble(CriterioAvaliacao::calcularNota).sum();
			avaliacao.setNotaFinal(sum);
			avaliacao.setDataAprovacaoGerente(new Date());
			avaliacao.setGerenteAprovador(funcionarioAvaliador);
			avaliacao.setComentariosGerente(form.getComentarios());
			avaliacao.setRessalvasGerente(form.getRessalvas());
			avaliacao.setStatus(StatusAvaliacao.FINALIZADO);

			avaliacaoProfissionalRepository.saveAndFlush(avaliacao);

			if ((sum >= PERCENT_APROV_PROX_NIVEL) && funcionario.getNivel() < 5 && StringUtils.isEmpty(form.getRessalvas())) {
				funcionario.setNivel((1 + funcionario.getNivel()));
				funcionarioRepository.saveAndFlush(funcionario);
			}

			return avaliacao;

		} catch (Exception e) {
			throw new AvaliacaoException("Ocorreu um erro ao realizar a aprovação da avaliação do funcionário!");
		}
	}

}
