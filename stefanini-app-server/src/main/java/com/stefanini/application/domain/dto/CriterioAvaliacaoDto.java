package com.stefanini.application.domain.dto;

import com.stefanini.application.domain.enums.ValorCriterio;

public class CriterioAvaliacaoDto {

	private Long idCriterio;
	private String descricaoValorEmpresa;
	private ValorCriterio nota;

	public CriterioAvaliacaoDto() {
		super();
	}

	public CriterioAvaliacaoDto(Long idCriterio, String descricaoValorEmpresa) {
		super();
		this.idCriterio = idCriterio;
		this.descricaoValorEmpresa = descricaoValorEmpresa;
	}

	public CriterioAvaliacaoDto(Long idCriterio, String descricaoValorEmpresa, ValorCriterio nota) {
		super();
		this.idCriterio = idCriterio;
		this.descricaoValorEmpresa = descricaoValorEmpresa;
		this.nota = nota;
	}

	public Long getIdCriterio() {
		return idCriterio;
	}

	public void setIdCriterio(Long idCriterio) {
		this.idCriterio = idCriterio;
	}

	public String getDescricaoValorEmpresa() {
		return descricaoValorEmpresa;
	}

	public void setDescricaoValorEmpresa(String descricaoValorEmpresa) {
		this.descricaoValorEmpresa = descricaoValorEmpresa;
	}

	public ValorCriterio getNota() {
		return nota;
	}

	public void setNota(ValorCriterio nota) {
		this.nota = nota;
	}

}
