package com.stefanini.application.message.response;

import java.util.List;

import com.stefanini.application.domain.dto.CriterioAvaliacaoDto;
import com.stefanini.application.domain.dto.PropertyDto;

public class InfoAvaliarResponse {

	private Long idAvaliacao;

	private String nomeMentor;

	private Double notaMentor;

	private String comentariosMentor;

	private String nomeBp;

	private Double notaBp;

	private String comentariosBp;

	private String ressalvasAvaliacaoAnterior;

	private List<CriterioAvaliacaoDto> criterios;

	private List<PropertyDto> pesos;

	public Long getIdAvaliacao() {
		return idAvaliacao;
	}

	public void setIdAvaliacao(Long idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}

	public String getNomeMentor() {
		return nomeMentor;
	}

	public void setNomeMentor(String nomeMentor) {
		this.nomeMentor = nomeMentor;
	}

	public Double getNotaMentor() {
		return notaMentor;
	}

	public void setNotaMentor(Double notaMentor) {
		this.notaMentor = notaMentor;
	}

	public String getComentariosMentor() {
		return comentariosMentor;
	}

	public void setComentariosMentor(String comentariosMentor) {
		this.comentariosMentor = comentariosMentor;
	}

	public String getNomeBp() {
		return nomeBp;
	}

	public void setNomeBp(String nomeBp) {
		this.nomeBp = nomeBp;
	}

	public Double getNotaBp() {
		return notaBp;
	}

	public void setNotaBp(Double notaBp) {
		this.notaBp = notaBp;
	}

	public String getComentariosBp() {
		return comentariosBp;
	}

	public void setComentariosBp(String comentariosBp) {
		this.comentariosBp = comentariosBp;
	}

	public String getRessalvasAvaliacaoAnterior() {
		return ressalvasAvaliacaoAnterior;
	}

	public void setRessalvasAvaliacaoAnterior(String ressalvasAvaliacaoAnterior) {
		this.ressalvasAvaliacaoAnterior = ressalvasAvaliacaoAnterior;
	}

	public List<CriterioAvaliacaoDto> getCriterios() {
		return criterios;
	}

	public void setCriterios(List<CriterioAvaliacaoDto> criterios) {
		this.criterios = criterios;
	}

	public List<PropertyDto> getPesos() {
		return pesos;
	}

	public void setPesos(List<PropertyDto> pesos) {
		this.pesos = pesos;
	}

}
