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
package gr.interamerican.bo2.impl.open.jdbc;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.InitializationException;
import gr.interamerican.bo2.arch.utils.ProviderUtils;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;
import gr.interamerican.bo2.utils.ArrayUtils;

import java.sql.Connection;

/**
 * Strategy object used for JDBC.
 */
public abstract class ConnectionStrategy {
	
	/**
	 * Decorated component.
	 */
	protected JdbcConnectionProviderImpl component;
	
	/**
	 * Implements doConnect on component.
	 * 
	 * @return Returns the connection.
	 * 
	 * @throws InitializationException
	 */
	public abstract Connection doConnect() throws InitializationException;
	
	/**
	 * Implements parseProperties on component.
	 * 
	 * @throws InitializationException
	 */
	public abstract void parseProperties() throws InitializationException;

	/**
	 * Gets the component.
	 *
	 * @return Returns the component
	 */
	public JdbcConnectionProviderImpl getComponent() {
		return component;
	}

	/**
	 * Assigns a new value to the component.
	 *
	 * @param component the component to set
	 */
	public void setComponent(JdbcConnectionProviderImpl component) {
		this.component = component;
	}
	
	/**
	 * Gets a mandatory property from the component's properties.
	 * 
	 * @param key
	 *        Key of the property.
	 *        
	 * @return Returns the value of the property.
	 * 
	 * @throws InitializationException
	 *         If the property is null.
	 */
	protected String getMandatoryProperty(String key) throws InitializationException {
		return ProviderUtils.getMandatoryProperty(component.getProperties(), key);
	}
	
	/**
	 * Validates that the Bo2 configuration is correct. This includes
	 * the compatibility of the {@link ConnectionStrategy} and the
	 * {@link TransactionManager}. The implementation of this validation
	 * relies on the {@link Bo2Session} threadlocal instance in order
	 * to assess the {@link TransactionManager} implementation used.
	 * 
	 * @throws InitializationException 
	 *         Thrown if the configuration is wrong.
	 */
	@SuppressWarnings("nls")
	protected void validateSetup() throws InitializationException {
		Class<?>[] transactionManagerTypes = compatibleTransactionManagerImplementations();
		if(ArrayUtils.isNullOrEmpty(transactionManagerTypes)) {
			throw new InitializationException("No compatible transaction managers for " + this);
		}
		Provider prov = Bo2Session.getProvider();
		if(prov==null || prov.getTransactionManager()==null) { //Hacky
			return;
		}
		Class<?> tmClass = Bo2Session.getProvider().getTransactionManager().getClass();
		for(Class<?> type : transactionManagerTypes) {
			if(type==tmClass) {
				return;
			}
		}
		String msg = "Incompatible transaction manager and connection strategy in the current configuration.";
		throw new InitializationException(msg);
	}
	
	/**
	 * @return Returns the {@link TransactionManager} implementations that are known
	 *         to work with this {@link ConnectionStrategy}.
	 */
	protected abstract Class<?>[] compatibleTransactionManagerImplementations();
	
}
