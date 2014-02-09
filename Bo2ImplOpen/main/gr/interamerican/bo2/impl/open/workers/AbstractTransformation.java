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
package gr.interamerican.bo2.impl.open.workers;

import gr.interamerican.bo2.arch.Worker;
import gr.interamerican.bo2.arch.exceptions.DataException;
import gr.interamerican.bo2.arch.exceptions.LogicException;
import gr.interamerican.bo2.utils.adapters.Transformation;

/**
 * Abstract base class for implementations of {@link Transformation}
 * that must also be {@link Worker}s.
 * 
 * @param <A>
 *        Transformation argument. 
 * @param <R>
 *        Transformation result. 
 */
public abstract class AbstractTransformation<A,R> 
extends AbstractResourceConsumer 
implements Transformation<A, R> {
	
	public R execute(A a) {
		try {
			return transform(a);
		} catch (LogicException e) {
			throw new RuntimeException(e);
		} catch (DataException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Transformation.
	 * 
	 * @param a
	 * @return Returns the result of the transformation.
	 * 
	 * @throws LogicException 
	 * @throws DataException 
	 */
	protected abstract R transform(A a)
	throws LogicException, DataException ;

}
