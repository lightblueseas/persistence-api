package de.alpharogroup.db.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Entity class {@link Attribute} is keeping the information for
 * attributes that can be added to another Entity class.
 */
@Entity
@Table(name = "attributes")
@Getter
@Setter
@NoArgsConstructor
public class Attribute<T extends Serializable>
extends BaseEntity<T>
implements Cloneable {

	/**  The serial Version UID. */
	private static final long serialVersionUID = 1L;

	/** The name. */
	@Column(name = "name", length = 64)
	private String name;

	/** The value. */
	@Column(name = "value", length = 2048)
	private String value;

}