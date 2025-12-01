package com.hwan.lazyant.repository.portfolio.converter;

import com.hwan.lazyant.model.portfolio.Factor;
import jakarta.persistence.AttributeConverter;

public class FactorConverter implements AttributeConverter<Factor, Short> {

    @Override
    public Short convertToDatabaseColumn(Factor attribute) {
        if (attribute == null) return null;
        return attribute.getDbValue();
    }

    @Override
    public Factor convertToEntityAttribute(Short dbData) {
        return Factor.fromDbValue(dbData);
    }
}