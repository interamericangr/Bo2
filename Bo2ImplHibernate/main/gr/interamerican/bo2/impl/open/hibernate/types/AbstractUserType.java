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

import org.hibernate.HibernateException;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

/**
 * Base implementation for custom Hibernate UserTypes.
 */
public abstract class AbstractUserType 
implements EnhancedUserType, ParameterizedType{
	
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
