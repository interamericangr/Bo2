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

import gr.interamerican.bo2.utils.Utils;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Trims the string value stored in the database before getting the value. <br/>
 * 
 * Useful when the column type is CHAR. In this case, if the value
 * stored in the column is longer than the column length, the value 
 * in the column is padded with spaces. This user type, trims the
 * value fetched from the table.
 */
public class TrimmedStringUserType implements UserType {

	public int[] sqlTypes() {
		return new int[] { Types.CHAR };
	}

	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return String.class;
	}

	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
		String value = rs.getString(names[0]);
		return value==null ? null : value.trim(); 
	}

	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
		 st.setString(index, (String)value);
	}

	public boolean equals(Object x, Object y) throws HibernateException {
		return Utils.equals(x, y);
	}

	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	public boolean isMutable() {
		return false;
	}

	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

}
