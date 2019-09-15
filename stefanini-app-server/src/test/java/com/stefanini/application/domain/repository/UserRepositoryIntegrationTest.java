package com.stefanini.application.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.stefanini.application.domain.entity.User;
import com.stefanini.application.domain.enums.RoleName;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryIntegrationTest {

	private String USERNAME_GERENTE = "gerente.pedro";
	private String USERNAME_MENTOR = "mentor.antonella";
	private String EMAIL_DESENVOLVEDOR = "mentor.antonella@stefanini.com";

	@Autowired
	private UserRepository userRepository;

	@Test
	public void testFindByUsername() {
		Optional<User> gerenteUserOpt = userRepository.findByUsername(USERNAME_GERENTE);
		assertTrue(gerenteUserOpt.isPresent());

		if (gerenteUserOpt.isPresent()) {
			assertThat(gerenteUserOpt.get().getName()).isEqualTo("Pedro dos Santos");
			assertThat(gerenteUserOpt.get().getRoles().stream().map(r -> r.getName())).contains(RoleName.ROLE_GERENTE);
		}
	}

	@Test
	public void testExistsByUsername() {
		Boolean existsMentor = userRepository.existsByUsername(USERNAME_MENTOR);
		assertTrue(existsMentor);

		Boolean existsOutroUsername = userRepository.existsByUsername("USERNAME INEXISTENTE");
		assertFalse(existsOutroUsername);
	}

	@Test
	public void testExistsByEmail() {
		Boolean existsDev = userRepository.existsByEmail(EMAIL_DESENVOLVEDOR);
		assertTrue(existsDev);

		Boolean existsOutroDev = userRepository.existsByEmail("dev@stefanini.com");
		assertFalse(existsOutroDev);
	}

}
