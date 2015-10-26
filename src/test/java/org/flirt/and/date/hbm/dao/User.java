package org.flirt.and.date.hbm.dao;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;

import de.alpharogroup.db.entity.BaseEntity;

/**
 * Simple class that represents any User domain entity in any application.
 * 
 * <p>
 * Because this class performs its own Realm and Permission checks, and these
 * can happen frequently enough in a production application, it is highly
 * recommended that the internal User {@link #getRoles} collection be cached in
 * a 2nd-level cache when using JPA and/or Hibernate. The hibernate xml
 * configuration for this sample application does in fact do this for your
 * reference (see User.hbm.xml - the 'roles' declaration).
 * </p>
 */
@Entity
@Table(name = "users")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class User extends BaseEntity<Long> {
	
	public static void main(final String[] args) throws CloneNotSupportedException {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role.Builder().id(2L).name("master").description("the master role").build());
		User user = new User();
		user.setRoles(roles);
		user.setEmail("bob@mail.com");
		user.setPassword("secret");
		user.setUsername("bob");
		System.out.println(user.toString());
		User cloned = (User) user.clone();
		System.out.println(cloned);
		System.out.println(cloned.toXml());
}

	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
	private String password;
	private Set<Role> roles = new HashSet<Role>();

	/**
	 * Returns the username associated with this user account;
	 * 
	 * @return the username associated with this user account;
	 */
	@Basic(optional = false)
	@Column(length = 100)
	@Index(name = "idx_users_username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Basic(optional = false)
	@Index(name = "idx_users_email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Returns the password for this user.
	 * 
	 * @return this user's password
	 */
	@Basic(optional = false)
	@Column(length = 255)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@ManyToMany
	@JoinTable(name = "users_roles")
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
