package de.alpharogroup.db.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * The class {@link LocalDateTimeConverter} is an attribute converter for {@link LocalDateTime} to {@link Date}.
 *
 * {@link LocalDateTime} the type of the entity attribute
 * {@link Date} the type of the database column
 */
@Converter(autoApply = true)
public class LocalDateTimeConverter implements AttributeConverter<LocalDateTime, Date> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Date convertToDatabaseColumn(final LocalDateTime date) {
        final Instant instant = Instant.from(date);
        return Date.from(instant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime convertToEntityAttribute(final Date value) {
        final Instant instant = value.toInstant();
        return LocalDateTime.from(instant);
    }

}