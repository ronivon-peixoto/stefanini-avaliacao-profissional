package com.stefanini.application.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.stefanini.application.domain.enums.ValorCriterio;

@Converter(autoApply = true)
public class ValorCriterioConverter implements AttributeConverter<ValorCriterio, Integer> {

	@Override
	public Integer convertToDatabaseColumn(ValorCriterio enumValorCriterio) {
		if (enumValorCriterio != null) {
			return enumValorCriterio.getValor();
		}
		return null;
	}

	@Override
	public ValorCriterio convertToEntityAttribute(Integer valor) {
		if (valor != null) {
			return ValorCriterio.fromValor(valor);
		}
		return null;
	}
}
