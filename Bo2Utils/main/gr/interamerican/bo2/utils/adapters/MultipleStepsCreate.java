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
package gr.interamerican.bo2.utils.adapters;

/**
 * Creates an object in multiple steps by using a series of
 * {@link Create} adapters.
 * 
 * @param <A>
 *        Type of argument. 
 * @param <R> 
 *        Type of result.
 */
public class MultipleStepsCreate<A,R> 
implements AnyOperation<A, R> {
			
	/**
	 * Sequence.
	 */
	Sequence<A, R> sequence;
	
	public R execute(A a) {		
		return sequence.execute(a);
	}

	/**
	 * Creates a new MultipleStepsCreate object. 
	 *
	 * @param classes
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public MultipleStepsCreate(Class<?>... classes) {
		super();
		int createsCount = classes.length-1;
		AnyOperation[] creates = new AnyOperation<?, ?>[createsCount];		
		for (int i = 0; i < createsCount; i++) {
			Class arg = classes[i];
			Class ret = classes[i+1];
			Create create = new Create(arg, ret);
			creates[i] = create;
		}	
		sequence = new Sequence<A, R>(creates);
	}

}
