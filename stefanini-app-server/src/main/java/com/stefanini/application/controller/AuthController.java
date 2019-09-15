package com.stefanini.application.controller;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.application.domain.entity.Role;
import com.stefanini.application.domain.entity.User;
import com.stefanini.application.domain.enums.RoleName;
import com.stefanini.application.domain.repository.RoleRepository;
import com.stefanini.application.domain.repository.UserRepository;
import com.stefanini.application.message.request.LoginForm;
import com.stefanini.application.message.request.SignUpForm;
import com.stefanini.application.message.response.InfoFuncionarioResponse;
import com.stefanini.application.message.response.JwtResponse;
import com.stefanini.application.message.response.ResponseMessage;
import com.stefanini.application.security.jwt.JwtProvider;
import com.stefanini.application.security.services.UserPrinciple;
import com.stefanini.application.service.FuncionarioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtProvider jwtProvider;

	@Autowired
	private FuncionarioService funcionarioService;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generateJwtToken(authentication);
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();

		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getUsername(), userDetails.getAuthorities()));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Falha! -> O nome de usuário já está sendo usado!"),
					HttpStatus.BAD_REQUEST);
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Falha! -> O e-mail já está sendo usado!"),
					HttpStatus.BAD_REQUEST);
		}

		// Creating user's account
		User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<Role> roles = new HashSet<>();
		Role pmRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new RuntimeException("Falha! -> Causa: Perfil do usuário não encontrado."));
		roles.add(pmRole);

		user.setRoles(roles);
		userRepository.save(user);

		return new ResponseEntity<>(new ResponseMessage("Usuário registrado com sucesso!"), HttpStatus.OK);
	}

	@GetMapping("/user-details")
	public ResponseEntity<InfoFuncionarioResponse> authenticatedUserDetails() throws Exception {
		UserPrinciple user = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Optional<InfoFuncionarioResponse> infoOpt = funcionarioService.obterDetalhesFuncionarioPorUsuarioId(user.getId());
		if (infoOpt.isPresent()) {
			return ResponseEntity.ok().body(infoOpt.get());
		}
		return ResponseEntity.notFound().build();
	}
}