package com.stefanini.application.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.stefanini.application.domain.entity.Role;
import com.stefanini.application.domain.entity.User;
import com.stefanini.application.domain.enums.RoleName;
import com.stefanini.application.security.services.UserPrinciple;

public abstract class BaseServiceTest {

	protected void mockUser(Long id, RoleName roleName) {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(roleName));

		User user = new User();
		user.setId(id);
		user.setUsername("fake-user");
		user.setRoles(roles);

		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		UserPrinciple principle = new UserPrinciple(user.getId(), user.getName(), user.getUsername(), user.getEmail(),
				user.getPassword(), authorities);

		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(principle, "", authorities));
	}

}
