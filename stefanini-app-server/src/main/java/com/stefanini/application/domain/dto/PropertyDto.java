package com.stefanini.application.domain.dto;

public class PropertyDto {

	private Object codigo;

	private String descricao;

	public PropertyDto() {
		super();
	}

	public PropertyDto(Object codigo, String descricao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
	}

	public Object getCodigo() {
		return codigo;
	}

	public void setCodigo(Object codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
