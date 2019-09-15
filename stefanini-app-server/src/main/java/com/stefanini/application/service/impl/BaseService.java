package com.stefanini.application.service.impl;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import com.stefanini.application.domain.enums.RoleName;
import com.stefanini.application.security.services.UserPrinciple;

public abstract class BaseService {

	protected UserPrinciple user() {
		return (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}

	@SuppressWarnings("unchecked")
	protected Boolean hasRole(RoleName role) {
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		boolean hasRole = false;
		for (GrantedAuthority authority : authorities) {
			hasRole = authority.getAuthority().equals(role.name());
			if (hasRole) {
				break;
			}
		}
		return hasRole;
	}

}
