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
package gr.interamerican.bo2.impl.open.jotm;

import gr.interamerican.bo2.arch.TransactionManager;
import gr.interamerican.bo2.arch.utils.ext.Bo2Session;

import org.enhydra.jdbc.standard.StandardXADataSource;

/**
 * Utilities for Jotm.
 */
public class JotmUtils {
	
	/**
	 * Enlists a transactional DataSource to the {@link JotmTransactionManager}
	 * instance associated with the current thread.
	 * @param ds 
	 */
	public static void enListTransactionalDataSource(StandardXADataSource ds) {
		TransactionManager tm = Bo2Session.getProvider().getTransactionManager();
		validateTm(tm);
		JotmTransactionManager jotm = (JotmTransactionManager) tm;
		ds.setTransactionManager(jotm.getJotmTransactionManager());
	}
	
	/**
	 * Validates a {@link TransactionManager}.
	 * 
	 * @param tm
	 */
	@SuppressWarnings("nls")
	static void validateTm(TransactionManager tm) {
		if (tm==null) {
			String msg = "No transaction manager.";
			throw new RuntimeException(msg);
		}
		if (!(tm instanceof JotmTransactionManager)) {
			String msg = "A Jotm based ConnectionStrategy has to be used in an " 
					+    "environment where the TransactionManager is Jotm based.";
			throw new RuntimeException(msg);
		}
	}

}
