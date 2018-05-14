package de.alpharogroup.db.entity.version;

/**
 * The interface {@link Versionable} can be implemented from an entity that need a version property
 * for the optimistic lock value.
 */
public interface Versionable
{

	/**
	 * Gets the version.
	 *
	 * @return the version
	 */
	Integer getVersion();

	/**
	 * Sets the version.
	 *
	 * @param version
	 *            the new version
	 */
	void setVersion(Integer version);
}
