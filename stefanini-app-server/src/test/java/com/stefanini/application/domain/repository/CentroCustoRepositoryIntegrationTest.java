package com.stefanini.application.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.stefanini.application.domain.entity.CentroCusto;
import com.stefanini.application.domain.entity.Empresa;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class CentroCustoRepositoryIntegrationTest {

	private Long ID_STEFANINI = 1L;
	private Optional<Empresa> empresaOpt;
	private String NOME_CENTRO_CUSTO_SALVADOR = "Stefanini - Salvador-Ba";

	@Autowired
	private EmpresaRepository empresaRepository;

	@Autowired
	private CentroCustoRepository centroCustoRepository;

	@Before
	public void setUp() throws Exception {
		empresaOpt = empresaRepository.findById(ID_STEFANINI);
	}

	@Test
	public void testFindByEmpresa() {
		assertTrue(empresaOpt.isPresent());

		List<CentroCusto> centrosCusto = centroCustoRepository.findByEmpresa(empresaOpt.get());

		assertThat(centrosCusto.size()).isEqualTo(1);
		assertThat(centrosCusto.get(0).getNome()).isEqualTo(NOME_CENTRO_CUSTO_SALVADOR);
	}

}
