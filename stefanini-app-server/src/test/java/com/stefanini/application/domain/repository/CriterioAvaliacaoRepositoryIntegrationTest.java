package com.stefanini.application.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.stefanini.application.domain.dto.CriterioAvaliacaoDto;
import com.stefanini.application.service.AvaliacaoProfissionalService;
import com.stefanini.application.service.exception.AvaliacaoException;
import com.stefanini.application.service.impl.AvaliacaoProfissionalServiceImpl;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CriterioAvaliacaoRepositoryIntegrationTest {

	@TestConfiguration
	static class CriterioAvaliacaoRepositoryIntegrationTestContextConfiguration {
		@Bean
		public AvaliacaoProfissionalService avaliacaoProfissionalService() {
			return new AvaliacaoProfissionalServiceImpl();
		}
	}

	@Autowired
	private AvaliacaoProfissionalService avaliacaoProfissionalService;

	@Autowired
	private CriterioAvaliacaoRepository criterioAvaliacaoRepository;

	@Test
	public void testListarCriteriosPorAvaliacao() throws AvaliacaoException {
		Integer numAvaliacoes = avaliacaoProfissionalService.processarNovasAvaliacoes();
		assertThat(numAvaliacoes).isGreaterThan(0);

		List<CriterioAvaliacaoDto> criterios = criterioAvaliacaoRepository.listarCriteriosPorAvaliacao(1L);
		assertThat(criterios.size()).isEqualTo(7);
	}

}
