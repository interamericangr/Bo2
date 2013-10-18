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

import gr.interamerican.bo2.arch.DetachStrategy;
import gr.interamerican.bo2.arch.Provider;

/**
 * This implementation of {@link DetachStrategy} does not do 
 * anything.
 */
public class JdbcDetachStrategy implements DetachStrategy {
	
	/**
	 * Singleton instance.
	 */
	public static final JdbcDetachStrategy INSTANCE = new JdbcDetachStrategy();
	
	/**
	 * Creates a new JdbcDetachStrategy object. 
	 *
	 */
	private JdbcDetachStrategy() {
		super();		
	}

	public void reattach(Object object, Provider provider) {/* do nothing */}

	public void detach(Object object, Provider provider) {/* do nothing */}	
	
	public void markExplicitSave(Object object, Provider provider) {/* do nothing */}

}
