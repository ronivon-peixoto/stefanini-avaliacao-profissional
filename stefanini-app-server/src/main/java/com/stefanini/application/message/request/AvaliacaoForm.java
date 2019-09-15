package com.stefanini.application.message.request;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.stefanini.application.domain.dto.CriterioAvaliacaoDto;

public class AvaliacaoForm implements Serializable {

	private static final long serialVersionUID = -4576540930962529775L;

	@NotNull
	private Long idAvaliacao;
	private String ressalvas;
	private String comentarios;
	private List<CriterioAvaliacaoDto> criterios;

	public AvaliacaoForm() {
		super();
	}

	public AvaliacaoForm(@NotNull Long idAvaliacao, String ressalvas, String comentarios) {
		super();
		this.idAvaliacao = idAvaliacao;
		this.ressalvas = ressalvas;
		this.comentarios = comentarios;
	}

	public Long getIdAvaliacao() {
		return idAvaliacao;
	}

	public void setIdAvaliacao(Long idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}

	public String getRessalvas() {
		return ressalvas;
	}

	public void setRessalvas(String ressalvas) {
		this.ressalvas = ressalvas;
	}

	public String getComentarios() {
		return comentarios;
	}

	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}

	public List<CriterioAvaliacaoDto> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<CriterioAvaliacaoDto> criterios) {
		this.criterios = criterios;
	}

	public void addCriterio(CriterioAvaliacaoDto criterio) {
		if (this.criterios == null) {
			this.criterios = new ArrayList<>();
		}
		this.criterios.add(criterio);
	}

}
