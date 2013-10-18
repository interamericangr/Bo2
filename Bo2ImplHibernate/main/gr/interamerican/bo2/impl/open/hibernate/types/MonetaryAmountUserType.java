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



import gr.interamerican.bo2.arch.utils.beans.MoneyImpl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.type.BigDecimalType;
import org.hibernate.usertype.UserType;

/**
 * Custom enum type used to persist monetary amounts.
 */
public class MonetaryAmountUserType implements UserType {

	/**
	 * Type declaration for BigDecimal
	 */
	public static final BigDecimalType BIG_DECIMAL = new BigDecimalType();

	public int[] sqlTypes() {
		return new int[] {BIG_DECIMAL.sqlType()};
	}
	
	@SuppressWarnings("rawtypes")
	public Class returnedClass() {
		return MoneyImpl.class;
	}

	public boolean isMutable() { 
		return false; 
	}

	public Object deepCopy(Object value) { 
		return value; 
	}

	public Serializable disassemble(Object value) { 
		return (Serializable) value;
	}

	public Object assemble(Serializable cached, Object owner) { 
		return cached; 
	}
		
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	public boolean equals(Object x, Object y) {
		if (x == y) return true;
		if (x == null || y == null) return false;
		return x.equals(y);
	}

	public int hashCode(Object x) {
		return x.hashCode();
	}

	public Object nullSafeGet(ResultSet resultSet, String[] names, Object owner)
	throws SQLException {
		BigDecimal value = resultSet.getBigDecimal(names[0]);
		if (resultSet.wasNull()) return null;
		return new MoneyImpl(value);
	}

	public void nullSafeSet(PreparedStatement statement, Object value, int index)
	throws HibernateException, SQLException {
		if (value == null) {
			statement.setNull(index, BIG_DECIMAL.sqlType());
		} else {
			MoneyImpl money = (MoneyImpl)value;
			statement.setBigDecimal(index, money.getAmount());
		}
	}
}
