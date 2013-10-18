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
package gr.interamerican.bo2.samples.providers;

import gr.interamerican.bo2.arch.Provider;
import gr.interamerican.bo2.arch.ResourceWrapper;
import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.InitializationException;

/**
 * Empty implementation of {@link Provider} used in this test.
 *
 */
public class EmptyProvider implements Provider {
	
	public void close() throws DataException {
		/* nothing to do */
	}
	
	public <C extends ResourceWrapper> C getResource (String resourceName, Class<C> subclass) 
	throws InitializationException {
		throw new InitializationException();
	}

	public TransactionManager getTransactionManager() {
		return null;
	}
}
