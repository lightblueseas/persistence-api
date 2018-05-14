package de.alpharogroup.db.entity.validation;

/**
 * The interface {@link Validatable}.
 *
 * @param <T>
 *            the generic type
 */
public interface Validatable<T>
{

	/**
	 * Gets the valid from.
	 *
	 * @return the valid from
	 */
	T getValidFrom();

	/**
	 * Sets the valid from.
	 *
	 * @param validFrom
	 *            the new valid from
	 */
	void setValidFrom(T validFrom);

	/**
	 * Gets the valid till.
	 *
	 * @return the valid till
	 */
	T getValidTill();

	/**
	 * Sets the valid till.
	 *
	 * @param validTill
	 *            the new valid till
	 */
	void setValidTill(T validTill);
}
