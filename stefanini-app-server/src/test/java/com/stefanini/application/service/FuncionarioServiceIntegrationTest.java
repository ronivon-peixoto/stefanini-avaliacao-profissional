package com.stefanini.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.stefanini.application.domain.dto.DetalhesFuncionario;
import com.stefanini.application.domain.enums.Cargo;
import com.stefanini.application.domain.enums.RoleName;
import com.stefanini.application.message.response.InfoFuncionarioResponse;
import com.stefanini.application.service.impl.FuncionarioServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class FuncionarioServiceIntegrationTest extends BaseServiceTest {

	@TestConfiguration
	static class FuncionarioServiceIntegrationTestContextConfiguration {
		@Bean
		public FuncionarioService funcionarioService() {
			return new FuncionarioServiceImpl();
		}
	}

	private Long GERENTE_USER_ID = 1L;
	private Long BP_USER_ID = 2L;
	private Long BP_FUNCIONARIO_ID = 2L;
	private Long MENTOR1_USER_ID = 3L;
	private Long MENTOR2_USER_ID = 4L;
	private Long FUNCIONARIO1_ID = 13L;
	private Long FUNCIONARIO2_ID = 14L;

	@Autowired
	private FuncionarioService funcionarioService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testListarDetalhesFuncionariosComPerfilGerente() throws Exception {
		mockUser(GERENTE_USER_ID, RoleName.ROLE_GERENTE);
		List<DetalhesFuncionario> listarDetalhes = funcionarioService.listarDetalhesFuncionarios();
		assertThat(listarDetalhes.size()).isEqualTo(20);
	}

	@Test
	public void testListarDetalhesFuncionariosComPerfilBP() throws Exception {
		mockUser(BP_USER_ID, RoleName.ROLE_BP);
		List<DetalhesFuncionario> listarDetalhes = funcionarioService.listarDetalhesFuncionarios();
		assertThat(listarDetalhes.size()).isEqualTo(20);
	}

	@Test
	public void testListarDetalhesFuncionariosComPerfilMentor() throws Exception {
		mockUser(MENTOR1_USER_ID, RoleName.ROLE_MENTOR);
		List<DetalhesFuncionario> listarDetalhesMentor1 = funcionarioService.listarDetalhesFuncionarios();
		assertThat(listarDetalhesMentor1.size()).isEqualTo(7);

		mockUser(MENTOR2_USER_ID, RoleName.ROLE_MENTOR);
		List<DetalhesFuncionario> listarDetalhesMentor2 = funcionarioService.listarDetalhesFuncionarios();
		assertThat(listarDetalhesMentor2.size()).isEqualTo(8);
	}

	@Test
	public void testObterDetalhesFuncionarioPorId() throws Exception {
		Optional<InfoFuncionarioResponse> detalhesFunciOpt = funcionarioService
				.obterDetalhesFuncionarioPorId(BP_FUNCIONARIO_ID);
		assertTrue(detalhesFunciOpt.isPresent());

		if (detalhesFunciOpt.isPresent()) {
			assertThat(detalhesFunciOpt.get().getFuncionario().getCpf()).isEqualTo("20647340828");
			assertThat(detalhesFunciOpt.get().getDetalhesAvaliacoes().size()).isEqualTo(0);
		}
	}

	@Test
	public void testObterDetalhesFuncionarioPorUsuarioId() throws Exception {
		Optional<InfoFuncionarioResponse> detalhesFunciOpt = funcionarioService
				.obterDetalhesFuncionarioPorUsuarioId(BP_USER_ID);
		assertTrue(detalhesFunciOpt.isPresent());

		if (detalhesFunciOpt.isPresent()) {
			assertThat(detalhesFunciOpt.get().getFuncionario().getCpf()).isEqualTo("20647340828");
			assertThat(detalhesFunciOpt.get().getDetalhesAvaliacoes().size()).isEqualTo(0);
		}
	}

	@Test
	public void testPromoverFuncionarioComSucesso() throws Exception {
		Optional<InfoFuncionarioResponse> infoOpt1 = funcionarioService.obterDetalhesFuncionarioPorId(FUNCIONARIO1_ID);
		assertTrue(infoOpt1.isPresent());
		if (infoOpt1.isPresent()) {
			assertThat(infoOpt1.get().getFuncionario().getCpf()).isEqualTo("46683206090");
			assertThat(infoOpt1.get().getFuncionario().getNivel()).isEqualTo(3);
			assertThat(infoOpt1.get().getFuncionario().getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR.getDescricao());
		}

		Boolean sucesso = funcionarioService.promoverFuncionario(FUNCIONARIO1_ID);
		assertThat(sucesso).isEqualTo(Boolean.TRUE);

		Optional<InfoFuncionarioResponse> infoOpt2 = funcionarioService.obterDetalhesFuncionarioPorId(FUNCIONARIO1_ID);
		assertTrue(infoOpt2.isPresent());
		if (infoOpt2.isPresent()) {
			assertThat(infoOpt2.get().getFuncionario().getCpf()).isEqualTo("46683206090");
			assertThat(infoOpt2.get().getFuncionario().getNivel()).isEqualTo(3);
			assertThat(infoOpt2.get().getFuncionario().getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_PLENO.getDescricao());
		}
	}

	@Test
	public void testPromoverFuncionarioComFalha() throws Exception {
		Optional<InfoFuncionarioResponse> infoOpt1 = funcionarioService.obterDetalhesFuncionarioPorId(FUNCIONARIO2_ID);
		assertTrue(infoOpt1.isPresent());
		if (infoOpt1.isPresent()) {
			assertThat(infoOpt1.get().getFuncionario().getCpf()).isEqualTo("33847661051");
			assertThat(infoOpt1.get().getFuncionario().getNivel()).isEqualTo(1);
			assertThat(infoOpt1.get().getFuncionario().getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR.getDescricao());
		}

		Boolean sucesso = funcionarioService.promoverFuncionario(FUNCIONARIO2_ID);
		assertThat(sucesso).isEqualTo(Boolean.FALSE);

		Optional<InfoFuncionarioResponse> infoOpt2 = funcionarioService.obterDetalhesFuncionarioPorId(FUNCIONARIO2_ID);
		assertTrue(infoOpt2.isPresent());
		if (infoOpt2.isPresent()) {
			assertThat(infoOpt2.get().getFuncionario().getCpf()).isEqualTo("33847661051");
			assertThat(infoOpt2.get().getFuncionario().getNivel()).isEqualTo(1);
			assertThat(infoOpt2.get().getFuncionario().getCargo()).isEqualTo(Cargo.DESENVOLVEDOR_JUNIOR.getDescricao());
		}
	}

}
