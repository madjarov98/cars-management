package com.master.cars;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class EmptyStringToNullConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String string) {
        if (string.equals(""))
            return null;
        else
            return string;
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData;
    }
}