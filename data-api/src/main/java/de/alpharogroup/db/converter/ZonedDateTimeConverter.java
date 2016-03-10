package de.alpharogroup.db.converter;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * The class {@link ZonedDateTimeConverter} is an attribute converter for {@link ZonedDateTime} to {@link Date}.
 *
 * @param ZonedDateTime the type of the entity attribute
 * @param Date the type of the database column
 */
@Converter(autoApply = true)
public class ZonedDateTimeConverter implements AttributeConverter<ZonedDateTime, Date> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Date convertToDatabaseColumn(final ZonedDateTime date) {
        final Instant instant = Instant.from(date);
        return Date.from(instant);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ZonedDateTime convertToEntityAttribute(final Date value) {
        final Instant instant = value.toInstant();
        return ZonedDateTime.from(instant);
    }
}