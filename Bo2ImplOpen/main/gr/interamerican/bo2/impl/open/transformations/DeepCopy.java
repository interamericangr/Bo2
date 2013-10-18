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
package gr.interamerican.bo2.impl.open.transformations;

import gr.interamerican.bo2.arch.PersistentObject;
import gr.interamerican.bo2.arch.ext.Copier;
import gr.interamerican.bo2.impl.open.po.PoUtils;
import gr.interamerican.bo2.utils.adapters.Modification;

/**
 * DeepCopier for objects of the same type.
 * 
 * The new object is being created using the default Bo2 factory.
 * 
 * @param <A> 
 *        Type of argument.
 */
public class DeepCopy<A extends PersistentObject<?>> 
implements Modification<A>, Copier<A> {

	/**
	 * Creates a new CopyTo object. 
	 */
	public DeepCopy() {
		super();
	}

	
	public A copy(A objectToCopy) {
		return execute(objectToCopy);
	}
	
	public A execute(A a) {
		return PoUtils.deepCopy(a);
	}

}
