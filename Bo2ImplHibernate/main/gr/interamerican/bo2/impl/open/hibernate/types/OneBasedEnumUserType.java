/*******************************************************************************
 * Copyright (c) 2013 INTERAMERICAN PROPERTY AND CASUALTY INSURANCE COMPANY S.A. 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/copyleft/lesser.html
 * 
 * This library is distributed in the hope that it will be useful, 
 * but WITHOUT ANY WARRANTY; without even the implied warranty of 
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU Lesser General Public License for more details.
 ******************************************************************************/
package gr.interamerican.bo2.impl.open.hibernate.types;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

/**
 * Custom enum type used to persist the enum ordinal value incremented by one.
 * 
 * Make sure that in the hibernate mapping the corresponding property and column
 * are marked with the attribute not-null="false".
 * 
 * Example declaration in mapping file:
 * 
 * <pre>
 * &lt;typedef class="gr.interamerican.bo2.impl.open.hibernate.types.OneBasedEnumUserType" name="InsuredItemType"&gt;
 *	&lt;param name="enumClassName"&gt;gr.interamerican.bo.def.pc.common.enums.InsuredItemType&lt;/param&gt;
 * &lt;/typedef&gt;
 * </pre>
 * 
 */
@SuppressWarnings("rawtypes")
public class OneBasedEnumUserType implements EnhancedUserType, ParameterizedType {

	/** the class of the enum. */
	private Class<Enum> enumClass;

	@Override
	@SuppressWarnings({ "unchecked", "nls" })
	public void setParameterValues(Properties parameters) {
		String enumClassName = parameters.getProperty("enumClassName");
		try {
			enumClass = (Class<Enum>) Class.forName(enumClassName);
		} catch (ClassNotFoundException cnfe) {
			throw new HibernateException("Enum class not found", cnfe);
		}
	}

	@Override
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	@Override
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	@Override
	public Serializable disassemble(Object value) throws HibernateException {
		return (Enum) value;
	}

	@Override
	public boolean equals(Object x, Object y) throws HibernateException {
		return x == y;
	}

	@Override
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	@Override
	public boolean isMutable() {
		return false;
	}

	@Deprecated
	@Override
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		long index = rs.getLong(names[0]);
		if (index == 0) {
			return null;
		}
		Enum enumValue = enumClass.getEnumConstants()[(int) index - 1];
		return rs.wasNull() ? null : enumValue;
	}

	@Deprecated
	@Override
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		if (value == null) {
			st.setLong(index, 0L);
		} else {
			st.setLong(index, ((Enum) value).ordinal() + 1);
		}
	}

	@Override
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	@Override
	public Class returnedClass() {
		return enumClass;
	}

	@Override
	public int[] sqlTypes() {
		return new int[] { Types.BIGINT };
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object fromXMLString(String xmlValue) {
		return Enum.valueOf(enumClass, xmlValue);
	}

	@Override
	public String objectToSQLString(Object value) {
		return '\'' + ((Enum) value).name() + '\'';
	}

	@Override
	public String toXMLString(Object value) {
		return ((Enum) value).name();
	}
}