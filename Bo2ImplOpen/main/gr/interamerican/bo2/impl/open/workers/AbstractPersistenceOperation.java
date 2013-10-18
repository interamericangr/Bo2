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

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.annotations.Child;

import javax.accessibility.Accessible;

/**
 * Abstract persistence operation.
 * 
 * This operation affects a {@link PersistentObject} that is
 * {@link Accessible} via the getPo() method. A persistence 
 * worker is automatically created by the default factory.
 * 
 * @param <P> Type of {@link PersistentObject}
 */
public abstract class AbstractPersistenceOperation
<P extends PersistentObject<?>> 
extends AbstractPoOperation<P> {
	
	
	/**
	 *  Persistence worker.
	 */
	@Child
	protected PersistenceWorker<P> pw;

	/**
	 * Creates a new AbstractSrudOperation object. 
	 * 
	 * @param poClass Class of PersistentObject.
	 *
	 */
	public AbstractPersistenceOperation(Class<P> poClass) {
		super();
		this.pw = Factory.createPw(poClass);
	}
}
