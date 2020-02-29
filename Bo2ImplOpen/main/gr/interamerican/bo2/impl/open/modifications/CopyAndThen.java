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
package gr.interamerican.bo2.impl.open.modifications;

import gr.interamerican.bo2.impl.open.creation.Factory;
import gr.interamerican.bo2.utils.ReflectionUtils;
import gr.interamerican.bo2.utils.adapters.Modification;

/**
 * A {@link Modification} that will apply another {@link Modification} (passed
 * as argument) on a copy of the original object. Execution is skipped on null
 * objects - null is returned on that case.
 * 
 * @param <T>
 *            Type of object being modified.
 */
public class CopyAndThen<T> implements Modification<T> {

	/**
	 * Class of Object
	 */
	private Class<T> clz;

	/**
	 * Action to do on copied object
	 */
	private Modification<T> then;

	/**
	 * Public Constructor.
	 * 
	 * @param clz
	 *            Class of Object
	 * @param then
	 *            Action to do on copied object
	 */
	public CopyAndThen(Class<T> clz, Modification<T> then) {
		this.clz = clz;
		this.then = then;
	}

	@Override
	public T execute(T a) {
		if (a == null) {
			return null;
		}
		T copy = Factory.create(clz);
		ReflectionUtils.copyProperties(a, copy);
		return then.apply(copy);
	}
}