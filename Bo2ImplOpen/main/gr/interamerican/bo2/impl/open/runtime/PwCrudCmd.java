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
package gr.interamerican.bo2.impl.open.runtime;

import gr.interamerican.bo2.arch.PersistenceWorker;
import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.impl.open.creation.Factory;

/**
 * {@link PwCrudCmd} is a {@link CrudCmd} that operates on a 
 * {@link PersistentObject} using a {@link PersistenceWorker}
 * that is created by the {@link Factory}.
 * 
 * @param <P>
 *        Type of PersistenceWorker.
 */
public class PwCrudCmd<P extends PersistentObject<?>> 
extends CrudCmd<P> {

	/**
	 * Creates a new PwCrudCmd object. 
	 *
	 * @param poClass
	 */
	@SuppressWarnings("cast")
	public PwCrudCmd(Class<P> poClass) {
		super((PersistenceWorker<P>)Factory.createPw(poClass));
	}

}
