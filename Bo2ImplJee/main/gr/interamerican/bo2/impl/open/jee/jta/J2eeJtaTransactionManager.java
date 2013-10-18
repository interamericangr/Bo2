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
package gr.interamerican.bo2.impl.open.jee.jta;

import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.exceptions.InitializationException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.transaction.UserTransaction;

/**
 * JTA implementation of {@link TransactionManager} based on a 
 * standard J2EE application server.
 */
public class J2eeJtaTransactionManager 
extends JtaTransactionManager {

	/**
	 * Creates a new J2eeJtaTransactionManager object. 
	 * 
	 * @throws InitializationException 
	 *
	 */
	public J2eeJtaTransactionManager() throws InitializationException {
		super();		
		 try {			 
			 Context ctx = new InitialContext(); 
			 ut = (UserTransaction) ctx.lookup("java:comp/UserTransaction"); //$NON-NLS-1$
		} catch (NamingException ne) {
			throw new InitializationException(ne);
		}
	}

}
