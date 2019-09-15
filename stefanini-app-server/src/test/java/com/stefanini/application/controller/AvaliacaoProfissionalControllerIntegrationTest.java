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

import org.junit.Before;
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

import com.stefanini.application.domain.dto.CriterioAvaliacaoDto;
import com.stefanini.application.domain.dto.DetalhesAvaliarFuncionario;
import com.stefanini.application.domain.entity.AvaliacaoProfissional;
import com.stefanini.application.message.request.AvaliacaoForm;
import com.stefanini.application.message.response.InfoAvaliarResponse;
import com.stefanini.application.service.AvaliacaoProfissionalService;
import com.stefanini.application.util.projections.DetalhesAvaliarFuncionarioDto;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = { AvaliacaoProfissionalController.class }, secure = false)
@ActiveProfiles("test")
public class AvaliacaoProfissionalControllerIntegrationTest extends BaseControllerTest {

	private String RESSALVA_ANTERIOR = "RESSALVA DA AVALIAÇÃO IMEDIATAMENTE ANTERIOR";

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AvaliacaoProfissionalService avaliacaoProfissionalService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testListarAvaliacoes() throws Exception {

		List<DetalhesAvaliarFuncionario> funcionarios = new ArrayList<>();
		funcionarios.add(new DetalhesAvaliarFuncionarioDto(1L, "62160185230"));
		funcionarios.add(new DetalhesAvaliarFuncionarioDto(2L, "84745285850"));
		funcionarios.add(new DetalhesAvaliarFuncionarioDto(3L, "78821687341"));

		given(avaliacaoProfissionalService.listarAvaliacoesPendentesVotacao()).willReturn(funcionarios);

		mvc.perform(get("/api/v1/avaliacoes")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(3)))
			.andExpect(jsonPath("$[0].idAvaliacao", is(1))).andExpect(jsonPath("$[0].cpfFuncionario", is("62160185230")))
			.andExpect(jsonPath("$[1].idAvaliacao", is(2))).andExpect(jsonPath("$[1].cpfFuncionario", is("84745285850")))
			.andExpect(jsonPath("$[2].idAvaliacao", is(3))).andExpect(jsonPath("$[2].cpfFuncionario", is("78821687341")));
	}

	@Test
	public void testObterCriteriosAvaliacao() throws Exception {

		List<CriterioAvaliacaoDto> criterios = new ArrayList<>();
		criterios.add(new CriterioAvaliacaoDto(1L, ""));

		InfoAvaliarResponse response = new InfoAvaliarResponse();
		response.setIdAvaliacao(1L);
		response.setRessalvasAvaliacaoAnterior(RESSALVA_ANTERIOR);
		response.setCriterios(criterios);

		given(avaliacaoProfissionalService.obterInfoAvaliacao(Mockito.any())).willReturn(response);

		mvc.perform(get("/api/v1/avaliacoes/{id}/criterios", 1L)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.idAvaliacao", is(1)))
			.andExpect(jsonPath("$.ressalvasAvaliacaoAnterior", is(RESSALVA_ANTERIOR)));
	}

	@Test
	public void testAvaliar() throws Exception {

		AvaliacaoForm form = new AvaliacaoForm();
		form.setIdAvaliacao(1L);

		AvaliacaoProfissional avaliacao = new AvaliacaoProfissional();
		avaliacao.setId(1L);

		given(avaliacaoProfissionalService.avaliarFuncionario(Mockito.any())).willReturn(avaliacao);

		mvc.perform(put("/api/v1/avaliacoes/{id}/avaliar", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(form)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", is(1)));
	}

	@Test
	public void testAprovarAvaliacao() throws Exception {

		AvaliacaoForm form = new AvaliacaoForm();
		form.setIdAvaliacao(1L);

		AvaliacaoProfissional avaliacao = new AvaliacaoProfissional();
		avaliacao.setId(1L);

		given(avaliacaoProfissionalService.aprovarAvaliacaoFuncionario(Mockito.any())).willReturn(avaliacao);

		mvc.perform(put("/api/v1/avaliacoes/{id}/aprovar", 1L)
			.contentType(MediaType.APPLICATION_JSON)
			.content(asJsonString(form)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", is(1)));
	}

}
