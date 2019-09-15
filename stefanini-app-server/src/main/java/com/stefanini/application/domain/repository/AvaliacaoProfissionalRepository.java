package com.stefanini.application.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stefanini.application.domain.dto.DetalhesAvaliacaoProfissional;
import com.stefanini.application.domain.dto.DetalhesAvaliarFuncionario;
import com.stefanini.application.domain.entity.AvaliacaoProfissional;

@Repository
public interface AvaliacaoProfissionalRepository extends JpaRepository<AvaliacaoProfissional, Long> {

	List<DetalhesAvaliarFuncionario> listarAvaliacoesPendentesAprovacaoGerente(Long userId);

	List<DetalhesAvaliarFuncionario> listarAvaliacoesPendentesVotacaoBP(Long userId);

	List<DetalhesAvaliarFuncionario> listarAvaliacoesPendentesVotacaoMentor(Long userId);

	String obterRessalvasAvaliacaoAnterior(Long avaliacaoId);

	List<DetalhesAvaliacaoProfissional> obterDetalhesAvaliacoesFuncionario(Long funcionarioId);

}
