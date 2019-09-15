package com.stefanini.application.domain.enums;

import java.util.ArrayList;
import java.util.List;

import com.stefanini.application.domain.dto.PropertyDto;

public enum ValorCriterio {

	NAO_ATENDE("Não Atende", 0), 
	ATENDE_PARCIALMENTE("Atende Parcialmente", 50), 
	ATENDE("Atende", 80),
	SUPERA("Supera", 100);

	private String descricao;
	private Integer valor;

	private ValorCriterio(String descricao, Integer valor) {
		this.descricao = descricao;
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getValor() {
		return valor;
	}

	public static ValorCriterio fromValor(Integer valor) {
		ValorCriterio aux = null;
		for (ValorCriterio it : ValorCriterio.values()) {
			if (it.getValor() == valor) {
				aux = it;
			}
		}
		return aux;
	}

	public static List<PropertyDto> toProperties() {
		List<PropertyDto> properties = new ArrayList<>();
		for (ValorCriterio it : ValorCriterio.values()) {
			properties.add(new PropertyDto(it.name(), it.getDescricao()));
		}
		return properties;
	}

}
