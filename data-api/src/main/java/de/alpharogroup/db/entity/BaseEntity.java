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
package de.alpharogroup.db.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import de.alpharogroup.lang.object.CloneObjectExtensions;
import de.alpharogroup.xml.XmlExtensions;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class BaseEntity holds the primary key.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity<PK extends Serializable> implements Serializable
{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The technical primary key. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private PK id;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return CloneObjectExtensions.cloneObjectQuietly(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		final BaseEntity other = (BaseEntity)obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		}
		else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * Factory callback method that can be overwritten to get another {@link ToStringStyle} object
	 * for the {@link BaseEntity#toString()}. Default is {@link ToStringStyle#SHORT_PREFIX_STYLE}.
	 *
	 * @return the new {@link ToStringStyle}
	 */
	protected ToStringStyle newToStringStyle()
	{
		return ToStringStyle.SHORT_PREFIX_STYLE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, newToStringStyle());
	}

	/**
	 * Returns a xml string representation of the object.
	 *
	 * @return the xml string.
	 */
	public String toXml()
	{
		final Map<String, Class<?>> aliases = new HashMap<>();
		final String lqSimpleName = this.getClass().getSimpleName().toLowerCase();
		aliases.put(lqSimpleName, getClass());
		final String xml = XmlExtensions.toXmlWithXStream(this, aliases);
		return xml;
	}
}
