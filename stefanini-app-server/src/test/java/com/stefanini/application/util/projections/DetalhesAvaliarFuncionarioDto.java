package com.stefanini.application.util.projections;

import java.util.Date;

import com.stefanini.application.domain.dto.DetalhesAvaliarFuncionario;

public class DetalhesAvaliarFuncionarioDto implements DetalhesAvaliarFuncionario {

	private Long idAvaliacao;
	private Date dataCriacaoAvaliacao;
	private Long idFuncionario;
	private String nomeFuncionario;
	private String cpfFuncionario;
	private Integer nivelFuncionario;
	private String cargoFuncionario;
	private Date dataAdmissaoFuncionario;
	private String nomeMentor;
	private String nomeGerente;
	private String nomeCentroCusto;

	public DetalhesAvaliarFuncionarioDto() {
		super();
	}

	public DetalhesAvaliarFuncionarioDto(Long idAvaliacao, String cpfFuncionario) {
		super();
		this.idAvaliacao = idAvaliacao;
		this.cpfFuncionario = cpfFuncionario;
	}

	@Override
	public Long getIdAvaliacao() {
		return idAvaliacao;
	}

	public void setIdAvaliacao(Long idAvaliacao) {
		this.idAvaliacao = idAvaliacao;
	}

	@Override
	public Date getDataCriacaoAvaliacao() {
		return dataCriacaoAvaliacao;
	}

	public void setDataCriacaoAvaliacao(Date dataCriacaoAvaliacao) {
		this.dataCriacaoAvaliacao = dataCriacaoAvaliacao;
	}

	@Override
	public Long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	@Override
	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
	}

	@Override
	public String getCpfFuncionario() {
		return cpfFuncionario;
	}

	public void setCpfFuncionario(String cpfFuncionario) {
		this.cpfFuncionario = cpfFuncionario;
	}

	@Override
	public Integer getNivelFuncionario() {
		return nivelFuncionario;
	}

	public void setNivelFuncionario(Integer nivelFuncionario) {
		this.nivelFuncionario = nivelFuncionario;
	}

	@Override
	public String getCargoFuncionario() {
		return cargoFuncionario;
	}

	public void setCargoFuncionario(String cargoFuncionario) {
		this.cargoFuncionario = cargoFuncionario;
	}

	@Override
	public Date getDataAdmissaoFuncionario() {
		return dataAdmissaoFuncionario;
	}

	public void setDataAdmissaoFuncionario(Date dataAdmissaoFuncionario) {
		this.dataAdmissaoFuncionario = dataAdmissaoFuncionario;
	}

	@Override
	public String getNomeMentor() {
		return nomeMentor;
	}

	public void setNomeMentor(String nomeMentor) {
		this.nomeMentor = nomeMentor;
	}

	@Override
	public String getNomeGerente() {
		return nomeGerente;
	}

	public void setNomeGerente(String nomeGerente) {
		this.nomeGerente = nomeGerente;
	}

	@Override
	public String getNomeCentroCusto() {
		return nomeCentroCusto;
	}

	public void setNomeCentroCusto(String nomeCentroCusto) {
		this.nomeCentroCusto = nomeCentroCusto;
	}

}
