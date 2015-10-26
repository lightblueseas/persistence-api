package org.flirt.and.date.hbm.dao;

import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

import de.alpharogroup.db.entity.BaseEntity;

/**
 * Model object that represents a security role.
 */
@Entity
@Table(name = "roles")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Role  extends BaseEntity<Long> {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String name;

	private String description;

	private Set<String> permissions;

	protected Role() {
	}

	public Role(String name) {
		this.name = name;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Basic(optional = false)
	@Column(length = 100)
	@Index(name = "idx_roles_name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic(optional = false)
	@Column(length = 255)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@ElementCollection
	@JoinTable(name = "roles_permissions")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public static class Builder {
		private Long id;
		private String name;
		private String description;
		private Set<String> permissions;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder permissions(Set<String> permissions) {
			this.permissions = permissions;
			return this;
		}

		public Role build() {
			return new Role(this);
		}
	}

	private Role(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.description = builder.description;
		this.permissions = builder.permissions;
	}
}
