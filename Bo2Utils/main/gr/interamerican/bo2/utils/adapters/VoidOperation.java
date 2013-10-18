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
 * VoidOperation is a generic adapter interface for any void operation
 * that can be applied to an object.
 * 
 * An adapter object that implements this interface is easy to implement.
 * Then the utilities using this adapter can be used in order to run the
 * operation in predefined data structures.
 * 
 * @param <A>
 *        Type of operation argument.
 * 
 */
public interface VoidOperation<A> {
	/**
	 * Operation.
	 * 
	 * @param a
	 *        Operation argument.
	 */
	void execute(A a);
}


