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
package de.alpharogroup.db.postgres.usertype;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;
import org.postgresql.util.PGobject;

/**
 * The class {@link PGEnumUserType} maps string to enum and back. Can be used
 * only with Postgres database and hibernate.
 * 
 * Note: Only use with Postgres and hibernate!!!
 */
public class PGEnumUserType implements EnhancedUserType, ParameterizedType {

	/** The enum class. */
	@SuppressWarnings("rawtypes")
	private Class<Enum> enumClass;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Enum) value;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object one, Object another) throws HibernateException {
		return one == another;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public Object fromXMLString(String xmlValue) {
		return Enum.valueOf(enumClass, xmlValue);
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode(Object object) throws HibernateException {
		return object.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isMutable() {
		return false;
	}

	/**
	 * Retrieve an instance of the mapped class from a JDBC result set.
	 *
	 * @param rs
	 *            the JDBC result set
	 * @param names
	 *            the column names
	 * @param owner
	 *            the entity that contains the enum.
	 * @return the object
	 * @throws HibernateException
	 *             is thrown if an error from hibernate occured
	 * @throws SQLException
	 *             is thrown if an error with sql
	 */
	@SuppressWarnings("unchecked")
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		Object object = rs.getObject(names[0]);
		if (rs.wasNull()) {
			return null;
		}
		// Notice how Object is mapped to PGobject. This makes this
		// implementation Postgres specific
		if (object instanceof PGobject) {
			PGobject pg = (PGobject) object;
			return Enum.valueOf(enumClass, pg.getValue());
		}
		// Try to get over the name of the enum value.
		if (object != null && enumClass != null) {
			String enumValueName = object.toString().trim();
			return Enum.valueOf(enumClass, enumValueName);
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
			throws HibernateException, SQLException {
		return nullSafeGet(rs, names, owner);
	}

	/**
	 * Write an instance of the mapped class to a prepared statement. A
	 * multi-column type should be written to parameters starting from
	 * <tt>index</tt>.
	 *
	 * @param st
	 *            a JDBC prepared statement
	 * @param value
	 *            the object to write
	 * @param index
	 *            the statement parameter index
	 * @throws HibernateException
	 *             is thrown if an error from hibernate occured
	 * @throws SQLException
	 *             is thrown if an error with sql
	 */
	@SuppressWarnings("rawtypes")
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (value == null) {
			st.setNull(index, Types.OTHER);
			// UPDATE: To support NULL insertion, change to:
			// st.setNull(index, 1111);
		} else {
			// Notice 1111 which java.sql.Type for Postgres Enum
			st.setObject(index, ((Enum) value), Types.OTHER);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
			throws HibernateException, SQLException {
		nullSafeSet(st, value, index);
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	public String objectToSQLString(Object value) {
		return '\'' + ((Enum) value).name() + '\'';
	}

	/**
	 * {@inheritDoc}
	 */
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return enumClass;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enumClassName");
		try {
			enumClass = (Class<Enum>) Class.forName(enumClassName);
		} catch (ClassNotFoundException cnfe) {
			throw new HibernateException("Enum class not found", cnfe);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int[] sqlTypes() {
		// UPDATE: To support NULL insertion, change to:
		// return new int[] { 1111 };
		return new int[] { Types.VARCHAR };
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("rawtypes")
	public String toXMLString(Object value) {
		return ((Enum) value).name();
	}
}
