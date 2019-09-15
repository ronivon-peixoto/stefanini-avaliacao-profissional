package com.stefanini.application.domain.repository;

import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.stefanini.application.domain.entity.Role;
import com.stefanini.application.domain.enums.RoleName;

@RunWith(SpringRunner.class)
@DataJpaTest
@ActiveProfiles("test")
public class RoleRepositoryIntegrationTest {

	@Autowired
	private RoleRepository roleRepository;

	@Test
	public void testFindByName() {
		Optional<Role> roleOpt = roleRepository.findByName(RoleName.ROLE_BP);
		assertTrue(roleOpt.isPresent());
	}

}
