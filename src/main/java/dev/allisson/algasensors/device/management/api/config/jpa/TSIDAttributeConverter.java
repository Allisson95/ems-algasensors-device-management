package dev.allisson.algasensors.device.management.api.config.jpa;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class TSIDAttributeConverter implements AttributeConverter<TSID, Long> {
    @Override
    public Long convertToDatabaseColumn(final TSID attrValue) {
        return attrValue.toLong();
    }

    @Override
    public TSID convertToEntityAttribute(final Long dbValue) {
        return TSID.from(dbValue);
    }
}
