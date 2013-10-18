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
import gr.interamerican.bo2.arch.PoDependent;
import gr.interamerican.bo2.arch.Rule;

/**
 * Base class for Persistent object validators.
 * 
 * @param <P> Type of persistent object validated by this validator.  
 */
public abstract class AbstractPoValidator<P extends PersistentObject<?>> 
extends AbstractBaseWorker
implements PoDependent<P>, Rule {
	
	/**
	 * Persistent object to validate.
	 */
	protected P po;

	public P getPo() {
		return po;
	}

	public void setPo(P po) {
		this.po = po;
	}
	
	
	

}
