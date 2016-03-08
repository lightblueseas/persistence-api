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

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import de.alpharogroup.lang.ObjectExtensions;
import de.alpharogroup.xml.XmlExtensions;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * The Class BaseEntity holds the primary key.
 */
@MappedSuperclass
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseEntity<PK extends Serializable> implements
		Serializable {

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
	public String toString() {
		return ToStringBuilder.reflectionToString(this, newToStringStyle());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return ObjectExtensions.cloneObjectQuietly(this);
	}

	/**
	 * Factory callback method that can be overwritten to get another
	 * {@link ToStringStyle} object for the {@link BaseEntity#toString()}.
	 * Default is {@link ToStringStyle#SHORT_PREFIX_STYLE}.
	 * @return the new {@link ToStringStyle}
	 */
	protected ToStringStyle newToStringStyle() {
		return ToStringStyle.SHORT_PREFIX_STYLE;
	}

	/**
	 * Returns a xml string representation of the object.
	 *
	 * @return the xml string.
	 */
	public String toXml() {
		Map<String, Class<?>> aliases = new HashMap<String, Class<?>>();
		String lqSimpleName = this.getClass().getSimpleName().toLowerCase();
		aliases.put(lqSimpleName, getClass());
		String xml = XmlExtensions.toXmlWithXStream(this, aliases);
		return xml;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("rawtypes")
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
