package com.stefanini.application.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.stefanini.application.domain.dto.CriterioAvaliacaoDto;
import com.stefanini.application.domain.entity.CriterioAvaliacao;

@Repository
public interface CriterioAvaliacaoRepository extends JpaRepository<CriterioAvaliacao, Long> {

	@Query("SELECT new com.stefanini.application.domain.dto.CriterioAvaliacaoDto(ca.id, ve.descricao) FROM CriterioAvaliacao ca INNER JOIN ca.valorEmpresa ve WHERE ca.avaliacao.id = :avaliacaoId")
	List<CriterioAvaliacaoDto> listarCriteriosPorAvaliacao(@Param("avaliacaoId") Long avaliacaoId);

}
