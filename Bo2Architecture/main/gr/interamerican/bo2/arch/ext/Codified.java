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
package gr.interamerican.bo2.arch.ext;

/**
 * An object that has a code for its identification.
 * 
 * @param <C> Type of code. 
 * 
 */
public interface Codified<C extends Comparable<? super C>> extends Comparable<Codified<C>> {
	
	/**
	 * Gets the code of the object.
	 * 
	 * @return Returns the code.
	 */
	public C getCode();
	
	/**
	 * Sets the code of the object.
	 * 
	 * @param code new code.
	 */
	public void setCode(C code);
	

}
