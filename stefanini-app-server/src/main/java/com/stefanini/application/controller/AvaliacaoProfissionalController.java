package com.stefanini.application.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stefanini.application.domain.dto.DetalhesAvaliarFuncionario;
import com.stefanini.application.domain.entity.AvaliacaoProfissional;
import com.stefanini.application.message.request.AvaliacaoForm;
import com.stefanini.application.message.response.InfoAvaliarResponse;
import com.stefanini.application.message.response.ResponseMessage;
import com.stefanini.application.service.AvaliacaoProfissionalService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/avaliacoes")
public class AvaliacaoProfissionalController {

	@Autowired
	private AvaliacaoProfissionalService avaliacaoProfissionalService;

	@GetMapping
	@PreAuthorize("hasRole('MENTOR') or hasRole('BP') or hasRole('GERENTE')")
	public ResponseEntity<List<DetalhesAvaliarFuncionario>> listarAvaliacoes() {
		List<DetalhesAvaliarFuncionario> avaliacoes = avaliacaoProfissionalService.listarAvaliacoesPendentesVotacao();
		return ResponseEntity.ok(avaliacoes);
	}

	@GetMapping("/{id}/criterios")
	@PreAuthorize("hasRole('MENTOR') or hasRole('BP') or hasRole('GERENTE')")
	public ResponseEntity<?> obterCriteriosAvaliacao(@PathVariable("id") Long avaliacaoId) {
		InfoAvaliarResponse response = avaliacaoProfissionalService.obterInfoAvaliacao(avaliacaoId);
		if (response.getCriterios().isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(response);
	}

	@PutMapping("/{id}/avaliar")
	@PreAuthorize("hasRole('MENTOR') or hasRole('BP')")
	public ResponseEntity<?> avaliar(@Valid @RequestBody AvaliacaoForm form, @PathVariable Long id) {
		try {
			AvaliacaoProfissional avaliacao = avaliacaoProfissionalService.avaliarFuncionario(form);
			return ResponseEntity.ok(avaliacao.getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest()
				.body(new ResponseMessage(e.getMessage()));
		}
	}

	@PutMapping("/{id}/aprovar")
	@PreAuthorize("hasRole('GERENTE')")
	public ResponseEntity<?> aprovarAvaliacao(@Valid @RequestBody AvaliacaoForm form, @PathVariable Long id) {
		try {
			AvaliacaoProfissional avaliacao = avaliacaoProfissionalService.aprovarAvaliacaoFuncionario(form);
			return ResponseEntity.ok(avaliacao.getId());
		} catch (Exception e) {
			return ResponseEntity.badRequest()
					.body(new ResponseMessage(e.getMessage()));
		}
	}

}
