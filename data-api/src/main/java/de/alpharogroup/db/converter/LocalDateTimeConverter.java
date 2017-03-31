/**
 * Copyright (C) 2015 Asterios Raptis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.alpharogroup.db.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * The class {@link LocalDateTimeConverter} is an attribute converter for
 * {@link LocalDateTime} to {@link Date}.
 *
 * {@link LocalDateTime} the type of the entity attribute {@link Date} the type
 * of the database column
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