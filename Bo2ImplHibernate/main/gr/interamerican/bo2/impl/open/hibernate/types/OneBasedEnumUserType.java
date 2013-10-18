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
 * <typedef class="gr.interamerican.bo2.impl.open.hibernate.types.OneBasedEnumUserType" name="InsuredItemType">
 *	<param name="enumClassName">gr.interamerican.bo.def.pc.common.enums.InsuredItemType</param>
 * </typedef>
 * 
 */
@SuppressWarnings("rawtypes")
public class OneBasedEnumUserType implements EnhancedUserType, ParameterizedType {

    
    /**
     * the class of the enum
     */
    private Class<Enum> enumClass;
 
    @SuppressWarnings({"unchecked", "nls"})
	public void setParameterValues(Properties parameters) {
        String enumClassName = parameters.getProperty("enumClassName");
        try {
            enumClass = (Class<Enum>) Class.forName(enumClassName);
        }
        catch (ClassNotFoundException cnfe) {
            throw new HibernateException("Enum class not found", cnfe);
        }
    }
 
    public Object assemble(Serializable cached, Object owner) 
    throws HibernateException {
        return cached;
    }
 
    public Object deepCopy(Object value) throws HibernateException {
        return value;
    }
 
    public Serializable disassemble(Object value) throws HibernateException {
        return (Enum) value;
    }
 
    public boolean equals(Object x, Object y) throws HibernateException {
        return x==y;
    }
 
    public int hashCode(Object x) throws HibernateException {
        return x.hashCode();
    }
 
    public boolean isMutable() {
        return false;
    }
 
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) 
    throws HibernateException, SQLException {
        long index = rs.getLong(names[0]);
        if (index == 0) {
        	return null;
        }
        Enum enumValue = enumClass.getEnumConstants()[(int)index-1];
        return rs.wasNull() ? null : enumValue;
    }
 
    public void nullSafeSet(PreparedStatement st, Object value, int index) 
    throws HibernateException, SQLException {
        if (value==null) {
            st.setLong(index, 0L);
        }
        else {
            st.setLong( index, ((Enum) value).ordinal() + 1); 
        }
    }
 
    public Object replace(Object original, Object target, Object owner) 
    throws HibernateException {
        return original;
    }
 
    public Class returnedClass() {
        return enumClass;
    }
 
    public int[] sqlTypes() {
        return new int[] { Types.BIGINT };
    }
 
    @SuppressWarnings("unchecked")
	public Object fromXMLString(String xmlValue) {
        return Enum.valueOf(enumClass, xmlValue);
    }
 
    public String objectToSQLString(Object value) {
        return '\'' + ( (Enum) value ).name() + '\'';
    }
 
    public String toXMLString(Object value) {
        return ( (Enum) value ).name();
    }
}
