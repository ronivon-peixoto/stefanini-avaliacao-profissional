package com.stefanini.application.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.stefanini.application.domain.dto.DetalhesFuncionario;
import com.stefanini.application.domain.entity.Funcionario;
import com.stefanini.application.domain.enums.RoleName;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class FuncionarioRepositoryIntegrationTest {

	private String CNPJ_STEFANINI = "58069360000120";
	private Integer INTERVALO_MESES_AVALIAR_FUNCIONARIO = 0;
	private Long GERENTE_USER_ID = 1L;
	private Long BP_USER_ID = 2L;
	private Long MENTOR_1_USER_ID = 3L;
	private Long MENTOR_2_USER_ID = 4L;
	private Long DEV_LUCIANA_ID = 19L;

	@Autowired
	private FuncionarioRepository funcionarioRepository;

	@Test
	public void testListarDetalhesFuncionariosGerente() {
		List<DetalhesFuncionario> funcionarios = funcionarioRepository.listarDetalhesFuncionariosGerente(GERENTE_USER_ID);
		assertThat(funcionarios.size()).isEqualTo(20);
	}

	@Test
	public void testListarDetalhesFuncionariosBP() {
		List<DetalhesFuncionario> funcionarios = funcionarioRepository.listarDetalhesFuncionariosBP(BP_USER_ID);
		assertThat(funcionarios.size()).isEqualTo(20);
	}

	@Test
	public void testListarDetalhesFuncionariosMentor() {
		List<DetalhesFuncionario> listaMentor1 = funcionarioRepository.listarDetalhesFuncionariosMentor(MENTOR_1_USER_ID);
		assertThat(listaMentor1.size()).isEqualTo(7);

		List<DetalhesFuncionario> funcionarios = funcionarioRepository.listarDetalhesFuncionariosMentor(MENTOR_2_USER_ID);
		assertThat(funcionarios.size()).isEqualTo(8);
	}

	@Test
	public void testObterDetalhesFuncionario() {
		Optional<DetalhesFuncionario> detalhesOpt = funcionarioRepository.obterDetalhesFuncionario(DEV_LUCIANA_ID);
		assertTrue(detalhesOpt.isPresent());

		if (detalhesOpt.isPresent()) {
			assertThat(detalhesOpt.get().getCpf()).isEqualTo("88146621775");
		}
	}

	@Test
	public void testObterDetalhesFuncionarioPorUsuarioId() {
		Optional<DetalhesFuncionario> detalhesUsuario = funcionarioRepository.obterDetalhesFuncionarioPorUsuarioId(GERENTE_USER_ID);
		assertTrue(detalhesUsuario.isPresent());

		if (detalhesUsuario.isPresent()) {
			assertThat(detalhesUsuario.get().getCpf()).isEqualTo("17632287205");
		}
	}

	@Test
	public void testObterFuncionariosParaAvaliavao() {
		List<Funcionario> listaFuncionariosAvaliar = funcionarioRepository.obterFuncionariosParaAvaliavao(INTERVALO_MESES_AVALIAR_FUNCIONARIO);
		assertThat(listaFuncionariosAvaliar.size()).isEqualTo(17);

		Long count = listaFuncionariosAvaliar.stream().filter(f -> f.getEmpresa().getCnpj().equals(CNPJ_STEFANINI)).count();
		assertThat(count).isEqualTo(17L);
	}

	@Test
	public void testObterFuncionarioPorUsuarioID() {
		Optional<Funcionario> mentor2 = funcionarioRepository.obterFuncionarioPorUsuarioID(MENTOR_2_USER_ID);
		assertTrue(mentor2.isPresent());

		if (mentor2.isPresent()) {
			assertThat(mentor2.get().getNome()).isEqualTo("Elaine Cláudia Antônia Campos");
			assertThat(mentor2.get().getCpf()).isEqualTo("20078051622");
			assertThat(mentor2.get().getUsuario().getRoles().stream().map(r -> r.getName())).contains(RoleName.ROLE_MENTOR);
		}
	}

}
