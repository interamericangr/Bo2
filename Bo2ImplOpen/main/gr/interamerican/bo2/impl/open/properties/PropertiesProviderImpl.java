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
package gr.interamerican.bo2.impl.open.properties;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;
import gr.interamerican.bo2.impl.open.utils.Exceptions;
import gr.interamerican.bo2.utils.DateUtils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

/**
 * Implementation of {@link PropertiesProvider}. 
 *
 */
public class PropertiesProviderImpl implements PropertiesProvider {
	
	/**
	 * Message key for parameter not found.
	 */
	private static final String PARM_NOT_SET =
		"Program.PARAM_NOT_SET"; //$NON-NLS-1$
	
	/**
	 * Properties object.
	 */
	private Properties properties;
	
	/**
	 * Creates a new PropertiesProviderImpl.
	 * 
	 * @param properties 
	 *        Properties that this object will provide.
	 */
	public PropertiesProviderImpl(Properties properties) {
		this.properties=properties;
	}

	public Properties getProperties() {		
		return properties;
	}

	public Object getPropertyValue(String key) {
		return properties.get(key);
	}

	public Date getPropertyAsDate (String key) 
	throws UnexpectedException {
		Object value = properties.get(key);
		try {
			return DateUtils.getDate(value);
		} catch (ParseException e) {
			throw new UnexpectedException(e);
		}
	}

	public Double getPropertyAsDouble (String key) 
	throws UnexpectedException {
		
		Object value = properties.get(key);
		if (value==null) {
			return null;
		}
		if (value instanceof Number) {
			Number num = (Number) value;
			return num.doubleValue();
		}
		NumberFormat nf = NumberFormat.getInstance();
		try {
			Number num = nf.parse(value.toString());
			return num.doubleValue();
		} catch (ParseException e) {
			throw new UnexpectedException(e);
		}
	}

	public String getProperty (String key) {		
		Object value = properties.get(key);
		if (value==null) {
			return null;
		}
		return value.toString();
	}

	public Boolean getPropertyAsBoolean (String key) {
        String s = properties.getProperty(key);
        if (s==null) {
        	return null;
        }
        s = s.trim();
        return s.equalsIgnoreCase("TRUE")  //$NON-NLS-1$
           ||  s.equalsIgnoreCase("YES")   //$NON-NLS-1$                       
           ||  s.equals("1");              //$NON-NLS-1$
        
    } 	

    public String getMainProperty (String key) {
		String value = getProperty(key);
		if (value==null) throw Exceptions.runtime(PARM_NOT_SET, key); 
		return value;
	}	

	public Date getMainPropertyAsDate (String key) 
	throws UnexpectedException {
		Date value = getPropertyAsDate(key);
		if (value==null) throw Exceptions.runtime(PARM_NOT_SET, key);
		return value;
	}

	public Double getMainPropertyAsDouble (String key) 
	throws UnexpectedException {
		Double value = getPropertyAsDouble(key);
		if (value==null) throw Exceptions.runtime(PARM_NOT_SET, key);
		return value;
	}

	public Boolean getMainPropertyAsBoolean (String key) {
		Boolean value = getPropertyAsBoolean(key);
		if (value==null) throw Exceptions.runtime(PARM_NOT_SET, key);
		return value;
    }

	public Object getMainPropertyValue(String key) {
		Object value = getPropertyValue(key);
		if (value==null) throw Exceptions.runtime(PARM_NOT_SET, key);
		return value;
	}
			
	public void close() throws DataException {
		/* empty */		
	}

}
