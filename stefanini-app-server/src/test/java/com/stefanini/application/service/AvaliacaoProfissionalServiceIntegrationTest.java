package com.stefanini.application.service;

import static com.stefanini.application.domain.enums.ValorCriterio.ATENDE;
import static com.stefanini.application.domain.enums.ValorCriterio.ATENDE_PARCIALMENTE;
import static com.stefanini.application.domain.enums.ValorCriterio.SUPERA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.stefanini.application.domain.dto.CriterioAvaliacaoDto;
import com.stefanini.application.domain.dto.DetalhesAvaliarFuncionario;
import com.stefanini.application.domain.entity.AvaliacaoProfissional;
import com.stefanini.application.domain.entity.CriterioAvaliacao;
import com.stefanini.application.domain.entity.Funcionario;
import com.stefanini.application.domain.entity.ValorEmpresa;
import com.stefanini.application.domain.enums.Cargo;
import com.stefanini.application.domain.enums.RoleName;
import com.stefanini.application.domain.enums.StatusAvaliacao;
import com.stefanini.application.domain.enums.ValorCriterio;
import com.stefanini.application.domain.repository.AvaliacaoProfissionalRepository;
import com.stefanini.application.domain.repository.FuncionarioRepository;
import com.stefanini.application.domain.repository.ValorEmpresaRepository;
import com.stefanini.application.message.request.AvaliacaoForm;
import com.stefanini.application.message.response.InfoAvaliarResponse;
import com.stefanini.application.message.response.InfoFuncionarioResponse;
import com.stefanini.application.service.exception.AvaliacaoException;
import com.stefanini.application.service.impl.AvaliacaoProfissionalServiceImpl;
import com.stefanini.application.service.impl.FuncionarioServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class AvaliacaoProfissionalServiceIntegrationTest extends BaseServiceTest {

	@TestConfiguration
	static class FuncionarioServiceIntegrationTestContextConfiguration {
		@Bean
		public AvaliacaoProfissionalService avaliacaoProfissionalService() {
			return new AvaliacaoProfissionalServiceImpl();
		}
		@Bean
		public FuncionarioService funcionarioService() {
			return new FuncionarioServiceImpl();
		}
	}

	private Long GERENTE_USER_1 = 1L;
	private Long BP_USER_2 = 2L;
	private Long MENTOR1_USER_3 = 3L;
	private Long MENTOR2_USER_4 = 4L;
	private Long FUNCIONARIO_BP_USER_2 = 2L;
	private Long FUNCIONARIO_10 = 10L;

	@Autowired
	private AvaliacaoProfissionalService avaliacaoProfissionalService;

	@Autowired
	private FuncionarioService funcionarioService;

	@Autowired
	private AvaliacaoProfissionalRepository avaliacaoProfissionalRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Autowired
	private ValorEmpresaRepository valorEmpresaRepository;

	@Test
	public void testProcessarNovasAvaliacoes() throws AvaliacaoException {
		Integer numAvaliacoes = avaliacaoProfissionalService.processarNovasAvaliacoes();
		assertThat(numAvaliacoes).isEqualTo(17);
	}

	@Test
	public void testListarAvaliacoesPendentesVotacao() throws AvaliacaoException {
		Integer numAvaliacoes = avaliacaoProfissionalService.processarNovasAvaliacoes();
		assertThat(numAvaliacoes).isEqualTo(17);

		mockUser(BP_USER_2, RoleName.ROLE_BP);
		List<DetalhesAvaliarFuncionario> avaliacoes = avaliacaoProfissionalService.listarAvaliacoesPendentesVotacao();
		assertThat(avaliacoes.size()).isEqualTo(17);

		mockUser(MENTOR1_USER_3, RoleName.ROLE_MENTOR);
		List<DetalhesAvaliarFuncionario> avaliacoesMentor1 = avaliacaoProfissionalService.listarAvaliacoesPendentesVotacao();
		assertThat(avaliacoesMentor1.size()).isEqualTo(7);

		mockUser(MENTOR2_USER_4, RoleName.ROLE_MENTOR);
		List<DetalhesAvaliarFuncionario> avaliacoesMentor2 = avaliacaoProfissionalService.listarAvaliacoesPendentesVotacao();
		assertThat(avaliacoesMentor2.size()).isEqualTo(8);

		mockUser(GERENTE_USER_1, RoleName.ROLE_GERENTE);
		List<DetalhesAvaliarFuncionario> avaliacoesGerente = avaliacaoProfissionalService.listarAvaliacoesPendentesVotacao();
		assertThat(avaliacoesGerente.size()).isEqualTo(0); // <!-- Só aparece para aprovação após a votação (BP, Mentor)
	}
	
	@Test(expected = AvaliacaoException.class)
	public void testAvaliarFuncionario_ExcecaoUsuarioSemAutorizacao() throws Exception {

		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(FUNCIONARIO_10);
		assertTrue(funcionarioOpt.isPresent());
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			assertThat(funcionario.getCpf()).isEqualTo("84745285850");
			assertThat(funcionario.getNivel()).isEqualTo(2);
			assertThat(funcionario.getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR);
		}

		AvaliacaoProfissional avaliacao = criarAvaliacao(FUNCIONARIO_10);

		mockUser(BP_USER_2, RoleName.ROLE_FUNCIONARIO); // <!-- Apenas as roles MENTOR e BP podem avaliar.

		InfoAvaliarResponse info = avaliacaoProfissionalService.obterInfoAvaliacao(avaliacao.getId());
		assertNotNull(info);
		assertNull(info.getNotaBp());
		assertNull(info.getNotaMentor());
		assertThat(info.getCriterios().size()).isEqualTo(7);
		assertThat(info.getPesos().size()).isEqualTo(4);

		avaliacao = avaliar(avaliacao.getId(), "", "", info.getCriterios(), SUPERA, ATENDE_PARCIALMENTE, SUPERA, ATENDE, SUPERA, SUPERA, ATENDE);
	}

	@Test(expected = AvaliacaoException.class)
	public void testAvaliarFuncionario_ExcecaoAvaliacaoIndisponivel() throws Exception {

		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(FUNCIONARIO_10);
		assertTrue(funcionarioOpt.isPresent());
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			assertThat(funcionario.getCpf()).isEqualTo("84745285850");
			assertThat(funcionario.getNivel()).isEqualTo(2);
			assertThat(funcionario.getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR);
		}

		// <!-- Não foi criada a avaliação para o funcionário.

		mockUser(BP_USER_2, RoleName.ROLE_BP);

		avaliar(100L, "", "", null, SUPERA, ATENDE_PARCIALMENTE, SUPERA, ATENDE, SUPERA, SUPERA, ATENDE);
	}

	@Test(expected = AvaliacaoException.class)
	public void testAvaliarFuncionario_ExcecaoCriteriosInvalidos() throws Exception {

		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(FUNCIONARIO_10);
		assertTrue(funcionarioOpt.isPresent());
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			assertThat(funcionario.getCpf()).isEqualTo("84745285850");
			assertThat(funcionario.getNivel()).isEqualTo(2);
			assertThat(funcionario.getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR);
		}

		AvaliacaoProfissional avaliacao = criarAvaliacao(FUNCIONARIO_10);

		mockUser(BP_USER_2, RoleName.ROLE_BP);

		InfoAvaliarResponse info = avaliacaoProfissionalService.obterInfoAvaliacao(avaliacao.getId());
		assertNotNull(info);
		assertNull(info.getNotaBp());
		assertNull(info.getNotaMentor());
		assertThat(info.getCriterios().size()).isEqualTo(7);
		assertThat(info.getPesos().size()).isEqualTo(4);
		info.getCriterios().remove(0); // <!-- Foi removido um dos critérios.
		assertThat(info.getCriterios().size()).isEqualTo(6);

		avaliacao = avaliar(avaliacao.getId(), "", "", info.getCriterios(), ATENDE, ATENDE_PARCIALMENTE, SUPERA, SUPERA, SUPERA, ATENDE);
	}

	@Test(expected = AvaliacaoException.class)
	public void testAvaliarFuncionario_ExcecaoFuncionarioIgualAoAvaliador() throws Exception {

		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(FUNCIONARIO_10);
		assertTrue(funcionarioOpt.isPresent());
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			assertThat(funcionario.getCpf()).isEqualTo("84745285850");
			assertThat(funcionario.getNivel()).isEqualTo(2);
			assertThat(funcionario.getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR);
		}

		AvaliacaoProfissional avaliacao = criarAvaliacao(FUNCIONARIO_BP_USER_2); // <!-- Mesmo usuário avaliador (B.P.)

		mockUser(BP_USER_2, RoleName.ROLE_BP);

		InfoAvaliarResponse info = avaliacaoProfissionalService.obterInfoAvaliacao(avaliacao.getId());
		assertNotNull(info);
		assertNull(info.getNotaBp());
		assertNull(info.getNotaMentor());
		assertThat(info.getCriterios().size()).isEqualTo(7);
		assertThat(info.getPesos().size()).isEqualTo(4);

		avaliacao = avaliar(avaliacao.getId(), "", "", info.getCriterios(), SUPERA, ATENDE_PARCIALMENTE, SUPERA, ATENDE, SUPERA, SUPERA, ATENDE);
	}

	@Test(expected = AvaliacaoException.class)
	public void testAvaliarFuncionario_ExcecaoNaoCorrespondeAoMentorDoFuncionario() throws Exception {

		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(FUNCIONARIO_10);
		assertTrue(funcionarioOpt.isPresent());
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			assertThat(funcionario.getCpf()).isEqualTo("84745285850");
			assertThat(funcionario.getNivel()).isEqualTo(2);
			assertThat(funcionario.getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR);
		}

		AvaliacaoProfissional avaliacao = criarAvaliacao(FUNCIONARIO_10);

		mockUser(MENTOR2_USER_4, RoleName.ROLE_MENTOR); // <!-- Não é o mentor do funcionário.

		InfoAvaliarResponse info = avaliacaoProfissionalService.obterInfoAvaliacao(avaliacao.getId());
		assertNotNull(info);
		assertNull(info.getNotaBp());
		assertNull(info.getNotaMentor());
		assertThat(info.getCriterios().size()).isEqualTo(7);
		assertThat(info.getPesos().size()).isEqualTo(4);

		avaliacao = avaliar(avaliacao.getId(), "", "", info.getCriterios(), ATENDE, SUPERA, SUPERA, ATENDE, SUPERA, SUPERA, SUPERA);
	}

	@Test(expected = AvaliacaoException.class)
	public void testAvaliarFuncionario_ExcecaoValoresCriteriosNulos() throws Exception {

		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(FUNCIONARIO_10);
		assertTrue(funcionarioOpt.isPresent());
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			assertThat(funcionario.getCpf()).isEqualTo("84745285850");
			assertThat(funcionario.getNivel()).isEqualTo(2);
			assertThat(funcionario.getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR);
		}

		AvaliacaoProfissional avaliacao = criarAvaliacao(FUNCIONARIO_10);

		mockUser(MENTOR1_USER_3, RoleName.ROLE_MENTOR);

		InfoAvaliarResponse info = avaliacaoProfissionalService.obterInfoAvaliacao(avaliacao.getId());
		assertNotNull(info);
		assertNull(info.getNotaBp());
		assertNull(info.getNotaMentor());
		assertThat(info.getCriterios().size()).isEqualTo(7);
		assertThat(info.getPesos().size()).isEqualTo(4);

		// Passando valor nulos para os critérios de avaliação -->
		avaliacao = avaliar(avaliacao.getId(), "", "", info.getCriterios(), ATENDE, SUPERA, null, ATENDE, SUPERA, SUPERA, SUPERA);
	}

	@Test
	public void testAvaliarFuncionarioFluxoCompleto() throws Exception {

		// ESTADO INICIAL DO FUNCIONÁRIO ************************************************
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(FUNCIONARIO_10);
		assertTrue(funcionarioOpt.isPresent());
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			assertThat(funcionario.getCpf()).isEqualTo("84745285850");
			assertThat(funcionario.getNivel()).isEqualTo(2);
			assertThat(funcionario.getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR);

			Optional<InfoFuncionarioResponse> infoIniFuncionario = funcionarioService.obterDetalhesFuncionarioPorId(FUNCIONARIO_10);
			assertTrue(infoIniFuncionario.isPresent());
			if (infoIniFuncionario.isPresent()) {
				assertThat(infoIniFuncionario.get().getDetalhesAvaliacoes().size()).isEqualTo(0);
			}
		}

		// CRIAÇÃO DA AVALIAÇÃO *********************************************************
		AvaliacaoProfissional avaliacao = criarAvaliacao(FUNCIONARIO_10);

		// AVALIAÇÃO DO BUSINESS PARTNER ************************************************
		mockUser(BP_USER_2, RoleName.ROLE_BP);

		InfoAvaliarResponse info = avaliacaoProfissionalService.obterInfoAvaliacao(avaliacao.getId());
		assertNotNull(info);
		assertNull(info.getNotaBp());
		assertNull(info.getNotaMentor());
		assertThat(info.getCriterios().size()).isEqualTo(7);
		assertThat(info.getPesos().size()).isEqualTo(4);

		avaliacao = avaliar(avaliacao.getId(), "", "", info.getCriterios(), SUPERA, ATENDE_PARCIALMENTE, SUPERA, ATENDE, SUPERA, SUPERA, ATENDE);
		assertNotNull(avaliacao);
		assertThat(avaliacao.getCriteriosAvaliacao().stream().mapToDouble(CriterioAvaliacao::calcularNotaBp).sum()).isEqualTo(87.5);
		assertThat(avaliacao.getCriteriosAvaliacao().stream().mapToDouble(CriterioAvaliacao::calcularNotaMentor).sum()).isEqualTo(0.0);
		assertThat(avaliacao.getNotaFinal()).isEqualTo(0.0);
		assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.AGUARDANDO_AVALIACAO);

		// AVALIAÇÃO DO MENTOR DO FUNCIONAIO ********************************************
		mockUser(MENTOR1_USER_3, RoleName.ROLE_MENTOR);

		info = avaliacaoProfissionalService.obterInfoAvaliacao(avaliacao.getId());
		assertNotNull(info);
		assertNull(info.getNotaBp()); // não exibe nota ao outro votante.
		assertNull(info.getNotaMentor());
		assertThat(info.getCriterios().size()).isEqualTo(7);
		assertThat(info.getPesos().size()).isEqualTo(4);

		avaliacao = avaliar(avaliacao.getId(), "", "", info.getCriterios(), ATENDE, SUPERA, SUPERA, ATENDE, SUPERA, SUPERA, SUPERA);
		assertNotNull(avaliacao);
		assertThat(avaliacao.getCriteriosAvaliacao().stream().mapToDouble(CriterioAvaliacao::calcularNotaBp).sum()).isEqualTo(87.5);
		assertThat(avaliacao.getCriteriosAvaliacao().stream().mapToDouble(CriterioAvaliacao::calcularNotaMentor).sum()).isEqualTo(95.0);
		assertThat(avaliacao.getNotaFinal()).isEqualTo(0.0);
		assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.AGUARDANDO_APROVACAO);

		// APROVAÇÃO DO GERENTE *********************************************************
		mockUser(GERENTE_USER_1, RoleName.ROLE_GERENTE);

		info = avaliacaoProfissionalService.obterInfoAvaliacao(avaliacao.getId());
		assertNotNull(info);
		assertThat(info.getNotaBp()).isEqualTo(87.5);
		assertThat(info.getNotaMentor()).isEqualTo(95.0);
		assertThat(info.getCriterios().size()).isEqualTo(7);
		assertThat(info.getPesos().size()).isEqualTo(4);

		AvaliacaoForm form = new AvaliacaoForm(avaliacao.getId(), "", "Parabéns!");
		avaliacao = avaliacaoProfissionalService.aprovarAvaliacaoFuncionario(form);
		assertNotNull(avaliacao);
		assertThat(avaliacao.getStatus()).isEqualTo(StatusAvaliacao.FINALIZADO);
		assertThat(avaliacao.getCriteriosAvaliacao().stream().mapToDouble(CriterioAvaliacao::calcularNotaBp).sum()).isEqualTo(87.5);
		assertThat(avaliacao.getCriteriosAvaliacao().stream().mapToDouble(CriterioAvaliacao::calcularNotaMentor).sum()).isEqualTo(95.0);
		assertThat(avaliacao.getNotaFinal()).isEqualTo(91.25);

		// VERIFICAÇÃO DO ESTADO ATUAL DO FUNCIONÁRIO ***********************************
		funcionarioOpt = funcionarioRepository.findById(FUNCIONARIO_10);
		assertTrue(funcionarioOpt.isPresent());
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			assertThat(funcionario.getCpf()).isEqualTo("84745285850");
			assertThat(funcionario.getNivel()).isEqualTo(3); // nível 2 --> nível 3
			assertThat(funcionario.getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR);
		}

		// PROMOÇÃO DO FUNCIONÁRIO ******************************************************
		Boolean result = funcionarioService.promoverFuncionario(FUNCIONARIO_10);
		assertTrue(result);

		// VERIFICAÇÃO FINAL DO FUNCIONÁRIO *********************************************
		funcionarioOpt = funcionarioRepository.findById(FUNCIONARIO_10);
		assertTrue(funcionarioOpt.isPresent());
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			assertThat(funcionario.getCpf()).isEqualTo("84745285850");
			assertThat(funcionario.getNivel()).isEqualTo(3);
			assertThat(funcionario.getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_PLENO);

			Optional<InfoFuncionarioResponse> infoFuncionario = funcionarioService.obterDetalhesFuncionarioPorId(FUNCIONARIO_10);
			assertTrue(infoFuncionario.isPresent());
			if (infoFuncionario.isPresent()) {
				assertThat(infoFuncionario.get().getDetalhesAvaliacoes().size()).isEqualTo(1);
			}
		}
	}

	private AvaliacaoProfissional criarAvaliacao(Long funcionarioId) {
		Optional<Funcionario> funcionarioOpt = funcionarioRepository.findById(funcionarioId);
		if (funcionarioOpt.isPresent()) {
			Funcionario funcionario = funcionarioOpt.get();
			AvaliacaoProfissional avaliacao = new AvaliacaoProfissional();
			avaliacao.setStatus(StatusAvaliacao.AGUARDANDO_AVALIACAO);
			avaliacao.setFuncionario(funcionario);
			List<ValorEmpresa> valores = valorEmpresaRepository.findAllByEmpresa(funcionario.getEmpresa());
			for (ValorEmpresa valorEmpresa : valores) {
				avaliacao.addCriterioAvaliacao(new CriterioAvaliacao(valorEmpresa));
			}
			return avaliacaoProfissionalRepository.save(avaliacao);
		}
		return null;
	}

	private AvaliacaoProfissional avaliar(Long avaliavaoId, String comentarios, String ressalvas, List<CriterioAvaliacaoDto> criterios, ValorCriterio... valoresCriterios) throws AvaliacaoException {
		AvaliacaoForm form = new AvaliacaoForm();
		form.setIdAvaliacao(avaliavaoId);
		form.setComentarios(comentarios);
		form.setRessalvas(ressalvas);
		if (criterios != null) {
			for (int i = 0; i < criterios.size(); i++) {
				criterios.get(i).setNota(valoresCriterios[i]);
			}
			form.setCriterios(criterios);
		}
		return avaliacaoProfissionalService.avaliarFuncionario(form);
	}
}
