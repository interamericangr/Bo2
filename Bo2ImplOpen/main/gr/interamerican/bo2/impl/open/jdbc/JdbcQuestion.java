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


import gr.interamerican.bo2.arch.Question;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.Debug;

import java.util.HashMap;
import java.util.Map;

/**
 * JDBC based implementation of {@link Question}
 *
 * @param <A> Type of answer.
 */
public abstract class JdbcQuestion<A> 
extends AbstractJdbcWorker 
implements Question<A> {
	
	/**
	 * Cache of answer types.
	 */
	protected static Map<Class<?>, Class<?>> answerTypes = new HashMap<Class<?>, Class<?>>();
	
	@Override
	public void ask() throws DataException, LogicException {
		try {
			Debug.setActiveModule(this);
			validateOpen();		
			work();
		} finally {
			Debug.resetActiveModule();
		}
	}
	
	/**
	 * Main body of Queston.
	 * 
	 * @throws DataException
	 * @throws LogicException
	 */
	protected abstract void work() 
	throws DataException, LogicException;	

	public abstract A getAnswer();

}
