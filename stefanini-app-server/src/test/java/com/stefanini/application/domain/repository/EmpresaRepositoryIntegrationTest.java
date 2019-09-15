package com.stefanini.application.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.stefanini.application.domain.entity.Empresa;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class EmpresaRepositoryIntegrationTest {

	private String CNPJ_STEFANINI = "58069360000120";

	@Autowired
	private EmpresaRepository empresaRepository;

	@Test
	public void testFindByCnpj() {
		Optional<Empresa> empresaOpt = empresaRepository.findByCnpj(CNPJ_STEFANINI);

		assertTrue(empresaOpt.isPresent());

		if (empresaOpt.isPresent()) {
			assertThat(empresaOpt.get().getNomeFantasia()).isEqualTo("Stefanini IT Solutions");
		}
	}

}
