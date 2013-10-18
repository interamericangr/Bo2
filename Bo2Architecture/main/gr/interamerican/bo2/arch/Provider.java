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
package gr.interamerican.bo2.arch;

import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;

/**
 * Provider is a class that provides the runtime dependent 
 * resources to a Bo2 class. 
 * 
 * Provider is an abstraction of the runtime layer. Any type of 
 * resource as a database connection, file handles, etc can be 
 * provided by the runtime layer to a Bo2 class. <br/>
 * The {@link ResourceWrapper} is the abstraction used for all
 * resources provided by a provider.
 *
 */
public interface Provider {
    
	/**
	 * Returns the named {@link ResourceWrapper} of a specified type.
	 * 
	 * 
	 * @param subclass 
	 *        Type of {@link ResourceWrapper}.
	 * @param resourceName
	 *        Name that distinguishes the specific resource from other
	 *        resources of the same type.
	 * @param <C> 
	 *        Type of ResourceWrapper.       
	 * 
	 * @return Returns an instance that implements the specific sub-interface 
	 *         of ResourceWrapper.
	 *         
	 * @throws InitializationException
	 *         When this Provider can't return the specified sub-type of
	 *         ResourceWrapper.        
	 */
	public <C extends ResourceWrapper> C getResource (String resourceName, Class<C> subclass)
	throws InitializationException;
	
	/**
	 * Gets this provider's {@link TransactionManager}.
	 * 
	 * @return Returns the transaction manager of this provider.
	 */
	public TransactionManager getTransactionManager();
	
	
	
	/**
	 * Closes all resources of this provider.
	 * 
	 * This method will not call any transaction manager functionality. 
	 * Transaction management is done explicitly via the transaction manager.
	 * This method does only cleanup of the resources.
	 * 
	 * @throws DataException
	 */
	public void close() throws DataException;
			
}
