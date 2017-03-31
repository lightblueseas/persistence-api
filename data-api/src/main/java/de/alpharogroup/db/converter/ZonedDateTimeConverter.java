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
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * The class {@link ZonedDateTimeConverter} is an attribute converter for
 * {@link ZonedDateTime} to {@link Date}.
 *
 * {@link ZonedDateTime} the type of the entity attribute {@link Date} the type
 * of the database column
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