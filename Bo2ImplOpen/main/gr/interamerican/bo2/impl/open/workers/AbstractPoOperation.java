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

import gr.interamerican.bo2.arch.PersistentObject;

/**
 * AbstractPoOperation is an operation that is relevant with one
 * {@link PersistentObject}. 
 * 
 * This operation either takes a {@link PersistentObject} as input,
 * or even operates on it.
 * 
 * @param <P> Type of Persistent object.
 */
public abstract class AbstractPoOperation <P extends PersistentObject<?>> 
extends AbstractOperation {
	/**
	 * Persistent object.
	 */
	protected P po;

	/**
	 * Gets the persistent object.
	 * 
	 * @return Returns the persistent object.
	 */
	public P getPo() {
		return po;
	}
	/**
	 * Sets the persistent object.
	 * 
	 * @param po New persistent object.
	 */
	public void setPo(P po) {
		this.po = po;
	}

}
