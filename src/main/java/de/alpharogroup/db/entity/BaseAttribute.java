package de.alpharogroup.db.entity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;

import de.alpharogroup.db.entity.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Access(AccessType.FIELD)
@Getter
@Setter
@NoArgsConstructor
public abstract class BaseAttribute<T extends Serializable> extends BaseEntity<T> {


	/**  The serial Version UID. */
	private static final long serialVersionUID = 1L;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		      name="base_attributes",
		      joinColumns={@JoinColumn(name="base_attributes_id", referencedColumnName="id")},
		      inverseJoinColumns={@JoinColumn(name="attributes_id", referencedColumnName="id")})
	private Set<Attribute<T>> attributes = new HashSet<>(0);
	
}