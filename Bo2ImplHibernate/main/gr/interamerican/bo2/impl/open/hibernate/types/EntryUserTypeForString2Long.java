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
import gr.interamerican.bo2.utils.StringConstants;
import gr.interamerican.bo2.utils.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;

/**
 * Concrete implementation for Codified<Long> entries that
 * are stored in the database as strings.
 */
public class EntryUserTypeForString2Long
extends EntryUserType<Long> {
  
	@Override
    protected Long getCode(ResultSet rs, String name) 
    throws SQLException {		
		String dbCode = rs.getString(name);
		/*
		 * Default value, when the data is not correct.
		 * This SHOULD not return anything from the cache.
		 */
		Long code = -1L;
		if(StringUtils.isNullOrBlank(dbCode)) {
			return code;
		}
		try {
			code = Long.parseLong(dbCode.trim());
		}catch(NumberFormatException nfe) {
			return -1L;
		}
    	return code;
    }
    
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        if (value==null) {
            st.setString(index, StringConstants.EMPTY);
        }else {
        	@SuppressWarnings("unchecked") 
			TypedSelectable<Long> val = (TypedSelectable<Long>) value;
            st.setString( index, String.valueOf(val.getCode()));
        }
    }
    
    public String objectToSQLString(Object value) {
    	@SuppressWarnings("unchecked")
		TypedSelectable<Long> ts = (TypedSelectable<Long>) value; 
        return ts.getCode().toString();
    }
 
    public String toXMLString(Object value) {
    	@SuppressWarnings("unchecked")
		TypedSelectable<Long> ts = (TypedSelectable<Long>) value; 
        return ts.getCode().toString();
    }
    
    public Object fromXMLString(String xmlValue) {
		Long code = NumberUtils.string2Long(xmlValue);
		return this.cache().get(typeId, code);
	}
    
    @Override
    public int[] sqlTypes() {
		return new int[] { Types.CHAR };
	}
 
}
