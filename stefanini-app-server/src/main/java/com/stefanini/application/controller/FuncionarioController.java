package com.stefanini.application.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.application.domain.dto.DetalhesFuncionario;
import com.stefanini.application.message.response.InfoFuncionarioResponse;
import com.stefanini.application.service.FuncionarioService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/funcionarios")
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;

	@GetMapping
	@PreAuthorize("hasRole('MENTOR') or hasRole('BP') or hasRole('GERENTE')")
	public ResponseEntity<List<DetalhesFuncionario>> listarFuncionarios() throws Exception {
		List<DetalhesFuncionario> funcionarios = funcionarioService.listarDetalhesFuncionarios();
		return ResponseEntity.ok(funcionarios);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('FUNCIONARIO') or hasRole('MENTOR') or hasRole('BP') or hasRole('GERENTE')")
	public ResponseEntity<InfoFuncionarioResponse> obterFuncionario(@PathVariable("id") Long id) throws Exception {
		Optional<InfoFuncionarioResponse> infoOpt = funcionarioService.obterDetalhesFuncionarioPorId(id);
		if (infoOpt.isPresent()) {
			return ResponseEntity.ok().body(infoOpt.get());
		}
		return ResponseEntity.notFound().build();
	}

	@PutMapping("/{id}/promover")
	@PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<Boolean> promoverFuncionario(@PathVariable("id") Long id) throws Exception {
		Boolean result = funcionarioService.promoverFuncionario(id);
		if (result) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.badRequest().build();
	}

}
