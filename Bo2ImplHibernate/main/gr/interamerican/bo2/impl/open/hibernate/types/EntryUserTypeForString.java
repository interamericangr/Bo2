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
import gr.interamerican.bo2.utils.StringConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.hibernate.HibernateException;

/**
 * Concrete implementation for Codified<String> entries.
 */
public class EntryUserTypeForString 
extends EntryUserType<String> {
  
	@Override
    protected String getCode(ResultSet rs, String name) throws SQLException {		
    	return rs.getString(name);
    }
    
	public void nullSafeSet(PreparedStatement st, Object value, int index)
    throws HibernateException, SQLException {
        if (value==null) {
            st.setString(index, StringConstants.EMPTY);
        }else {
        	@SuppressWarnings("unchecked") 
			TypedSelectable<String> val = (TypedSelectable<String>) value;
            st.setString( index, val.getCode());
        }
    }
    
    public String objectToSQLString(Object value) {
    	@SuppressWarnings("unchecked")
		TypedSelectable<String> ts = (TypedSelectable<String>) value; 
        return ts.getCode();
    }
 
    public String toXMLString(Object value) {
    	@SuppressWarnings("unchecked")
		TypedSelectable<String> ts = (TypedSelectable<String>) value; 
        return ts.getCode();
    }
    
    public Object fromXMLString(String xmlValue) {
		return this.cache.get(typeId, xmlValue);
	}
 
}
