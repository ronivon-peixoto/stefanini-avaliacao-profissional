package com.stefanini.application.util.projections;

import java.util.Date;

import com.stefanini.application.domain.dto.DetalhesFuncionario;

public class DetalhesFuncionarioDto implements DetalhesFuncionario {

	private String username;
	private String email;
	private Long id;
	private String nome;
	private String cpf;
	private Date dataAdmissao;
	private Integer nivel;
	private String cargo;
	private Date dataPromocao;
	private String nomeMentor;
	private String nomeGerente;
	private String nomeCentroCusto;
	private String enderecoCentroCusto;

	public DetalhesFuncionarioDto() {
		super();
	}

	public DetalhesFuncionarioDto(Long id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	@Override
	public Date getDataAdmissao() {
		return dataAdmissao;
	}

	public void setDataAdmissao(Date dataAdmissao) {
		this.dataAdmissao = dataAdmissao;
	}

	@Override
	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	@Override
	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	@Override
	public Date getDataPromocao() {
		return dataPromocao;
	}

	public void setDataPromocao(Date dataPromocao) {
		this.dataPromocao = dataPromocao;
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

	@Override
	public String getEnderecoCentroCusto() {
		return enderecoCentroCusto;
	}

	public void setEnderecoCentroCusto(String enderecoCentroCusto) {
		this.enderecoCentroCusto = enderecoCentroCusto;
	}

}
