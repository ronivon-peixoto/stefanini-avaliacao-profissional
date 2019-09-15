package com.stefanini.application.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.stefanini.application.domain.enums.Cargo;

@Converter(autoApply = true)
public class CargoConverter implements AttributeConverter<Cargo, String> {

	@Override
	public String convertToDatabaseColumn(Cargo enumCargo) {
		if (enumCargo != null) {
			return enumCargo.getDescricao();
		}
		return null;
	}

	@Override
	public Cargo convertToEntityAttribute(String descricao) {
		if (descricao != null && !descricao.equals("")) {
			return Cargo.fromDescricao(descricao);
		}
		return null;
	}
}
