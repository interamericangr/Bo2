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

import gr.interamerican.bo2.arch.ext.TypedSelectable;
import gr.interamerican.bo2.utils.NumberUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;

/**
 * Concrete implementation for Codified<Integer> entries.
 */
public class EntryUserTypeForInteger
extends EntryUserType<Integer> {
	
	@Override
    protected Integer getCode(ResultSet rs, String name) 
    throws SQLException {		
    	return rs.getInt(name);
    }
    
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        if (value==null) {
            st.setLong(index, 0);
        }else {
        	@SuppressWarnings("unchecked") 
			TypedSelectable<Integer> val = (TypedSelectable<Integer>) value;
            st.setInt(index, val.getCode());
        }
    }
    
    public String objectToSQLString(Object value) {
    	@SuppressWarnings("unchecked")
		TypedSelectable<Integer> ts = (TypedSelectable<Integer>) value; 
        return ts.getCode().toString();
    }
 
    public String toXMLString(Object value) {
    	@SuppressWarnings("unchecked")
		TypedSelectable<Integer> ts = (TypedSelectable<Integer>) value; 
        return ts.getCode().toString();
    }
    
    public Object fromXMLString(String xmlValue) {
		Integer code = NumberUtils.string2Int(xmlValue);
		return this.cache.get(typeId, code);
	}
 
}
