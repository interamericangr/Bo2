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

import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.exceptions.UnexpectedException;

import java.util.Date;
import java.util.Properties;




/**
 * Properties provider.
 * 
 * A properties object can contain any type of object
 * that is accessible with a key.
 *
 */
public interface PropertiesProvider extends ResourceWrapper {
    
    /**
     * Gets the properties object.
     * 
     * @return Returns a properties object. 

     */
    public Properties getProperties(); 
    
    
    /**
     * Gets the value of the specified property.
     * 
     * @param key key of property.
     * 
     * @return value of the property.
     */
    public Object getPropertyValue(String key);
    
	/**
	 * Gets the string value of a an input parameter.
	 * 
	 * @param key The parameter name.
	 * 
	 * @return Returns the string value of the input parameter.
	 */
	public String getProperty (String key);
    
    
	/**
	 * Gets the date value of an input parameter.
	 * 
	 * @param key String the property's name
	 * 
	 * @return Returns the date represented by the
	 *         input parameter.
	 * 
	 * @throws UnexpectedException 
	 *         If the parameter value does not contain a valid
	 *         date according to the system locale.
	 * 
	 */
	public Date getPropertyAsDate (String key) 
	throws UnexpectedException;
	
	/**
	 * Gets the double value of a an input parameter.
	 * 
	 * @param key The parameter name.
	 * 
	 * @return Returns the double value of the input parameter.
	 * 
	 * @throws UnexpectedException 
	 *         If the parameter value does not contain a number
	 *         according to the system locale.
	 */
	public Double getPropertyAsDouble (String key) 
	throws UnexpectedException;
	
	/**
	 * Gets the boolean value of a an input parameter.
	 * 
	 * @param key The parameter name.
	 * 
	 * @return Returns the double value of the input parameter.
	 */
	public Boolean getPropertyAsBoolean (String key);
	
    
	/**
	 * Gets the string value of an obligatory input parameter.
	 * 
	 * If the value of the parameter is null, then a the method
	 * will throw a RuntimeException.
	 * 
	 * @param key The parameter name.
	 * 
	 * @return Returns the string value of the input parameter.
	 * 
	 */
    public String getMainProperty (String key);
	
	/**
	 * Gets the date value of an obligatory input parameter.
	 * 
	 * If the value of the parameter is null, then a the method
	 * will throw a RuntimeException.
	 *  
	 * @param key String the property's name
	 * 
	 * @return Returns the date represented by the
	 *         input parameter.
	 * 
	 * @throws UnexpectedException 
	 *         If the parameter value does not contain a valid
	 *         date according to the system locale.
	 * 
	 */
	public Date getMainPropertyAsDate (String key) 
	throws UnexpectedException;
	
	/**
	 * Gets the double value of an obligatory input parameter.
	 * 
	 * If the value of the parameter is null, then a the method
	 * will throw a RuntimeException.
	 *
	 * @param key The parameter name.
	 * 
	 * @return Returns the double value of the input parameter.
	 * 
	 * @throws UnexpectedException 
	 *         If the parameter value does not contain a number
	 *         according to the system locale.
	 */
	public Double getMainPropertyAsDouble (String key) 
	throws UnexpectedException;
    
	/**
	 * Gets the boolean value of an obligatory input parameter.
	 * 
	 * If the value of the parameter is null, then a the method
	 * will throw a RuntimeException.
	 *
	 * @param key The parameter name.
	 * 
	 * @return Returns the double value of the input parameter.
	 */
	public Boolean getMainPropertyAsBoolean (String key);
	
	
    /**
     * Gets the value of the specified property.
	 * 
	 * If the value of the parameter is null, then a the method
	 * will throw a RuntimeException.
	 * 
     * @param key key of property.
     * 
     * @return value of the property.
     */
    public Object getMainPropertyValue(String key);
    


}
