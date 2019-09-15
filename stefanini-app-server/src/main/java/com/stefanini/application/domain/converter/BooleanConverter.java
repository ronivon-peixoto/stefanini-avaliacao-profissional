package com.stefanini.application.domain.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanConverter implements AttributeConverter<Boolean, Character> {

	@Override
	public Character convertToDatabaseColumn(Boolean attribute) {
		if (attribute != null) {
			if (attribute) {
				return 'S';
			} else {
				return 'N';
			}

		}
		return null;
	}

	@Override
	public Boolean convertToEntityAttribute(Character dbData) {
		if (dbData != null) {
			return dbData.equals('S');
		}
		return null;
	}
}
