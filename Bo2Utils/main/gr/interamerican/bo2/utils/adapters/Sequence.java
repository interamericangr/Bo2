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
 * Sequential operation.
 * 
 * Executes a sequence of adapter operations.
 * 
 * @param <A>
 *        Type of argument. Must be the same as the argument type
 *        of the sequence's first adapter operation. 
 * @param <R>
 *        Type of result. Must be the same as the result type
 *        of the sequence's last adapter operation. 

 *  
 */
public class Sequence<A,R> implements AnyOperation<A, R>{
	
	/**
	 * Sequence of operations to execute.
	 */
	AnyOperation<?, ?>[] operations;

	
	/**
	 * Creates a new Sequence object. 
	 *
	 * @param operations
	 */
	public Sequence(AnyOperation<?, ?>... operations) {
		super();
		this.operations = operations;
	}


	@SuppressWarnings("unchecked")
	public R execute(Object a) {
		Object arg = a;
		for (int i = 0; i < operations.length; i++) { 
			AnyOperation<Object,Object> operation = (AnyOperation<Object,Object>) operations[i];
			Object result = operation.execute(arg);
			arg = result;
		}
		return (R)arg;
	}

}
