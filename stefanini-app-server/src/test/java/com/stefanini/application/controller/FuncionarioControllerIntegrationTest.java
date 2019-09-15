package com.stefanini.application.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.stefanini.application.domain.dto.DetalhesFuncionario;
import com.stefanini.application.message.response.InfoFuncionarioResponse;
import com.stefanini.application.service.FuncionarioService;
import com.stefanini.application.util.projections.DetalhesFuncionarioDto;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = { FuncionarioController.class }, secure = false)
@ActiveProfiles("test")
public class FuncionarioControllerIntegrationTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private FuncionarioService funcionarioService;

	@Test
	public void testListarFuncionarios() throws Exception {

		List<DetalhesFuncionario> funcionarios = new ArrayList<>();
		funcionarios.add(new DetalhesFuncionarioDto(1L, "Pedro dos Santos"));
		funcionarios.add(new DetalhesFuncionarioDto(2L, "Alícia Lorena Nascimento"));
		funcionarios.add(new DetalhesFuncionarioDto(3L, "Antonella Daiane Porto"));

		given(funcionarioService.listarDetalhesFuncionarios()).willReturn(funcionarios);

		mvc.perform(get("/api/v1/funcionarios").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].id", is(1))).andExpect(jsonPath("$[0].nome", is("Pedro dos Santos")))
			.andExpect(jsonPath("$[1].id", is(2))).andExpect(jsonPath("$[1].nome", is("Alícia Lorena Nascimento")))
			.andExpect(jsonPath("$[2].id", is(3))).andExpect(jsonPath("$[2].nome", is("Antonella Daiane Porto")));
	}

	@Test
	public void testObterFuncionario() throws Exception {

		InfoFuncionarioResponse response = new InfoFuncionarioResponse();
		response.setPromover(false);

		given(funcionarioService.obterDetalhesFuncionarioPorId(Mockito.any())).willReturn(Optional.ofNullable(response));

		mvc.perform(get("/api/v1/funcionarios/{id}", 1L)
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.promover", is(false)));
	}

	@Test
	public void testPromoverFuncionarioComSucesso() throws Exception {

		given(funcionarioService.promoverFuncionario(Mockito.anyLong())).willReturn(Boolean.TRUE);

		mvc.perform(put("/api/v1/funcionarios/{id}/promover", 1L)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk());
	}

	@Test
	public void testPromoverFuncionarioInexistenteOuSemNivelAdequadoAoNovoCargo() throws Exception {

		given(funcionarioService.promoverFuncionario(Mockito.anyLong())).willReturn(Boolean.FALSE);

		mvc.perform(put("/api/v1/funcionarios/{id}/promover", 1L)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isBadRequest());
	}

}
